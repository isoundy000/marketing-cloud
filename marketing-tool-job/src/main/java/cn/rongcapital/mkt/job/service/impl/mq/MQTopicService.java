package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.TaskTypeEnum;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.service.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.po.TaskRunLog;

@Service
public class MQTopicService {

    private static volatile boolean isJndiInited = false;

    private static TopicConnection connection = null;
    private static TopicSession session = null;
    private static String MY_TOPIC = "test1.mq.topic";
    private static String MY_TOPIC1 = "test2.mq.topic";
    private static String MY_TOPIC2 = "test3.mq.topic";
    private static String MY_TOPIC3 = "test4.mq.topic";

    private static final String MQ_TASK_NAME = "taskName";
    private static final String MQ_SERVICE_NAME = "serviceName";
    @Value("${spring.activemq.broker-url}")
    private String providerUrl;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ApplicationContext cotext;
    @Autowired
    private TaskRunLogDao taskRunLogDao;
    
    public void initMQService() {
        if (isJndiInited) {
            return;
        }
        isJndiInited = true;
        try {
            // 创建链接工厂
            TopicConnectionFactory factory =
                            new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                                            ActiveMQConnection.DEFAULT_PASSWORD, providerUrl);
            // 通过工厂创建一个连接
            connection = factory.createTopicConnection();
            // connection.setClientID("1");离线
            // 创建一个session会话
            session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // TopicSubscriber subscriber = session.createDurableSubscriber(topic, "test");离线模式
            // 启动连接
            connection.start();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private  void receiverMessage(TopicConnection connection, String topicName)
                    throws JMSException {
        // 创建一个消息队列
        Topic topic = session.createTopic(topicName);
        // 创建消息制作者
        TopicSubscriber subscriber = session.createSubscriber(topic, null, true);
        subscriber.setMessageListener(new MessageListener() {
            public void onMessage(Message msg) {
                if (msg != null) {
                    MapMessage map = (MapMessage) msg;
                    try {
                        String taskName = map.getString(MQ_TASK_NAME);
                        String serviceName = map.getString(MQ_SERVICE_NAME);
                        
                        logger.info("taskName is {}" , taskName);
                        logger.info("serviceName is {}" , serviceName);
                        serviceName = getServiceName(serviceName);
                        startMqTask(serviceName);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void initReceiver() {
       initMQService();
        try {
            receiverMessage(connection, MY_TOPIC);
            receiverMessage(connection, MY_TOPIC1);
            receiverMessage(connection, MY_TOPIC2);
            receiverMessage(connection, MY_TOPIC3);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void senderMessage(String topicName, ActiveMqMessageVO message)
                    throws JMSException {
        // 发送的时候要新建一个链接，用完就关闭
        TopicConnection connection = null;
        TopicSession session = null;
        try {
            // 创建链接工厂
            TopicConnectionFactory factory =
                            new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                                            ActiveMQConnection.DEFAULT_PASSWORD, providerUrl);
            // 通过工厂创建一个连接
            connection = factory.createTopicConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Topic topic = session.createTopic(topicName);
            // 创建消息发送者
            TopicPublisher publisher = session.createPublisher(topic);
            // 设置持久化模式
            publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, publisher, message);
            // 提交会话
            session.commit();
        } catch (JMSException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭释放资源
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void sendMessage(TopicSession session, TopicPublisher publisher,
                    ActiveMqMessageVO messageVo) throws Exception {
        MapMessage map = session.createMapMessage();
        map.setString(MQ_TASK_NAME, messageVo.getTaskName());
        map.setString(MQ_SERVICE_NAME, messageVo.getServiceName());
        publisher.send(map);
    }

    private String getServiceName(String serviceName) {
        char serviceNameChar[] = serviceName.toCharArray();
        serviceNameChar[0] = Character.toLowerCase(serviceNameChar[0]);
        String sname = String.valueOf(serviceNameChar);
        return sname;
    }
    
    
    //测试任务
    public static void main(String[] args) {
        ActiveMqMessageVO message = new ActiveMqMessageVO();
        message.setTaskName("任务一");
        message.setServiceName("TestTask");
        try {
            MQTopicService mc = new MQTopicService();
            mc.senderMessage(MY_TOPIC2, message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 执行mq topic的任务
     * @param serviceName
     */
    public synchronized void startMqTask(String serviceName) {
        logger.info("startTask:" + serviceName);
        try {
            Object serviceBean = cotext.getBean(serviceName);
            if (serviceBean instanceof TaskService) {
                TaskService taskService = (TaskService) serviceBean;
                TaskRunLog taskRunLog = null;
                
                //记录日志
                taskRunLog = new TaskRunLog();
                taskRunLog.setCreateTime(new Date());
                taskRunLog.setStartTime(new Date());
                taskRunLog.setTaskName(serviceName);
                taskRunLog.setTaskType((byte)TaskTypeEnum.HIDE.getCode());
                taskRunLogDao.insert(taskRunLog);
                
                taskService.task();
                if (null != taskRunLog) {
                    taskRunLog.setEndTime(new Date());
                    taskRunLogDao.updateById(taskRunLog);
                }
            }
            Log.info("service not instanceof TaskService");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

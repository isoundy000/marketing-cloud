package cn.rongcapital.mkt.service.impl;

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
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SynchSystemTagService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;

/**
 * @author 李海光
 *
 */
@Service
public class MQTopicServiceImpl implements MQTopicService {

	private static volatile boolean isJndiInited = false;

	private static TopicConnection connection = null;
	private static TopicSession session = null;

	private static final String MQ_TASK_NAME = "taskName";
	private static final String MQ_SERVICE_NAME = "serviceName";
	private static final String MQ_MESSAGE = "message";
	private static final String MQ_PARAM_KEY = "SystemTag";
	private static final String TARGET_SERVICE = "synchSystemTagServiceImpl";
	private static final String MQ_SEGMENT_KEY = "mq.synSegment";
	private static final String MQ_SMS_SERVICE = "smsSendTaskServiceImpl";
	private static final String MQ_CODE_SERVICE = "couponCodeSaveTaskImpl";
	private static final String MQ_SMS_GENERATE_DETAIL_SERVICE = "generateSmsDetailTask";
	private static final String MQ_CREATE_TARGET_AUDIENCE_SERVICE = "createTargetAudienceGroupTask";

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
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD, providerUrl);
			// 通过工厂创建一个连接
			connection = factory.createTopicConnection();
			// connection.setClientID("1");离线
			// 创建一个session会话
			session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// TopicSubscriber subscriber =
			// session.createDurableSubscriber(topic, "test");离线模式
			// 启动连接
			connection.start();

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void receiverMessage(TopicConnection connection, String topicName) throws JMSException {
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

						logger.info("taskName is {}", taskName);
						logger.info("serviceName is {}", serviceName);
						serviceName = getServiceName(serviceName);
						startMqTask(serviceName, taskName);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	/**
	 * 用于接收处理mq里面的数据(通用)
	 * 
	 */
	private void receiverMessageComm(TopicConnection connection, String topicName) throws JMSException{
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
								String message = map.getString(MQ_MESSAGE);
								//TODO 测试用 后期改为debug
								logger.info("taskName is {}", taskName);
								logger.info("serviceName is {}", serviceName);
								logger.info("message is {}" , message);
								serviceName = getServiceName(serviceName);
								
								startMqTaskComm(serviceName, taskName, message);
							} catch (JMSException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}
	
	public synchronized void startMqTaskComm(String serviceName, String taskName, String message) {
		logger.info("startTask:" + serviceName);
		try {
			Object serviceBean = cotext.getBean(serviceName);
			if (serviceBean instanceof TaskService) {
				TaskService taskService = (TaskService) serviceBean;
				TaskRunLog taskRunLog = null;

				// 记录日志
				taskRunLog = new TaskRunLog();
				taskRunLog.setCreateTime(new Date());
				taskRunLog.setStartTime(new Date());
				taskRunLog.setTaskName(taskName);
				taskRunLog.setTaskType((byte) TaskTypeEnum.DISPLAY.getCode());
				taskRunLogDao.insert(taskRunLog);
				//异步处理
				new Thread(){
				    public void run(){
				        taskService.task(message);
				    }
				}.start();
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

	public void initReceiver() {
		initMQService();
		try {
			receiverMessage(connection, "GetPubList");
			receiverMessage(connection, "GetPubFansList");
			receiverMessage(connection, "GetImgtextAsset");
			getParamValue(connection, "SystemTag");
			synSegmentTaskService(connection, MQ_SEGMENT_KEY);
			receiverMessageComm(connection, MQ_SMS_SERVICE);
			receiverMessageComm(connection, MQ_CODE_SERVICE);
			receiverMessageComm(connection, MQ_SMS_GENERATE_DETAIL_SERVICE);
			receiverMessageComm(connection, MQ_CREATE_TARGET_AUDIENCE_SERVICE);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.rongcapital.mkt.service.MQTopicService#senderMessage(java.lang.String,
	 * cn.rongcapital.mkt.vo.ActiveMqMessageVO)
	 */
	@Override
	public void senderMessage(String topicName, ActiveMqMessageVO message) throws JMSException {
		// 发送的时候要新建一个链接，用完就关闭
		TopicConnection connection = null;
		TopicSession session = null;
		try {
			// 创建链接工厂
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
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

	public static void sendMessage(TopicSession session, TopicPublisher publisher, ActiveMqMessageVO messageVo)
			throws Exception {
		MapMessage map = session.createMapMessage();
		map.setString(MQ_TASK_NAME, messageVo.getTaskName());
		map.setString(MQ_SERVICE_NAME, messageVo.getServiceName());
		map.setString(MQ_MESSAGE, messageVo.getMessage() == null ? "" : messageVo.getMessage());
		publisher.send(map);
	}

	private String getServiceName(String serviceName) {
		char serviceNameChar[] = serviceName.toCharArray();
		serviceNameChar[0] = Character.toLowerCase(serviceNameChar[0]);
		String sname = String.valueOf(serviceNameChar);
		return sname;
	}

	// 测试任务
	public static void main(String[] args) {
		ActiveMqMessageVO message = new ActiveMqMessageVO();
		message.setTaskName("任务一");
		message.setServiceName(MQ_SMS_SERVICE);
		message.setMessage("1");
		try {
			MQTopicServiceImpl mc = new MQTopicServiceImpl();
			mc.senderMessage(MQ_SMS_SERVICE, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendSmsByTaskId(String taskId){
		ActiveMqMessageVO message = new ActiveMqMessageVO();
		message.setTaskName("短信发送任务");
		message.setServiceName(MQ_SMS_SERVICE);
		message.setMessage(taskId);
		try {
//			MQTopicServiceImpl mc = new MQTopicServiceImpl();
			this.senderMessage(MQ_SMS_SERVICE, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 执行mq topic的任务
	 * 
	 * @param serviceName
	 */
	public synchronized void startMqTask(String serviceName, String taskName) {
		logger.info("startTask:" + serviceName);
		try {
			Object serviceBean = cotext.getBean(serviceName);
			if (serviceBean instanceof TaskService) {
				TaskService taskService = (TaskService) serviceBean;
				TaskRunLog taskRunLog = null;

				// 记录日志
				taskRunLog = new TaskRunLog();
				taskRunLog.setCreateTime(new Date());
				taskRunLog.setStartTime(new Date());
				taskRunLog.setTaskName(taskName);
				taskRunLog.setTaskType((byte) TaskTypeEnum.DISPLAY.getCode());
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

	@Override
	public void senderMessage(String topicName, String message) throws JMSException {
		// 发送的时候要新建一个链接，用完就关闭
		TopicConnection connection = null;
		TopicSession session = null;
		try {
			// 创建链接工厂
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
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
			putMessage(session, publisher, message);
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

	public static void putMessage(TopicSession session, TopicPublisher publisher, String message) throws Exception {
		MapMessage map = session.createMapMessage();
		map.setString(MQ_PARAM_KEY, message);
		publisher.send(map);
	}

	private void getParamValue(TopicConnection connection, String topicName) throws JMSException {
		// 创建一个消息队列
		Topic topic = session.createTopic(topicName);
		// 创建消息制作者
		TopicSubscriber subscriber = session.createSubscriber(topic, null, true);
		subscriber.setMessageListener(new MessageListener() {
			public void onMessage(Message msg) {
				if (msg != null) {
					MapMessage map = (MapMessage) msg;
					try {
						String paramValue = map.getString(MQ_PARAM_KEY);
						Object serviceBean = cotext.getBean(TARGET_SERVICE);
						SynchSystemTagService synchSystemTag = (SynchSystemTagService) serviceBean;
						synchSystemTag.synchTagToMongo(paramValue);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void synSegmentTaskService(TopicConnection connection, String topicName) throws JMSException {
		// 创建一个消息队列
		Topic topic = session.createTopic(topicName);
		// 创建消息制作者
		TopicSubscriber subscriber = session.createSubscriber(topic, null, true);
		subscriber.setMessageListener(new MessageListener() {
			public void onMessage(Message msg) {
				if (msg != null) {
					MapMessage map = (MapMessage) msg;
					TaskLog(map, TaskTypeEnum.HIDE);

				}
			}
		});
	}

	private void TaskLog(MapMessage mapMsg, TaskTypeEnum taskType) {
		try {
			String serviceName = mapMsg.getString(MQ_SERVICE_NAME);
			Log.info("TaskService class:" + serviceName);
			String taskName = mapMsg.getString(MQ_TASK_NAME);
			Log.info("TaskService desc:" + taskName);
			String message = mapMsg.getString("message");
			Log.info("TaskService json:" + message);

			Object serviceBean = cotext.getBean(serviceName);

			if (serviceBean instanceof TaskService) {
				Log.info("service instanceof TaskService");
				TaskService taskService = (TaskService) serviceBean;
				TaskRunLog taskRunLog = null;

				// 记录日志
				taskRunLog = new TaskRunLog();
				taskRunLog.setCreateTime(new Date());
				taskRunLog.setStartTime(new Date());
				taskRunLog.setTaskName(taskName);
				taskRunLog.setTaskType((byte) taskType.getCode());
				taskRunLogDao.insert(taskRunLog);

				taskService.task(message);
				if (null != taskRunLog) {
					taskRunLog.setEndTime(new Date());
					taskRunLogDao.updateById(taskRunLog);
				}
			}
		} catch (JMSException e) {
			logger.error("TaskLog-----" + e.getMessage(), e);
		}

	}

}

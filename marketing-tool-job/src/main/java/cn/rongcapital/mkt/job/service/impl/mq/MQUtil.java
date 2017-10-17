//package cn.rongcapital.mkt.job.service.impl.mq;
//
//import java.util.List;
//
//import javax.jms.Connection;
//import javax.jms.Destination;
//import javax.jms.Session;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsMessagingTemplate;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//
//import cn.rongcapital.mkt.po.mongodb.Segment;
//
//@Component
//public class MQUtil {
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired  
//    private JmsMessagingTemplate jmsMessagingTemplate; 
//	
//	@Autowired
//	private JmsTemplate jmsTemplate;
//	
//	/**
//	 * campaignSegmentList
//	 * @param campaignSegmentListMap
//	 * @param dest
//	 */
//	public void sendDynamicQueue(List<Segment> campaignSegmentList,String dest) { 
//		Connection connection = null;
//		Session session = null;
//    	try {
//    		connection = jmsMessagingTemplate.getConnectionFactory().createConnection();
//            connection.start();
//    		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//    		Destination destination = session.createQueue(dest);
//    		jmsMessagingTemplate.convertAndSend(destination, campaignSegmentList); 
//    		
//    		session.close();
//			connection.close();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}
//    } 
//	
//}

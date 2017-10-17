package cn.rongcapital.mkt.common.plugin;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class ActiveMQConfig {
	@Value("${spring.activemq.broker-url}")
	private String MQ_BROKER_URL;
    @Bean  
    public ActiveMQConnectionFactory activeMQConnectionFactory () {  
        ActiveMQConnectionFactory activeMQConnectionFactory =  
                new ActiveMQConnectionFactory(  
                        ActiveMQConnectionFactory.DEFAULT_USER,  
                        ActiveMQConnectionFactory.DEFAULT_PASSWORD,  
                        MQ_BROKER_URL);  
//	                    ActiveMQConnectionFactory.DEFAULT_BROKER_URL);  
        return activeMQConnectionFactory;  
    }  
}

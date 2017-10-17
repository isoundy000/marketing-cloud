/**
 * 
 */
package cn.rongcapital.mkt.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shangchunming
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@PropertySource(value = {"classpath:${conf.dir}/application-api.properties",
        "classpath:${conf.dir}/application-dao.properties", "classpath:${conf.dir}/event-center.properties"

})
@ImportResource(value = {"classpath:spring/spring-config-mybatis-heracles.xml",
        "classpath:spring/spring-config-mongodb.xml", "classpath:event-center.xml"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"cn.rongcapital.mkt.common", "cn.rongcapital.mkt.event", "cn.rongcapital.mkt.dao.event",
        "cn.rongcapital.mkt.redis"})
@EnableMongoRepositories
public class EventCenterLauncher {
    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EventCenterLauncher.class, args);
    }

}

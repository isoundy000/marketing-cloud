package cn.rongcapital.mkt.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/*************************************************
 * @功能及特点的描述简述: 增加JdbcTemplate支持
 * 
 * @see （与该类关联的类): WechatInfoSendBiz
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.3 @date(创建、开发日期): 2016-09-26 最后修改日期: 2016-09-26
 * @复审人: 丛树林
 *************************************************/
@Configuration
public class DataSourceConfig {
	/**
	 * @功能简述 获取数据源
	 * 
	 * @return: datasource
	 * 
	 */
	@Bean(name = "secondaryDataSource")
	@Qualifier("secondaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource secondaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * @功能简述 获取JdbcTemplate
	 * 
	 * @param: DataSource
	 *             获取数据源
	 * @return: JdbcTemplate JdbcTemplate
	 */
	@Bean(name = "secondaryJdbcTemplate")
	public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
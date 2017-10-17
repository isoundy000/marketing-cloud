package cn.rongcapital.mkt.common.jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:application.properties")
public class JedisProperties {

	private static final Logger log = Logger.getLogger(JedisProperties.class);

	public static String APPLICATION_CONFIG = "/application.properties";
	public static String JEDIS_CONFIG = "/jedisConfig.properties";
	private static JedisProperties properties = null;
	private JedisConfiguration config = null;
	private String classesPath = null;
	private static Properties applicationConfig = new Properties();
	
	
	public static JedisProperties getInstance() throws IOException {		
		InputStream  inputStream = JedisProperties.class.getResourceAsStream(APPLICATION_CONFIG);
		applicationConfig.load(inputStream);				
		String jedis_config = applicationConfig.getProperty("conf.dir");
		JEDIS_CONFIG ="/"+jedis_config+JEDIS_CONFIG;
		if (properties == null) {
			properties = new JedisProperties();
			try {
				properties.config = new JedisConfiguration(JEDIS_CONFIG);
			} catch (JedisConfigurationException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return properties;
	}

	public static JedisProperties getInstance(String filepath) {
		if (properties == null) {
			properties = new JedisProperties();
			try {
				properties.config = new JedisConfiguration(filepath);
			} catch (JedisConfigurationException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return properties;
	}

	public String getValue(String key, String defalutValue) {
		return this.config.getValue(key, defalutValue);
	}

	public String getValue(String key) {
		return this.config.getValue(key);
	}

	public int getIntValue(String key, int defaultValue) {
		int value = 0;
		value = Integer.parseInt(getValue(key, defaultValue + ""));
		return value;
	}

	public int getIntValue(String key) {
		int value = 0;
		value = Integer.parseInt(getValue(key));
		return value;
	}

	public void setValue(String key, String value) throws JedisConfigurationException {
		this.config.setValue(key, value);
	}

	public String getClassesPath() {
		return this.classesPath;
	}

	public void setClassesPath(String classesPath) {
		this.classesPath = classesPath;
	}

	public static void main(String[] args) throws JedisConfigurationException, IOException {
		String ips = getInstance().getValue("dns.list");
		System.out.println(ips);
	}
}

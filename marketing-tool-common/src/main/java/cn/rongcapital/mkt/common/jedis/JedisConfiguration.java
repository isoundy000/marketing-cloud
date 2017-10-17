package cn.rongcapital.mkt.common.jedis;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JedisConfiguration {
	
	private Log log = LogFactory.getLog(JedisConfiguration.class);
	private Properties config = new Properties();

	public JedisConfiguration() {
	}

	public JedisConfiguration(String fileName) throws JedisConfigurationException {
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream(fileName);
			this.config.load(in);
			this.log.debug("file path=" + in);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.log.error("Can't read files:" + fileName);
			this.log.debug("使用默认类加载路径加载配置文件出错，尝试个性类加载路径加载配置文件");
			try {
				in = JedisConfiguration.class.getResourceAsStream("/prop.properties");
				this.config.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public String getValue(String itemName) {
		return this.config.getProperty(itemName);
	}

	public String getValue(String itemName, String defaultValue) {
		String result = this.config.getProperty(itemName, defaultValue);
		return result;
	}

	public void setValue(String itemName, String value) {
		this.config.setProperty(itemName, value);
	}

	public void saveFile(String fileName, String description)
			throws JedisConfigurationException {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			this.config.store(fout, description);
			fout.close();
		} catch (IOException ex) {
			this.log.error("Can't not save files:" + fileName);
			throw new JedisConfigurationException("Can't not save files:" + fileName);
		}
	}

	public void saveFile(String fileName) throws JedisConfigurationException {
		saveFile(fileName, "");
	}
}

package cn.rongcapital.mkt.common.jedis;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author 作者姓名
 * @version 创建时间：2015-4-13 下午12:15:35 类说明
 */
public class JedisConnectionManager {

	private static JedisPool pool;
	private static JedisPool pool_user;
	private static JedisPool pool2;
	private static JedisPool pool3;
	private static JedisPool pool4;
	private static JedisPool pool5;

	private static Logger logger = LoggerFactory.getLogger(JedisConnectionManager.class);

	public JedisConnectionManager() throws IOException {
		JedisProperties prop = JedisProperties.getInstance();
		String REDIS_IP = prop.getValue("redis.host");
		int REDIS_PORT = prop.getIntValue("redis.host.port");
		String REDIA_PASS = prop.getValue("redis.host.pass");
		int DATA_BASE = prop.getIntValue("redis.host.database");
		int DATA_BASE_USER = prop.getIntValue("redis.host.database.user");

		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setTestOnBorrow(true);
		jpc.setMaxIdle(2000);
		jpc.setMaxTotal(3000);
		jpc.setMaxWaitMillis(1000 * 5);
		jpc.setTestOnBorrow(true);
		jpc.setTestOnReturn(true);
		// jpc.setMaxActive(200);
		// jpc.setMaxWait(100L);
		/** 判断有无密码来生成连接池 */
		/*
		 * if(REDIA_PASS == null || REDIA_PASS.length() <= 0){
		 * JedisConnectionManager.pool = new JedisPool(jpc, REDIS_IP,
		 * REDIS_PORT, 2000); }else{ JedisConnectionManager.pool = new
		 * JedisPool(jpc, REDIS_IP, REDIS_PORT, 2000, REDIA_PASS,DATA_BASE); }
		 */
		/** 这里我们必须使用密码 */
		JedisConnectionManager.pool = new JedisPool(jpc, REDIS_IP, REDIS_PORT, 2000, REDIA_PASS, DATA_BASE);
		JedisConnectionManager.pool_user = new JedisPool(jpc, REDIS_IP, REDIS_PORT, 2000, REDIA_PASS, DATA_BASE_USER);
		JedisConnectionManager.pool2 = new JedisPool(jpc, REDIS_IP, REDIS_PORT, 2000, REDIA_PASS, 2);
		JedisConnectionManager.pool3 = new JedisPool(jpc, REDIS_IP, REDIS_PORT, 2000, REDIA_PASS, 3);
		JedisConnectionManager.pool4 = new JedisPool(jpc, REDIS_IP, REDIS_PORT, 2000, REDIA_PASS, 4);
		JedisConnectionManager.pool5 = new JedisPool(jpc, REDIS_IP, REDIS_PORT, 2000, REDIA_PASS, 5);

	}

	public static Jedis getConnection(int database) {
		Jedis jedis = null;
		switch (database) {
		case 2: {
			jedis = pool2.getResource();
			break;
		}
		case 3: {
			jedis = pool3.getResource();
			break;
		}
		case 4: {
			jedis = pool4.getResource();
			break;
		}
		case 5: {
			jedis = pool5.getResource();
			break;
		}
		default: {
			jedis = pool.getResource();
			pool.returnBrokenResource(jedis);
		}
		}
		return jedis;
	}

	public static Jedis getConnection() {
		Jedis jedis = pool.getResource();
		return jedis;
	}

	public static Jedis getNewConnection() {
		int timeoutCount = 0;
		while (true) {
			try {
				Jedis jedis = pool.getResource();
				return jedis;
			} catch (Exception e) {
				// 底层原因是SocketTimeoutException，不过redis已经捕捉且抛出JedisConnectionException，不继承于前者
				if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException) {
					timeoutCount++;
					logger.warn("getJedis timeoutCount={}", timeoutCount);
					if (timeoutCount > 4) {
						break;
					}
				} else {
					logger.error("getJedis error", e);
					break;
				}
			}
		}
		return null;
	}

	public static void closeConnection(Jedis jedis) {
		pool.returnResource(jedis);
	}

	public static void closeConnection(Jedis jedis, int database) {
		switch (database) {
		case 2: {
			pool2.returnBrokenResource(jedis);
			break;
		}
		case 3: {
			jedis = pool3.getResource();
			pool3.returnBrokenResource(jedis);
			break;
		}
		case 4: {
			jedis = pool4.getResource();
			pool4.returnBrokenResource(jedis);
			break;
		}
		case 5: {
			jedis = pool5.getResource();
			pool5.returnBrokenResource(jedis);
			break;
		}
		default: {
			jedis = pool.getResource();
			pool.returnBrokenResource(jedis);
		}
		}
	}

	public static void destroy() {
		pool.destroy();
	}

	public static Jedis getConnectionUser() {
		Jedis jedis = pool_user.getResource();
		return jedis;
	}

	public static void closeConnectionUser(Jedis jedis) {
		pool_user.returnResource(jedis);
	}

	public static void destroyUser() {
		pool_user.destroy();
	}
}
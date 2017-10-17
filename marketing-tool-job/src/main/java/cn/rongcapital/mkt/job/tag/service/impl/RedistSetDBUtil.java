package cn.rongcapital.mkt.job.tag.service.impl;

import cn.rongcapital.mkt.common.jedis.JedisConnectionManager;
import redis.clients.jedis.Jedis;

/*************************************************
 * @功能简述: 设置Redis数据库索工具类
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/11/10
 * @复审人:
 *************************************************/
public class RedistSetDBUtil {
	
	private static final int REDIS_DATABASE_INDEX = 2;
	
	public static Jedis getRedisInstance(){
		 Jedis jedis = JedisConnectionManager.getConnection();
		 jedis.select(REDIS_DATABASE_INDEX);
		 return jedis;
	}

}

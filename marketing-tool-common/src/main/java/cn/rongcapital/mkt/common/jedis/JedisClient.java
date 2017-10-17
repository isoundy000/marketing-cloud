package cn.rongcapital.mkt.common.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * jedis服务客户端
 * 
 * @author ningjing
 * @version 创建时间：2015-4-13 下午12:25:32
 */
public class JedisClient {

	
	public static long delete(String... keys) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		long rs;
		try {
			rs = jedis.del(keys);
		} catch (Exception e) {
			throw new JedisException("删除String... Key异常!", e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}
	public static long delete(Integer index,String... keys) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
        jedis.select(index);
		long rs;
		try {
			rs = jedis.del(keys);
		} catch (Exception e) {
			throw new JedisException("删除String... Key异常!", e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}
	public static boolean delete(String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		boolean rs;
		try {
			rs = jedis.del(key) == 1 ? true : false;
		} catch (Exception e) {
			throw new JedisException("删除单个key异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
		
	}
	public static boolean delete(Integer index,String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
        jedis.select(index);
		boolean rs;
		try {
			rs = jedis.del(key) == 1 ? true : false;
		} catch (Exception e) {
			throw new JedisException("删除单个key异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
		
	}
	public static boolean delete(byte[] key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		boolean rs;
		try {
			rs = jedis.del(key) == 1 ? true : false;
		} catch (Exception e) {
			throw new JedisException("删除byte[] key异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	public static String get(String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		String rs;
		try {
			rs = jedis.get(key);			
		} catch (Exception e) {
			throw new JedisException("获取单个key值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}
	
   public static Map<String, String> getuser(String key) throws JedisException {
        Jedis jedis = JedisConnectionManager.getConnectionUser();
        Map<String, String> rs = null;
        try {
//            Map<String, String> map =jedis.hgetAll(key);
            rs = jedis.hgetAll(key);
        } catch (Exception e) {
            throw new JedisException("获取单个key值异常!",e);
        } finally {
            JedisConnectionManager.closeConnectionUser(jedis);
        }        
        return rs;
    }

	public static List<String> get(String[] keys) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		List<String> rs;
		try {
			rs = jedis.mget(keys);
		} catch (Exception e) {
			throw new JedisException("获取String[] keys值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	public static boolean set(String key, String value) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		boolean rsb;
		try {
			String rs = jedis.set(key, value);
			rsb = rs != null && rs.equals("OK") ? true : false;
		}
		catch (Exception e) {
			throw new JedisException("添加单个String key, String value值异常!", e);
		}
		finally {
			JedisConnectionManager.closeConnection(jedis);
		}

		return rsb;
	}

	public static boolean set(String key, String value, int seconds) throws JedisException {
		Jedis jedis = JedisConnectionManager.getConnection();
		boolean rsb;
		try {
			String rs = jedis.setex(key, seconds, value);
			rsb = rs != null && rs.equals("OK") ? true : false;
		} catch (Exception e) {
			throw new JedisException("添加单个String key, String value, int seconds值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rsb;
	}

	/**
	 * @param key
	 * @param value
	 * 临时为解决用户验证的方法
	 * @return
	 * @throws JedisException
	 */
	public static boolean setUser(String key, String value) throws JedisException {
        Jedis jedis = JedisConnectionManager.getConnectionUser();
        boolean rsb;
        try {
            String rs = jedis.set(key, value);
            rsb = rs != null && rs.equals("OK") ? true : false;
        }
        catch (Exception e) {
            throw new JedisException("添加单个String key, String value值异常!", e);
        }
        finally {
            JedisConnectionManager.closeConnectionUser(jedis);
        }
        return rsb;
	 }

    /**
     * @param key
     * @param value
     * @param seconds
     * 临时为解决用户验证的方法
     * @return
     * @throws JedisException
     */
    public static boolean expireUser(String key, int seconds) throws JedisException {
        Jedis jedis = JedisConnectionManager.getConnectionUser();
        boolean rsb;
        try {
            Long rs = jedis.expire(key, seconds);
            rsb = rs == 1 ? true : false;
        } catch (Exception e) {
            throw new JedisException("添加单个String key, String value, int seconds值异常!",e);
        } finally {
            JedisConnectionManager.closeConnectionUser(jedis);
        }	        
        return rsb;
    }
	
	public static boolean set(byte[] key, byte[] value) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		boolean rsb;
		try {
			String rs = jedis.set(key, value);
			rsb = rs != null && rs.equals("OK") ? true : false;
		} catch (Exception e) {
			throw new JedisException("添加byte[] key, byte[] value值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rsb;
	}

	public static boolean set(byte[] key, byte[] value, int second) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		boolean rsb;
		try {
			String rs = jedis.setex(key, second, value);
			rsb = rs != null && rs.equals("OK") ? true : false;
		} catch (Exception e) {
			throw new JedisException("添加byte[] key, byte[] value, int second值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rsb;
	}

	public static byte[] get(byte[] key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		byte[] rs;
		try {
			rs = jedis.get(key);
		} catch (Exception e) {
			throw new JedisException("获取byte[] key值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	public static long hset(String key, String field, String value) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		long rs;
		try {
			rs = jedis.hset(key, field, value);
		} catch (Exception e) {
			throw new JedisException("设置HASH的某FIELD为某值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}
	public static long hset(Integer index,String key, String field, String value) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
	    jedis.select(index);
		long rs;
		try {
			rs = jedis.hset(key, field, value);
		} catch (Exception e) {
			throw new JedisException("设置HASH的某FIELD为某值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}
	public static Map<String, String> hgetAll(String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		Map<String, String> rs;
		try {
			rs = jedis.hgetAll(key);
		} catch (Exception e) {
			throw new JedisException("获取HASH的所有key和value异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	public static boolean hmset(String key, Map<String, String> map) throws JedisException {

		Jedis jedis = JedisConnectionManager.getNewConnection();
		String rs;
		try {
			rs = jedis.hmset(key, map);
		} catch (Exception e) {
			throw new JedisException("设置key和HASH值异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs != null && rs.equals("OK") ? true : false;
	}
	
	public static boolean hmset(Integer index, String key, Map<String, String> map) throws JedisException {
	    
	    Jedis jedis = JedisConnectionManager.getConnection();
	    String rs;
	    try {
	        jedis.select(index);
	        rs = jedis.hmset(key, map);
	    } catch (Exception e) {
	        throw new JedisException("设置key和HASH值异常!",e);
	    } finally {
	        JedisConnectionManager.closeConnection(jedis);
	    }
	    
	    return rs != null && rs.equals("OK") ? true : false;
	}
	
	
	public static List<String> hmget(String key,String... fields) throws JedisException{
	    Jedis jedis = JedisConnectionManager.getConnection();
	    try {
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            throw new JedisException("获取key对应的fields异常",e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
	}

	public static List<String> hmget(String key,int database,String... fields) throws JedisException{
	    Jedis jedis = JedisConnectionManager.getConnection(database);
	    try {
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            throw new JedisException("获取key对应的fields异常",e);
        } finally {
            JedisConnectionManager.closeConnection(jedis,database);
        }
	}
	
	/**
	 * 
	 * @Title:        incr 
	 * @Description:  TODO(这里用一句话描述这个方法的作用) 
	 * @param:        @param key
	 * @param:        @return
	 * @param:        @throws JedisException    
	 * @return:       long    
	 * @throws 
	 * @author        Administrator
	 * @Date          2015-4-13 下午2:22:02
	 */
	public static long incr(String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		long rs;
		try {
			rs = jedis.incr(key);
		} catch (Exception e) {
			throw new JedisException("为key对应的整数值自增1异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	public static long incrBy(String key, long value) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		long rs;
		try {
			rs = jedis.incrBy(key, value);
		} catch (Exception e) {
			throw new JedisException("为key对应的整数值自增value异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	public static long decr(String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		long rs;
		try {
			rs = jedis.decr(key);
		} catch (Exception e) {
			throw new JedisException("为key对应的整数值-1异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	public static long decrBy(String key, long value) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		long rs;
		try {
			rs = jedis.decrBy(key, value);
		} catch (Exception e) {
			throw new JedisException("为key对应的整数值-value异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}
	
	/**
	 * 
	 * @Title:        getlPop 
	 * @Description:  获取list中的第一个元素
	 * @param:        @param key
	 * @param:        @return
	 * @param:        @throws JedisException    
	 * @return:       String    
	 * @throws 
	 * @author        Administrator
	 * @Date          2015-4-13 下午12:58:40
	 */
	public static String getLPop(String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		String rs;
		try {
			rs = jedis.lpop(key);
		} catch (Exception e) {
			throw new JedisException("获取list中第一个元素异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}

	/**
	 * 
	 * @Title:        setRPush 
	 * @Description:  向缓存List中尾部添加一个元素
	 * @param:        @param key
	 * @param:        @param value
	 * @param:        @return
	 * @param:        @throws JedisException    
	 * @return:       Long    
	 * @throws 
	 * @author        Administrator
	 * @Date          2015-4-13 下午2:21:07
	 */
	public static Long setRPush(String key, String value) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		Long rs;
		try {
			rs = jedis.rpush(key, value);
		} catch (Exception e) {
			throw new JedisException("向list尾部添加一个元素异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rs;
	}
	

	/**
	 * 判断redis中是否存在
	 * @param key
	 * @return
	 * @throws JedisException
	 */
	public static Boolean existsKey(String key) throws JedisException {

		Jedis jedis = JedisConnectionManager.getConnection();
		Boolean exists;
		try {
			exists = jedis.exists(key);
		} catch (Exception e) {
			throw new JedisException("判断key是否存在出异常！",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return exists;
	}
	
	/**
	 * @param key
	 * @param seconds 过期时间
	 * @return
	 * @throws JedisException
	 */
	public static boolean expire(String key, int seconds) throws JedisException {
		Jedis jedis = JedisConnectionManager.getConnection();
		boolean rsb;
		try {
			long rs = jedis.expire(key, seconds);
			rsb = rs == 1 ? true : false;
		} catch (Exception e) {
			throw new JedisException("设置key过期时间异常!",e);
		} finally {
			JedisConnectionManager.closeConnection(jedis);
		}
		
		return rsb;
	}
	
    public static void sadd(String key, String... elems) throws JedisException {
        Jedis jedis = JedisConnectionManager.getConnection();
        try {
            jedis.sadd(key, elems);
        } catch (Exception e) {
            throw new JedisException("sadd 新增异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }

    }
    
    public static void sadd(Integer index, String key, String... elems) throws JedisException {
        Jedis jedis = JedisConnectionManager.getNewConnection();
        try {
            jedis.select(index);
            jedis.sadd(key, elems);
        } catch (Exception e) {
            throw new JedisException("sadd 新增异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
        
    }
    
    
    public static Set<String> smembers(String key) throws JedisException{
        Jedis jedis = JedisConnectionManager.getConnection();
        try {
            return jedis.smembers(key);
        } catch (Exception e) {
            throw new JedisException("smembers异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
    }
    
	public static Set<String> smembers(String key,int database) throws JedisException{
	    Jedis jedis = JedisConnectionManager.getConnection(database);
	    try {
            return jedis.smembers(key);
        } catch (Exception e) {
            throw new JedisException("smembers异常",e);
        } finally {
            JedisConnectionManager.closeConnection(jedis,database);
        }
	}
    
    public static Set<String> sinter(final String... keys) throws JedisException{
        Jedis jedis = JedisConnectionManager.getConnection();
        try {
            return jedis.sinter(keys);
        } catch (Exception e) {
            throw new JedisException("sinter异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
    }
    public static Long sinterstore(Integer index,String key,final String... keys) throws JedisException{
        Jedis jedis = JedisConnectionManager.getConnection();
        jedis.select(index);
        
        try {
            return jedis.sinterstore(key, keys);
        } catch (Exception e) {
            throw new JedisException("sinter异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
    }
    public static Set<String> sunion(final String... keys) throws JedisException{
        Jedis jedis = JedisConnectionManager.getConnection();
        try {
            return jedis.sunion(keys);
        } catch (Exception e) {
            throw new JedisException("sunion异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
    }
    
    public static Set<String> sunion(Integer index,final String... keys) throws JedisException{
        Jedis jedis = JedisConnectionManager.getConnection();
        jedis.select(index);
        try {
            return jedis.sunion(keys);
        } catch (Exception e) {
            throw new JedisException("sunion异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
    }
    
    public static Long sunionstore(Integer index,String key,final String... keys) throws JedisException{
        Jedis jedis = JedisConnectionManager.getConnection();
        jedis.select(index);
        try {
            return jedis.sunionstore(key,keys);
        } catch (Exception e) {
            throw new JedisException("sunion异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
    }
    
    public static Long scard(Integer index,String key) throws JedisException{
        Jedis jedis = JedisConnectionManager.getConnection();
        jedis.select(index);
        try {
            return jedis.scard(key);
        } catch (Exception e) {
            throw new JedisException("sunion异常!", e);
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
    }
	public static void main(String[] args) throws JedisException {

		 Jedis jedis = JedisConnectionManager.getConnection();
		 try {
		 System.out.println(jedis.setex("sbbbbb", -1, "555"));
		
		 } finally {
		 JedisConnectionManager.closeConnection(jedis);
		 }
//		JedisClient.set("11", "22");
//		System.out.print(JedisClient.get("11"));
		System.out.println(System.currentTimeMillis());
	}

}

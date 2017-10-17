package cn.rongcapital.mkt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.service.SegmentManageCalService;


@Service
public class SegmentManageCalServiceImpl implements SegmentManageCalService {
    
    private static Logger logger = LoggerFactory.getLogger(SegmentManageCalServiceImpl.class);

	@Override
	public void sinterstore(Integer index, String descKey, String... keys) {

		try {
			JedisClient.sinterstore(index, descKey, keys);
		} catch (JedisException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sunionstore(Integer index, String descKey, String... keys) {
		try {
			JedisClient.sunionstore(index, descKey, keys);
		} catch (JedisException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Long scard(Integer index, String key) {
		try {
			return JedisClient.scard(index, key);
		} catch (JedisException e) {
			e.printStackTrace();
		}
		return 0l;
	}

	@Override
	public void deleteTempKey(Integer index, String... keys) {

		try {
			JedisClient.delete(index, keys);
		} catch (JedisException e) {
			e.printStackTrace();
		}
	}
}

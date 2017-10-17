package cn.rongcapital.mkt.service;

/**
 * 细分地图 性别
 * @author lijinkai
 * 2016-11-09
 */
public interface SegmentManageCalService {
    public void sinterstore(Integer index,String descKey,final String... keys);
    public void sunionstore(Integer index,String descKey,final String... keys);
    public Long scard(Integer index,String key);
    public void deleteTempKey(Integer index,String... keys);
}

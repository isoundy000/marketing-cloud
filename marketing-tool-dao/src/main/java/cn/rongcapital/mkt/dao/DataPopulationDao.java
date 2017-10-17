/*************************************************
 * @功能简述: DAO接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.vo.out.TagAudienceDownloadOut;
import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.vo.in.SegmentSearchIn;
import cn.rongcapital.mkt.vo.out.SegmentSearchDownloadOut;
public interface DataPopulationDao extends BaseDao<DataPopulation>, BaseDataFilterDao<DataPopulation>{
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象list;
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListBycustomMap(Map<String,Object> paramMap);
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象总数
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);
    
    /**
     * @功能简述 : 根据原始数据更新DataPopulation的数据
     * @author xukun
     */
    void cleanAndUpdateByOriginal(Map<String, List<DataPopulation>> paramMap);

    /**
     * 批量更新数据状态
     * @param idList
     * @return
     */
	int updateStatusByIds(@Param("list") List<Integer> idList, @Param("status") Integer status);
	
	
    /**
     * @功能简述 : 根据手机号查询data_population的数据
     * @param mobile
     * 				手机号
     * @return DataPopulation
     * 				data_population对象
     * 
     */
	DataPopulation getDataPopulationByMobile(@Param("mobile") String mobile);
	
    /**
     * @功能简述 : 根据手机号更新data_population的数据
     * @param dataPopulation
     * 				data_population对象
     * @return int
     * 				更新记录条数
     * 
     */
	int updateDataPopulation(DataPopulation dataPopulation);
	
	/**
	 * 
	 * @param dataPopulation
	 * @return
	 */
	int updateDataPopulationByPubIdAndOpenId(DataPopulation dataPopulation);

	/**
	 * @功能简述 : 根据IdList获取KeyidList(keyidList即dataparty的Idlist)
	 * @param memberKeyidList
	 * @return List
	 */
	List<Integer> selectKeyidListByIdList(@Param("idList") List<Integer> memberKeyidList);

	/**
	 * @功能简述 : mkt.audience.search.download
	 * @param 
	 * @return List
	 */
	List<DataPopulation> searchDataByAudienceId(@Param("audience_id") Integer audience_id);
	/**
	 * @功能简述 : mkt.segment.search.get
	 * @param 
	 * @return List
	 */
	List<DataPopulation> segmentSearch(SegmentSearchIn searchIn);
	
	/**
	 * mkt.segment.search.download 下载
	 * @param head_ids
	 * @return
	 */
	List<SegmentSearchDownloadOut> getSegmentSearchDownload(@Param("headidList") List<Integer> head_ids);

	/**
	 * @功能简述 : 查询产生主数据的条数
	 * @param      distinctKeyidList
	 */
	Integer selectCountFromContactList(@Param("idList") List<Integer> distinctKeyidList);
	
	/**
	 * 描述：根据keyid获取渠道名和渠道大类
	 * 
	 * @param keyId
	 * @return
	 */
	Map<String, Object> selectMediaChannel(@Param("keyId") Integer keyId);

	/**
	 * 标签人群得下载下载
	 * @param idList
	 * @return
	 */
	List<TagAudienceDownloadOut> getTagAudienceDownloadList(@Param("idList") List<Integer> idList);
	
	/**
	 * 更新Keyid
	 * newkeyId-5
	 * oldkeyId-6
	 */
	void updateDataPopulationKeyid(Map<String,Object> map);
	
}
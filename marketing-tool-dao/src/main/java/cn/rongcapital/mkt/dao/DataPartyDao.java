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

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.po.*;
import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.vo.in.SegmentSearchIn;
import cn.rongcapital.mkt.vo.out.TagAudienceDownloadOut;

public interface DataPartyDao extends BaseDao<DataParty>, BaseDataFilterDao<DataParty>{
	
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
     * mkt.data.main.count.get
     * 
     * @功能简述 : 获取主数据条数
     * @author nianjun
     * @return map
     */
    public Map<String, Object> selectMainCount(Map<String, Object> paramMap);
    
//    /**
//     * mkt.data.main.delete
//     * 
//     * @功能简述 : 删除某条主数据
//     * @author nianjun
//     * @return map
//     */
//    public int logicDeleteById(DataParty dataParty);

    /**
     *
     * @功能简述 : 根据主键获取某条主数据
     * @param id
     * @author zhuxuelong
     * @return DataParty
     */
    public DataParty getDataById(Integer id);

    /**
     * mkt.data.main.searchbyid.get
     *
     * @功能简述 : 根据类型和编号，搜索获取主数据(人群)
     * @author nianjun
     * @return map
     */
    Map<String,Object> selectAudienceDetail(Map<String, Object> map);

    
    /**
     * mkt.audience.search.get
     *
     * @功能简述 : 在人群中查找
     * @author xukun
     * @return map
     */
    List<Map<String,Object>> selectListByKeyName(String audience_name);

	/**
	 * mkt.audience.search.get
	 *
	 * @功能简述 : 在指定人群中查找
	 * @author xukun
	 * @return map
	 */
    List<Map<String,Object>> selectListByNameInList(Map<String,Object> paramMap);

    
	/**
	 * @功能简述 : 获取dataPartyIds
	 * @author nianjun
	 * @return map
	 */
	List<Long> selectDataPartyIdsByMappinKeyIds(List<Long> mappingIdList);
	
	/**
     * @功能简述 : 根据自定义标签获取覆盖人群
     * @author nianjun
     * @return map
     */
	List<DataParty> selectCustomAudiencesByTagId(Map<String, String> paramMap);
	

    /**
     * 批量插入数据
     * @param list
     * @return
     */
	int batchInsert(@Param("list") List<DataParty> list);

    /**
     * 批量更新数据状态
     * @param idList
     * @return
     */
    int updateStatusByIds(@Param("list") List<Integer> idList, @Param("status") Integer status);

	/**
	 * 同步微信数据到主数据
	 * @param idList
	 * @return
	 */
	Integer batchInsertWechatDatas(List<Map<String, Object>> notSyncWechatMemberList);

	/**
	 * 根据mappingId选择dataPartyId
	 * @param idList
	 * @return
	 */
	Integer selectIdByMappingId(@Param("mapping_keyid") Long id);
	
	/**
	 * 根据手机号更新data_party表数据
	 * @param dataParty 
	 * @return 更新数据条数
	 */
	int updateDataPartyInfo(DataParty dataParty);

	/**
	 * 查询指定Id集合所包含的source和该source对应的个数
	 * @param dataPartyIdList
	 * @return 返回source及该source对应的个数
	 */
	List<DataCountBySource> selectDataSourceAndSourceCount(@Param("idList") List<Integer> dataPartyIdList);

    /**
     * 根据手机号更新data_party表数据
     *
     * @param dataParty
     * @return 月份与对应的count值
     */
    List<HomePageMonthlyCount> selectMonthlyCount(Map<String, Date> paramMap);

    /**
     * 根据source获取每种source的count值
     *
     * @param dataParty
     * @return source与对应的count值
     */
    List<HomePageSourceGroupCount> selectSourceGroupCount();
    
    /**
     * 未经过数据清洗的，从文件接入的数据量+微信接入的数据量的数据数量
     *
     * @return Integer
     */
    Integer selectTotalOriginalCount();
    
    /**
     * 查询手机号不为空的数据量
     *
     * @return Integer
     */
    Integer selectNotNullMobile();
    
    /**
     * 根据pubId与openId查询dataParty的id
     *
     * @return Integer
     */
    Integer selectDataPartyIdbyWechatInfo(String pubId , String openId);

	/**
	 * 根据bitmap获得主键的id
	 *
	 * @return Integer
	 */
	Integer getDataPartyIdByKey(DataParty dataParty);
	
	/**
	 * @Title: getPubUserCount   
	 * @Description: 获取公众号用户数量  
	 * @param: @param map
	 * @param: @return      
	 * @return: Integer      
	 * @throws
	 */
	Integer getPubUserCount(Map<String, Object> map);

	/**
	 * @功能简述 : 根据DataPartyIdList选取去过重的MobileList
	 * @author yunfeng
	 * @return map
	 */
	List<String> selectDistinctMobileListByIdList(@Param("idList") List<Long> dataPartyIdList);
	
	
	/**
     * @功能简述 : 根据联系人表单Id查找所生成的主数据
     * @author Lijinkai
     * @return list
     */
	List<DataParty> selectListByContactId(Integer contactId);
	
	List<Segment> selectSegmentByIdList(@Param("idList") List<String> dataPartyIdList);
	
	/**
	 * @功能简述 : mkt.segment.search.get
	 * @param 
	 * @return List
	 */
	//List<DataParty> segmentSearch(SegmentSearchIn searchIn);
	List<Map<String,Object>> segmentSearch(SegmentSearchIn searchIn);
	/**
     * 标签人群得下载下载
     * @param idList
     * @return
     */
    List<TagAudienceDownloadOut> getTagAudienceDownloadList(@Param("idList") List<Integer> idList);

	/**
	 * 根据dataPartyId选择Id和Mobile
	 * @param dataPartyIdList
	 * @return
	 */
	List<AudienceIDAndMobilePO> selectCacheAudienceListByIdList(@Param("idList") List<Long> dataPartyIdList);
	
    /**
     * 获取当前最大Id
     * @return
     */
    Integer getMaxId();
    
    /**
     * 获取重复的数据BitmapKeys
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> getRepeatDataByBitmapKeys(Map<String,Object> map);
    
    /**
     * 获取重复的数据Ids
     * @param paramMap
     * @return
     */
    List<Integer> getIdsByRepeatByBitmapKeys(DataParty dataParty);
    
    /**
     * 删除主数据
     * @param id
     */
    void deleteDataById(Integer id);
    
    
    List<String> getBitmaps(Integer id);
    
}

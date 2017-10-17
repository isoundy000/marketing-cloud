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

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.vo.weixin.WXFansListVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WechatMemberDao extends BaseDao<WechatMember>{

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
	 * 批量插入粉丝人数
	 * @param paramMap
	 * @return list
	 */
	void batchInsertFans(List<Map<String, Object>> fansList);
	
	/**
	 * 批量插入粉丝人数
	 * @param wechatMembers
	 */
	void batchInsert(List<WechatMember> wechatMembers);

	/**
	 * 判断这个人是否已经被保存
	 * @param paramMap
	 * @return list
	 */
	Long selectIdByGroupIdAndWxAcct(Map<String, Object> paramMap);

	/**
	 * 批量插入微信好友
	 * @param paramMap
	 * @return list
	 */
	void batchInsertContacts(List<Map<String, Object>> paramContacts);

	/**
	 * 根据组名获取组员的唯一标识
	 * @param paramMap
	 * @return list
	 */
	List<Long> selectIdListByGroupId(List<WechatAssetGroup> wechatAssetGroups);

	/**
	 * 选取微信人群的详细信息
	 * @param paramMap
	 * @return list
	 */
	List<WechatMember> selectPeopleDetails(List<WechatAssetGroup> wechatAssetGroups);

	/**
	 * 批量插入微信组成员信息
	 * @param paramMap
	 * @return list
	 */
	void batchInsertGroupMember(List<Map<String, Object>> paramGroupMembers);

	/**
	 * 获取一个组好下的总人数
	 * @param paramMap
	 * @return list
	 */
	Integer selectGroupMemeberCount(Map<String,Object> paramMap);

	/**
	 * 搜索微信小组中人的信息
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectSearchInfo(Map<String,Object> paramMap);

	/**
	 * 根据pub_id和open_id判断这个人是否已经被保存
	 * @param paramMap
	 * @return list
	 */
	List<Long> selectIdByPubIdAndOpenId(Map<String, Object> paramMap);

	/**
	 * 删掉与这个uin相关的所有数据
	 * @param paramMap
	 * @return list
	 */
	void deleteRecordByUin(String uin);

	/**
	 * 选出尚未同步到dataParty的数据
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectNotSyncWechatMemberList();

	/**
	 * 更新数据已经同步到data_party表中的数据标志
	 * @param paramMap
	 * @return list
	 */
	void updateSyncDataMark(List<Long> list);

	/**
	 * 选取未同步的微信粉丝
	 * @return Integer
	 */
	Integer selectedNotSyncCount();

	/**
	 * 根据IdList选出相应的对应到datapopulation的keyidList
	 * @return Integer
	 */
	List<Integer> selectKeyidListByIdList(@Param("idList") List<Long> idLists);
	
	/**
	 * @Title: deleteAllUser   
	 * @Description: 删除所有粉丝
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int deleteFansByWxcode(List<Map<String, Object>> fansList);
	
	/**
	 * @Title: deleteAllUser   
	 * @Description: 删除所有粉丝
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int deleteFansByVO(WXFansListVO wxFansListVO);
	
	/**
	 * 删除粉丝
	 * @param paramMap
	 * @return
	 */
	int deleteWechatMembersByIdsAndPubId(Map<String, Object> paramMap);
	
	/**
	 * 
	 * @param idLists
	 * @return
	 */
	List<WechatMember> selectListByIdListNoSelected(List<Long> idLists);
		
}
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
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.vo.ImgAsset;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ImgTextAssetDao extends BaseDao<ImgTextAsset>{
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
	 * 通过ownerName查询图文资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectListByName(Map<String,Object> paramMap);

	/**
	 * 通过ownerName查询图文资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectListByNameAndType(Map<String,Object> paramMap);

	/**
	 * 通过imgtext_id删除图文资产
	 * @param paramMap
	 * @return list
	 */
	int logicDeleteAssetById(Map<String, Object> paramMap);

	/**
	 * 查询所有图文资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectListAll(Map<String, Object> paramMap);
	/**
	 * 根据type查询所有资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectListByType(Map<String, Object> paramMap);
	/**
	 * 根据图文资产菜单列表
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectMenuList(Map<String, Object> paramMap);

	/**
	 * 根据图文资产菜单列表
	 * @return list
	 */
	List<Map<String,Object>> selectImgtextAssetCountByType();

	/**
	 * 从大连那边批量导入图文资产
	 * @return list
	 */
	void batchInsertTuwen(@Param("list") List<ImgTextAsset> imgTextAssetList);

	/**
	 * 将托管的图文资产插入数据库
	 * @return list
	 */
	void insertHostImg(Map<String, Object> paramMap);

	/**
	 * 根据material_id判断图文是否被保存
	 * @return list
	 */
	Integer selectImgtextIdByMaterialId(@Param("material_id") String materialId);
	
	/**
	 * 带创建时间和更新时间的插入方法
	 * @param imgTextAsset
	 * @return
	 */
	int insertWithDate(ImgTextAsset imgTextAsset);
	
	/**
	 * 带创建时间和更新时间的更新方法
	 * @param imgTextAsset
	 * @return
	 */
	int updateByIdWithDate(ImgTextAsset imgTextAsset);
	
	/**
	 * 根据pubId type wxType searchKey 查询图文信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String,Object>> selectListBySearchKeyLike(Map<String, Object> paramMap);
	
	/**
	 * 根据pubId type wxType searchKey 查询图文信息数量
	 * @param paramMap
	 * @return
	 */
	int selectListBySearchKeyLikeCount(Map<String, Object> paramMap);
	
	/**
	 * @param pubId
	 * 修改公众号下的图文消息的WechatStatus，是否被公众号删除了
	 */
	void batchUpdateWechatStatusByPubId(String pubId);
	/**
	 * @param pubId
	 * 删除公众号下的图文消息
	 */
	void batchDeleteWechatStatusByPubId(String pubId);
	
    /**
     * 根据pubId type wxType searchKey 查询 media_id
     * 
     * @param paramMap
     * @return
     * @author shuiyangyang
     * @Date 2016-11-17
     */
    List<String> selectMaterialIdListBySearchKeyLike(ImgTextAsset imgTextAsset);

    /**
     * 根据pubId type wxType searchKey 查询图文信息数量 media_id去重
     * 
     * @param paramMap
     * @return
     * @author shuiyangyang
     * @Date 2016-11-17
     */
    int selectMaterialIdListBySearchKeyLikeCount(ImgTextAsset imgTextAsset);

    /**
     * 根据pubId type wxType searchKey media_id 查询图文信息
     * 
     * @param imgTextAsset
     * @return
     * @author shuiyangyang
     * @Date 2016-11-17
     */
    List<Map<String, Object>> selectListBySearchKey(ImgTextAsset imgTextAsset);
}
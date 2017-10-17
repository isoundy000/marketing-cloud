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
import cn.rongcapital.mkt.po.OriginalDataCustomerTags;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OriginalDataCustomerTagsDao extends BaseDao<OriginalDataCustomerTags>{

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
	 * 批量插入数据
	 * @param List
	 * @return int
	 */
	int batchInsertUploadFileData(ArrayList<Map<String, Object>> insertList);

	/**
	 * 根据FileUnique获取id列表
	 * @param paramMap
	 * @return int
	 */
	List<Long> selelctIdListByFileUnique(Map<String, Object> paramMap);

	/**
	 * 根据FileUnique更新数据状态
	 * @param fileUnique
	 * @return
	 */
	int updateStatusByFileUnique(@Param("fileUnique") String fileUnique, @Param("status") Integer status);

	/**
	 * 选取唯一标识列表
	 * @param paramMap
	 * @return
	 */
	List<Integer> selelctIdentifierListByFileUnique(Map<String, Object> paramMap);
}
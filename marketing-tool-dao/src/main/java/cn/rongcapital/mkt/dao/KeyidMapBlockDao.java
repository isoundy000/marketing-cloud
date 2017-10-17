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
import cn.rongcapital.mkt.po.KeyidMapBlock;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface KeyidMapBlockDao extends BaseDao<KeyidMapBlock>{
	
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
	 * @功能简述: 查询对象list,只要不为NULL与空则为条件,属性值之间and连接
	 *
	 * @return: List<T>
	 */
	List<KeyidMapBlock> selectKeyidMapBlockList();

	/**
	 * @功能简述: 根据seq获取相应的field字段
	 * @Param:keyidMapBlock
	 * @return: KeyidMapBlock
	 */
	KeyidMapBlock selectKeyIdMapBolckBySeq(KeyidMapBlock keyidMapBlock);

	List<KeyidMapBlock> selectListBySequenceList(@Param("sequenceList") List<Integer> keyidListSequence);

	/**
	 * @功能简述: 根据名称选择KeyIdMapBlock
	 * @Param:keyidMapBlock
	 * @return: KeyidMapBlock
	 */
	List<KeyidMapBlock> selectListByCodeList(@Param("fieldCodeList") ArrayList<String> contactTemplateKeys);
}
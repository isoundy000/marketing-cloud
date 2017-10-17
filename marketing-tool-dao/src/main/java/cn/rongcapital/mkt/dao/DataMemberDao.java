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

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.DataMember;
import org.apache.ibatis.annotations.Param;

public interface DataMemberDao extends BaseDao<DataMember>, BaseDataFilterDao<DataMember>{
	
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
     * @功能简述 : 根据原始数据更新DataMember的数据
     * @author nianjun
     */
    void cleanAndUpdateByOriginal(Map<String, List<DataMember>> paramMap);

	/**
	 * 批量更新数据状态
	 * @param idList
	 * @return
	 */
	int updateStatusByIds(@Param("list") List<Integer> idList, @Param("status") Integer status);
    
	/**
	 * 更新Keyid
	 * newkeyId-5
	 * oldkeyId-6
	 */
	void updateDataMemberKeyid(Map<String,Object> map);
}
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
import cn.rongcapital.mkt.po.ImportDataHistory;
import org.apache.ibatis.annotations.Param;

public interface ImportDataHistoryDao extends BaseDao<ImportDataHistory>{
	
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
	 * 获取数据接入的总览信息
	 * @return map
	 */
	Map<String,Object> selectMigrationFileGeneralInfo();
	
	/**
	 * mkt.data.quality.count.get
	 * 
	 * @功能简述 : 获取数据接入条数
	 * @author nianjun
	 * @return map
	 */
    public Map<String, Object> selectQualityCount();
    
    /**
	 * mkt.data.unqualified.count.get
	 *
	 * @功能简述 : 获取非法数据条数
	 * @author nianjun
	 * @return map
	 */
	public Map<String, Object> selectUnqualifiedCount(ImportDataHistory paramImportDataHistory);

	/**
	 * mkt.data.unqualified.count.get
	 *
	 * @功能简述 : 获取非法数据条数
	 * @author nianjun
	 * @return map
	 */
	public void insertFileUnique(Map<String,Object> paramMap);

	/**
	 *
	 * @功能简述 : 根据FileUnique获取相应的数据条数
	 * @author nianjun
	 * @return map
	 */
	List<Map<String,Object>> selectTotalRowsAndFileType(Map<String, Object> paramMap);

	ImportDataHistory queryByFileUnique(@Param("fileUnique") String fileUnique);
	
	/**
     * @功能简述: 获取表中所有列
     * @return: List<String>
     */
    List<String> selectColumns();

}
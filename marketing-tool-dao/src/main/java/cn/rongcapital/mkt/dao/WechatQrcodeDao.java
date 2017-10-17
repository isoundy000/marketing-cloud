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
import cn.rongcapital.mkt.po.WechatQrcode;

public interface WechatQrcodeDao extends BaseDao<WechatQrcode>{
	
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
	 * 根据输入的二维码名称模糊查询表wechat_qrcode
	 * @param t
	 * @return
	 */
	List<WechatQrcode> fuzzySearchQrcodeName(WechatQrcode t);
	
	/**
	 * 同过Id删除数据
	 * @param t
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	int deleteById(WechatQrcode t);
	
	/**
	 * 通过BatchId更新 expiration_time qrcode_tag_ids qrcode_status
	 * @param t
	 * @return
	 */
	int updataByBatchId(WechatQrcode t);
	
	/**
	 * 通过Id更新status
	 * @param t
	 * @return
	 * @author zhouqi
	 * @Date 2016.08.29
	 */
	int updateStatusById(WechatQrcode t);
	
	
	List<WechatQrcode> selectListExpirationTime(Map<String,Object> paramMap);
	
	/**
	 * 更新数据的状态为失效
	 *
	 * @param WechatQrcode
	 * @return 
	 * @author congshulin
	 */
	int updateStatusByExpirationTime(WechatQrcode t);
	
	/**
	 * 根据渠道Id查询已经使用渠道的二维码数量
	 * @return
	 */
	int selectUsedChannelCountBychCode(Integer chCode);
	
	/**
	 * 根据公众号修改二维码取消授权
	 * @param pubId
	 */
	void unauthorizedByPubId(String pubId);
	
	/**
	 * 根据公众号修改二维码授权
	 * @param pubId
	 */
	void authorizedByPubId(String pubId);
}
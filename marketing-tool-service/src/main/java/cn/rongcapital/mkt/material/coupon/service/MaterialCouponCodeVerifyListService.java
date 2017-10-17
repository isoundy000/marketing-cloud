/*************************************************
 * @功能简述: 获取核销对账数据
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/7
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;



public interface MaterialCouponCodeVerifyListService {

    /**
     * 获取核销对账数据
     * 
     * @param id 优惠券主键
     * @param blur_search 查询关键字
     * @param receive_status 收到状态
     * @param verify_status 使用状态
     * @param expire_status 过期状态
     * @param index 页码
     * @param size 单页最大数量
     * @return BaseOutput
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    public BaseOutput listMaterialCouponCodeVerfy(Long id, String blurSearch, String receiveStatus,
                    String verifyStatus, String expireStatus, Integer index, Integer size);

}

 /*************************************************
 * @功能及特点的描述简述:获取单个物料的所有可接入配置属性
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：XXX系统
 * @author: liuhaizhan
 * @version: 版本
 * @date(创建、开发日期)：2016年12月6日
 * 最后修改日期：2016年12月6日
 * @复审人:
 *************************************************/ 
package cn.rongcapital.mkt.material.service;

import cn.rongcapital.mkt.material.po.MaterialAccessProperty;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponPropertiesService {
  
    /**
     * @author liuhaizhan
     * @功能简述: 获取单个物料所有可接入配置属性
     * @param 
     * @return 
     */
BaseOutput getProperties(MaterialAccessProperty materialAccessProperty);
}

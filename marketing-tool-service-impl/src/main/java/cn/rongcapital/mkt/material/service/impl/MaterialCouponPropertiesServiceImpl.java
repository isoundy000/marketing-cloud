/*************************************************
 * @功能及特点的描述简述: 获取单个物料所有可接入配置属性
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016年12月13日 
 * @date(最后修改日期)：2016年12月13日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.material.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.MaterialAccessPropertyDao;

import cn.rongcapital.mkt.material.coupon.vo.out.PropertiesOut;
import cn.rongcapital.mkt.material.po.MaterialAccessProperty;
import cn.rongcapital.mkt.material.service.MaterialCouponPropertiesService;
import cn.rongcapital.mkt.vo.BaseOutput;


@Service
public class MaterialCouponPropertiesServiceImpl implements MaterialCouponPropertiesService {

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.rongcapital.mkt.service.MaterialCouponProperties#getProperties(cn.rongcapital.mkt.po.MaterialAccessProperty)
     */
   
    @Autowired
    private MaterialAccessPropertyDao materialAccessPropertyDao;

    @Override
    public BaseOutput getProperties(MaterialAccessProperty materialAccessProperty) {
        // TODO Auto-generated method stub

        List<MaterialAccessProperty> data = materialAccessPropertyDao.selectList(materialAccessProperty);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        if (!CollectionUtils.isEmpty(data)) {
            
             List prout =new ArrayList();
             PropertiesOut  pr=null;
            for (MaterialAccessProperty map : data) {
                pr=new PropertiesOut();
                BeanUtils.copyProperties(map,pr);
                prout.add(pr);
            }

            result.setTotal(prout.size());
            result.setData(prout);
            
        }

        return result;
    }

}

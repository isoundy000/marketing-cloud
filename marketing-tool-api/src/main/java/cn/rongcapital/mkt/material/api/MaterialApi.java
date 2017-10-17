/*************************************************
 * @功能简述: API接口物料主类
 * @项目名称: marketing cloud
 * @see:
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/12/06
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.api;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.material.po.MaterialAccessProperty;
import cn.rongcapital.mkt.material.service.MaterialCouponPropertiesService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class MaterialApi {

    @Autowired
    private MaterialCouponPropertiesService  materialCouponPropertiesService;
    
    /**
     * @author liuhaizhan
     * @功能简述: 返回单个物料所有可接入配置属性
     * @param
     * @return
     */
    @GET
    @Path("/mkt.material.coupon.properties")
    public BaseOutput getProperties(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id) {
        MaterialAccessProperty mapro = new MaterialAccessProperty();
        mapro.setMaterialTypeId(id);
        mapro.setStatus((byte) 0);
        return materialCouponPropertiesService.getProperties(mapro);
    }
}
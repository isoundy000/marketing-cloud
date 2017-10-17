package cn.rongcapital.mkt.sms.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MKSmsApi {
    
     
}

package cn.rongcapital.mkt.sms.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialDeleteIn;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktSmsMaterialApi {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsMaterialService smsMaterialService;
	
	/**
	 * 增加或者修改素材接口
	 * @param smsMaterialIn
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/mkt.sms.smsmaterial.saveorupdate")
	public BaseOutput insertOrUpdateSmsMaterial(@Valid SmsMaterialIn smsMaterialIn) throws Exception {
		return smsMaterialService.insertOrUpdateSmsMaterial(smsMaterialIn);
	}

	/**
	 * 删除素材接口
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/mkt.sms.smsmaterial.delete")
	public BaseOutput deleteSmsMaterial(@Valid SmsMaterialDeleteIn smsMaterialDeleteIn) throws Exception {		
		return smsMaterialService.deleteSmsMaterial(smsMaterialDeleteIn);
	}

	
}

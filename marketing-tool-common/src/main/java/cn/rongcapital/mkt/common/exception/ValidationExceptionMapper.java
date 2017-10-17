/*************************************************
 * @功能简述: resteasy参数校验异常处理类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.exception;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ResteasyViolationException> {
	
	/**
	 * @功能简述: 参数校验异常后返回错误码
	 * @param: ResteasyViolationException cex
	 * @return: Response
	 */
	@Override
    public Response toResponse(ResteasyViolationException cex) {
		List<ResteasyConstraintViolation> exceptionList = cex.getViolations();
		String msg = "";
		if(null != exceptionList && exceptionList.size() > 0) {
			ResteasyConstraintViolation exceptionTmp = exceptionList.get(0);
			msg = exceptionTmp.getPath() + exceptionTmp.getMessage();
		}
        BaseOutput error = new BaseOutput(ApiErrorCode.VALIDATE_ERROR.getCode(),ApiErrorCode.VALIDATE_ERROR.getMsg()+":"+msg,0,null);
        return Response.ok().entity(error).build();
    }
}
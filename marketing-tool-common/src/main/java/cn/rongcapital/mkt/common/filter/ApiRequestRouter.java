/*************************************************
 * @功能简述: API接口根据传入的method参数把请求转发到相应接口
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.RedisUserTokenVO;


@Component
@Provider
@PreMatching
public class ApiRequestRouter implements ContainerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());	
	
	/**
	 * @功能简述: 根据传入的method参数把请求转发到相应接口
	 * @param: ContainerRequestContext requestContext
	 * url分为两种，带上user_token和不带user_token的。
	 * 带上user_token的需要去redis数据库校验，校验通过则继续往下走，如果不通过则返回错误信息，前端跳转到登录页面或者错误信息页面
	 * 不带user_token的链接则加上user_token=123 继续往下走
	 * redis校验规则：
	 * @return: Response
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException{
	    RedisUserTokenVO redisUserTokenVO = null;
	    try {	    	
	        redisUserTokenVO = validateUserToken(requestContext);
        } catch (JedisException e) {           
            logger.info(e.getMessage());
        }
	    
        if(false){	       	
//            requestContext.abortWith(Response.status(redisUserTokenVO.getCode()).entity(getBaseOutputBack(redisUserTokenVO)).build());       	
        	ResponseBuilderImpl builder = new ResponseBuilderImpl();
            builder.header("Content-Type", "application/json; charset=utf-8");
            builder.entity(JSONObject.toJSONString(redisUserTokenVO));           
            requestContext.abortWith(builder.build());
       }else{
	   		String url = requestContext.getUriInfo().getPath();
	   		String appId = "";
	   		if(StringUtils.isNotEmpty(url)&&url.contains(ApiConstant.API_PATH)){
	   			if(url.length()>=5){
	   				appId = url.substring(5);
	   			}	   			
		   		if(HttpMethod.GET.equals(requestContext.getMethod()) ||(HttpMethod.POST.equals(requestContext.getMethod()))) { 	   			
		   		    if(redisUserTokenVO.getCode()==0&&StringUtils.isNotEmpty(redisUserTokenVO.getMsg())){
		   		        requestContext.getUriInfo().getQueryParameters().add(ApiConstant.API_USER_TOKEN, ApiConstant.API_USER_TOKEN_VALUE);
		   		    }
		   		   
		   			List<String> pList = requestContext.getUriInfo().getQueryParameters().get(ApiConstant.API_METHOD);
		   			String method = pList==null?null:pList.get(0);
		   			if(StringUtils.isBlank(method)){	   				
//		   				requestContext.abortWith(Response.status(404).entity("Api method not found").build());
		   				ResponseBuilderImpl builder = getBuilder();           
			            requestContext.abortWith(builder.build());
		   			}else{
		   				if(StringUtils.isNotEmpty(appId)){				
			   				URI newRequestURI = requestContext.getUriInfo().getBaseUriBuilder().path(ApiConstant.API_PATH+"/"+method+"/"+appId).build();
			   				requestContext.setRequestUri(newRequestURI);			   				
			   			}else{
			   				URI newRequestURI = requestContext.getUriInfo().getBaseUriBuilder()
			   						.path(ApiConstant.API_PATH+"/"+method).build();
			   				requestContext.setRequestUri(newRequestURI);
			   			}
		   			}		   			
		   		}
		   		
	   		}else{
//	   			requestContext.abortWith(Response.status(404).entity("Api not found").build());	   			
	   			ResponseBuilderImpl builder = getBuilder();         
	            requestContext.abortWith(builder.build());
	   		}
       }
	}
	
	private ResponseBuilderImpl getBuilder(){
		RedisUserTokenVO redisUserTokenVO = new RedisUserTokenVO();
		redisUserTokenVO.setCode(404);
		redisUserTokenVO.setMsg("Api not found");
    	ResponseBuilderImpl builder = new ResponseBuilderImpl();
        builder.header("Content-Type", "application/json; charset=utf-8");
        builder.entity(JSONObject.toJSONString(redisUserTokenVO));
		return builder;        
	}
	
	public BaseOutput getBaseOutputBack(RedisUserTokenVO redisUserTokenVO){
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
	            ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
	List<Object> data = new ArrayList<Object>();
	data.add(JSONObject.toJSONString(redisUserTokenVO));
	baseOutput.setData(data);
	return baseOutput;
	} 
    
	
	
    /**
     * @param requestContext
     * 验证UserToken
     * @return
     * @throws JedisException
     */
    public RedisUserTokenVO validateUserToken(ContainerRequestContext requestContext) throws JedisException{
    	RedisUserTokenVO redisUserTokenVO = this.validateWhiteListOfMethod(requestContext);
    	if(redisUserTokenVO!=null){
    		return redisUserTokenVO;
    	}
        redisUserTokenVO = new RedisUserTokenVO();
        
        String backStr = "";
        MultivaluedMap<String, String> multivaluedMap = requestContext.getUriInfo().getQueryParameters();
        List<String> user_token_pList = multivaluedMap.get(ApiConstant.API_USER_TOKEN);
        String user_token = user_token_pList==null?null:user_token_pList.get(0);
        List<String> user_id_pList = multivaluedMap.get(ApiConstant.API_USER_ID);
        String user_id = user_id_pList==null?null:user_id_pList.get(0);
        String userKey ="user:"+user_id;
        if(StringUtils.isBlank(user_token)){
            
            backStr="&"+ApiConstant.API_USER_TOKEN+"="+ApiConstant.API_USER_TOKEN_VALUE;
            redisUserTokenVO.setCode(0);
            redisUserTokenVO.setMsg(backStr);            
        }else{
            if(StringUtils.isBlank(user_id)){
                
                redisUserTokenVO.setCode(ApiConstant.USER_TOKEN_PARAMS_MISSING);
                backStr="登录验证缺少参数！";
                redisUserTokenVO.setMsg(backStr);
            }else{
                
                
                Map<String, String> user_token_map = JedisClient.getuser(userKey);
                String userValue = user_token_map.get("token");
                if(!user_token.equals(userValue)){
                    redisUserTokenVO.setCode(ApiConstant.USER_TOKEN_LOGIN_CONFLICT);
                    backStr="登录冲突，请重新登录！";
                    redisUserTokenVO.setMsg(backStr);
                }else{
                    redisUserTokenVO.setCode(0);
                    int seconds = 36000;
                    JedisClient.expireUser(userKey, seconds);
                }
            }           
        }
        return redisUserTokenVO;
	}
	
    /**
     * @param requestContext
     * 添加方法白名单
     * @return
     */
    public RedisUserTokenVO validateWhiteListOfMethod(ContainerRequestContext requestContext){
    	RedisUserTokenVO redisUserTokenVO = null;
    	 MultivaluedMap<String, String> multivaluedMap = requestContext.getUriInfo().getQueryParameters();
			List<String> pList = multivaluedMap.get(ApiConstant.API_METHOD);
			String method = pList==null?null:pList.get(0);	       
	        List<String> user_token_pList = multivaluedMap.get(ApiConstant.API_USER_TOKEN);
	        String user_token = user_token_pList==null?null:user_token_pList.get(0);

			if(!StringUtils.isBlank(method)){	
				Map<String,String> whiteMapOfMethod = getWhiteMapOfMethod();
				if(whiteMapOfMethod.containsKey(method)){
					redisUserTokenVO = new RedisUserTokenVO();
			        if(StringUtils.isBlank(user_token)){
			            String backStr="&"+ApiConstant.API_USER_TOKEN+"="+ApiConstant.API_USER_TOKEN_VALUE;			           
			            redisUserTokenVO.setCode(0);
			            redisUserTokenVO.setMsg(backStr);            
			        }else{
			        	redisUserTokenVO.setCode(0);
			        	/**
			        	 * 延长session  出现异常 捕获 继续
			        	 */
			        	List<String> user_id_pList = multivaluedMap.get(ApiConstant.API_USER_ID);
			            String user_id = user_id_pList==null?null:user_id_pList.get(0);
			            String userKey ="user:"+user_id;
	                    int seconds = 36000;
	                    try {
							JedisClient.expireUser(userKey, seconds);
						} catch (JedisException e) {}
			        }
				}
			}
		return redisUserTokenVO;    	
    }
    
    /**
     * 跳过验证的方法白名单
     * @return
     */
    public Map<String,String> getWhiteMapOfMethod(){
    	Map<String,String> whiteMapOfMethod = new HashMap<String,String>();
    	whiteMapOfMethod.put("mkt.contact.list.pv", "mkt.contact.list.pv");
    	whiteMapOfMethod.put("mkt.contact.list.info.get","mkt.contact.list.info.get");
    	whiteMapOfMethod.put("mkt.contacts.commit.save","mkt.contacts.commit.save");
    	whiteMapOfMethod.put("mkt.contacts.longurl.get","mkt.contacts.longurl.get");
		return whiteMapOfMethod;   	
    }
}

/*************************************************
 * @功能简述: 拦截器，用来解决跨越问题
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/30
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CORSFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
	@Value("${api.cors.domain}")
	private String CORSDomain;
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
	    HttpServletResponse response = (HttpServletResponse) res;
	    response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, CORSDomain);
	    response.setHeader("Access-Control-Allow-Headers","X-Requested-With, Content-Type");
	    fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}

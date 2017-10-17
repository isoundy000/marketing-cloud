package cn.rongcapital.mkt.service;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface AudienceListDeleteService {
	public BaseOutput audienceListDel(Integer audienceListId,SecurityContext securityContext);
}

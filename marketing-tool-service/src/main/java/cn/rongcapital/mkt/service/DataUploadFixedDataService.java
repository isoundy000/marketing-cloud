package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataUploadFixedDataService {

    public BaseOutput uploadFixedData(MultipartFormDataInput formDataInput, SecurityContext securityContext);

}

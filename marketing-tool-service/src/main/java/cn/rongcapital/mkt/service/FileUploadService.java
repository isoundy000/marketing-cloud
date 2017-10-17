package cn.rongcapital.mkt.service;

import java.io.File;

import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public interface FileUploadService {

    File uploadFile(MultipartFormDataInput formDataInput, SecurityContext securityContext);
}

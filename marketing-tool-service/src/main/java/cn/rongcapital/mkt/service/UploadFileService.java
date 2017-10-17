package cn.rongcapital.mkt.service;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-6-2.
 */
public interface UploadFileService {

    Object uploadFile(String fileUnique, MultipartFormDataInput fileInput);

    Object uploadRepairFile(String fileUnique, MultipartFormDataInput fileInput);
    
    BaseOutput uploadFileBatch(String fileUnique, MultipartFormDataInput fileInput);

}

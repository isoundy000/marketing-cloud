package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String directory = "/Users/nianjun/Work/logs/upload/";


    @Override
    public File uploadFile(MultipartFormDataInput formDataInput, SecurityContext securityContext) {

        String fileName = "";
        Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file_upload");
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                if (!fileName.endsWith(".csv")) {
                    return new File(fileName);
                }
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                writeFile(bytes, directory + fileName);
                logger.info("文件上传完毕！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private File writeFile(byte[] content, String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();

        return file;
    }

}

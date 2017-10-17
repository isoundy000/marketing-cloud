/*************************************************
 * @功能简述: 实现FileStorageService
 * @项目名称: marketing cloud
 * @see:
 * @author: guozhenchao
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.file.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.ValidationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.file.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    
    public final static String SLASH = File.separator;
    
    private final static Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    
    @Override
    public void save(byte[] fileByte, String fileName, String key) throws ValidationException, IOException {

        if (fileByte == null) {
            logger.error("save file, file is not exist");
            throw new IllegalArgumentException("save file, file is not exist");
        }
        if (StringUtils.isBlank(fileName)) {
            logger.error("save file, fileName is null");
            throw new IllegalArgumentException("save file, fileName is null");
        }
        if (StringUtils.isBlank(key)) {
            logger.error("save file, key is null");
            throw new IllegalArgumentException("save file, key is null");
        }

        String fileUrl = key + SLASH + fileName;
        String dirUrl = key;
        writeFile(fileByte, fileUrl, dirUrl);
    }
    
    private void writeFile(byte[] content, String fileUrl, String dirUrl) throws IOException {
        File file = new File(fileUrl);
        File dirfile = new File(dirUrl);
        FileOutputStream fop = null;
        try {
            if (!file.exists()) {
                boolean success = dirfile.mkdirs();
                if (success) {
                    file.createNewFile();
                }
            } else {
                file.createNewFile();
            }
            fop = new FileOutputStream(file);
            fop.write(content);
            fop.flush();
        } catch (IOException e) {
            logger.error("parse file failed, fileUrl: {}, error: {}", fileUrl, e.getMessage(), e);
            throw e;
        } finally {
            try {
                if(fop != null){
                    fop.close();
                }
            } catch (IOException e) {
                logger.error("parse file, IO close failed, fileUrl: {}, error: {}", fileUrl, e.getMessage(), e);
            }
        }
    }

    @Override
    public byte[] get(String fileName, String key) throws IOException, ValidationException {

        if (StringUtils.isBlank(fileName)) {
            logger.error("get file, fileName is null");
            throw new IllegalArgumentException("get the file, fileName is null");
        }
        if (StringUtils.isBlank(key)) {
            logger.error("get file, key is null");
            throw new IllegalArgumentException("get file, key is null");
        }

        String filesUrl = key + SLASH + fileName;
        File file = new File(filesUrl);
        byte[] bytes = null;
        BufferedInputStream in = null;
        if (file.exists()) {
            try {
                in = new BufferedInputStream(new FileInputStream(file));
                bytes = IOUtils.toByteArray(in);
            } catch (FileNotFoundException e) {
                logger.error("get file failed, fileName: {}, error: {}", fileName, e.getMessage(), e);
                throw e;
            } catch (IOException e) {
                logger.error("get file IO failed, fileName: {}, error: {}", fileName, e.getMessage(), e);
                throw e;
            } finally {
                try {
                    if(in != null){
                        in.close();
                    }
                } catch (IOException e) {
                    logger.error("get the file IO close failed, fileName: {}, error: {}", fileName, e.getMessage(), e);
                }
            }
        } else {
            logger.error("file is not exist, fileName: {}", fileName);
            throw new FileNotFoundException("file is not exist");
        }
        return bytes;
    }

    @Override
    public boolean delete(String fileName, String key) throws ValidationException, FileNotFoundException {

        if (StringUtils.isBlank(fileName)) {
            logger.error("delete file, fileName is null");
            throw new IllegalArgumentException("delete file, fileName is null");
        }
        if (StringUtils.isBlank(key)) {
            logger.error("delete the file, key is null");
            throw new IllegalArgumentException("delete the file, key is null");
        }
        String filesUrl = key + SLASH + fileName;
        File file = new File(filesUrl);
        boolean flag = false;
        if (file.exists()) {
            flag = file.delete();
            flag = true;
        } else {
            logger.error("delete the file, the file is not exist, fileName: {}", fileName);
            throw new FileNotFoundException("delete the file, the file is not exist");
        }
        return flag;
    }
    
    @Override
    public boolean delete(String filesUrl) throws ValidationException, FileNotFoundException {

        if (StringUtils.isBlank(filesUrl)) {
            logger.error("delete file, filesUrl is null");
            throw new IllegalArgumentException("delete file, filesUrl is null");
        }
        File file = new File(filesUrl);
        boolean flag = false;
        if (file.exists()) {
            flag = file.delete();
            flag = true;
        } else {
            logger.error("delete the file, the file is not exist, filesUrl: {}", filesUrl);
            throw new FileNotFoundException("delete the file, the file is not exist");
        }
        return flag;
    }
}

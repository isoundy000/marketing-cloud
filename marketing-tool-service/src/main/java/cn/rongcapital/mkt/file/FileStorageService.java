package cn.rongcapital.mkt.file;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.ValidationException;


public interface FileStorageService {

    /**
     * 文件存储
     * @param fileByte 
     * @param fileName 
     * @param key 唯一路径/标示
     * @return
     * @throws Exception 
     */
    void save(byte[] fileByte, String fileName, String key) throws Exception;
    
    /**
     * 文件读取
     * @param fileName
     * @param key 唯一路径/标示
     * @return
     * @throws FileNotFoundException 
     * @throws IOException 
     * @throws ValidationException 
     */
    byte[] get(String fileName, String key) throws IOException, ValidationException;
    
    /**
     * 文件删除
     * @param fileName
     * @param key 唯一路径/标示
     * @return
     * @throws ValidationException 
     * @throws FileNotFoundException 
     */
    boolean delete(String fileName, String key) throws ValidationException, FileNotFoundException;
    
    /**
     * 文件删除
     * @param filesUrl 全路径
     * @return
     * @throws ValidationException
     * @throws FileNotFoundException
     */
    boolean delete(String filesUrl) throws ValidationException, FileNotFoundException;
}

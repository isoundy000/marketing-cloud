package cn.rongcapital.mkt.material.coupon.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.ValidationException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.file.FileStorageService;
import cn.rongcapital.mkt.file.service.impl.FileStorageServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceTest {

    private FileStorageService fileStorageService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    String filePath = "/rc/data/uploadFiles/code/1";
    String fileName = "2.xlsx";
    byte[] fileByte = null;
    
    @Before
    public void setUp() {
        logger.info("测试: FileStorageServiceTest 开始---------------------");
        fileStorageService = new FileStorageServiceImpl();
        try {
            fileByte = fileStorageService.get(fileName, filePath);
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：FileStorageServiceTest 结束---------------------");
    }
    
    @Test
    public void testFileGet() {
        try {
            fileByte = fileStorageService.get(fileName, filePath);
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(fileByte);
    }
    
    @Test
    public void testFileSave() {
        try {
            fileStorageService.save(fileByte, fileName, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFileDelete() {
        boolean flag = false;
        try {
            flag =fileStorageService.delete(fileName, filePath);
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(flag);
    }
}

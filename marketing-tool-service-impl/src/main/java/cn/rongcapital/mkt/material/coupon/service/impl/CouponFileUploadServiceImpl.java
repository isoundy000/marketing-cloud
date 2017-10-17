/*************************************************
 * @功能简述: CouponFileUploadService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: guozhenchao
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.file.FileStorageService;
import cn.rongcapital.mkt.material.coupon.service.CouponFileUploadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.UploadFileOut;

@Service
public class CouponFileUploadServiceImpl implements CouponFileUploadService {

    @Value("${uploaded.file.path}")
    private String filePath;
    
    public final static String SLASH = File.separator;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Override
    public BaseOutput uploadFile(MultipartFormDataInput fileInput, String userId) {

        Map<String, List<InputPart>> uploadForm = fileInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file_input");
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        if(inputParts == null){
            baseOutput.setCode(ApiErrorCode.FILE_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.FILE_ERROR.getMsg());
            return baseOutput;
        }
        UploadFileOut out = new UploadFileOut();
        InputPart inputPart = inputParts.get(0);
        String fileName = getFileName(inputPart);
        out.setFile_name(userId + "/" + fileName);
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg("上传的文件不是预定格式");
            return baseOutput;
        }
        InputStream is = null;
        try {
            is = inputPart.getBody(InputStream.class, null);
            byte[] bytes = IOUtils.toByteArray(is);
            is = new ByteArrayInputStream(bytes);
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            Long num = 0L;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Integer rowIndex = row.getRowNum();
                if (rowIndex == 0) {
                    continue;
                }
                Iterator<Cell> dataCellIterator = row.cellIterator();
                while (dataCellIterator.hasNext()) {
                    Cell dataColumnCell = dataCellIterator.next();
                    if(1 == dataColumnCell.getCellType()){
                        if(!StringUtils.isBlank(dataColumnCell.getStringCellValue())){
                            num++;
                        }
                    }
                }
            }
            out.setRecord_count(num);
            // 上传文件到服务器
            String dirUrl = filePath + userId;
            fileStorageService.save(bytes, fileName, dirUrl);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg("系统异常");
            return baseOutput;
        }
        List<Object> objList = new ArrayList<Object>();
        objList.add(out);
        baseOutput.setData(objList);
        return baseOutput;
    }

	private String getFileName(InputPart inputPart) {

		String finalFileName = null;
		try {
			Field field = inputPart.getClass().getDeclaredField("bodyPart");
			field.setAccessible(true);
			Object bodyPart = field.get(inputPart);
			Method methodBodyPart = bodyPart.getClass().getMethod("getHeader", new Class[] {});
			Iterable iterable = (Iterable) methodBodyPart.invoke(bodyPart, new Class[] {});
			Object[] content = IteratorUtils.toArray(iterable.iterator());
			Method methodContent = content[0].getClass().getMethod("getRaw",
					new Class[] {});
			String[] contentDisposition = methodContent.invoke(content[0], new Class[] {}).toString().split(";");
			for (String filename : contentDisposition) {
				if (filename.trim().startsWith("filename")) {
					String[] name = filename.split("=");
					finalFileName = name[1].trim().replaceAll("\"", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			finalFileName = "temp";

		}
		return finalFileName;
	}
    

    @Override
    public Object getCouponFileUploadUrlGet(String userId) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),1,null);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user_id",userId);
        map.put("file_url", ApiConstant.COUPON_FILE_UPLOAD_URL);
        baseOutput.getData().add(map);
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        return Response.ok().entity(baseOutput).build();
    }
}

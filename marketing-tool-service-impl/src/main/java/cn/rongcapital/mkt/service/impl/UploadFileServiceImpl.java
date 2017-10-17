package cn.rongcapital.mkt.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.csvreader.CsvWriter;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.IllegalDataHeadTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.common.util.ZipCreater;
import cn.rongcapital.mkt.dao.IllegalDataDao;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeLogDao;
import cn.rongcapital.mkt.dao.WechatQrcodeTicketDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.IllegalData;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeLog;
import cn.rongcapital.mkt.po.WechatQrcodeTicket;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.service.impl.vo.UploadFileProcessVO;
import cn.rongcapital.mkt.service.impl.vo.UploadFileVO;
import cn.rongcapital.mkt.service.impl.vo.WXMoudelVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.IllegalDataUploadModifyLogOut;
import cn.rongcapital.mkt.vo.out.IllegalDataUploadOut;
import cn.rongcapital.mkt.vo.out.UploadFileAccordTemplateOut;

/**
 * Created by Yunfeng on 2016-6-2.
 */
@Service
public class UploadFileServiceImpl implements UploadFileService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    //private final String directory = "//rc//";
    public static char CSV_WRITER_SEPARATOR=',';
    //public static String UPLOADED_FILE_PATH = "e://";
    public static String UPLOADED_FILE_NAME = "_upload.xlsx";
    public static String UPLOADED_FAIL_FILE_NAME = "_fail.csv";
    //TODO 以后放到配置文件中
    public static String UPLOADED_FILE_PATH = "/rc/data/uploadFiles/";
    public static String[] channels = new String[] {"经销商","渠道商","员工","区域","门店","活动"};
    public static String FAIL_FILE_PATH = "/rc/data/downloads/batchQrcodeErr/";
    public static String DOWN_LOAD_FAIL_FILE_PATH = "batchQrcodeErr/";
    //public static String FAIL_FILE_PATH = "e://";

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Autowired
    private ImportDataModifyLogDao importDataModifyLogDao;

    @Autowired
    private IllegalDataDao illegalDataDao;

    @Autowired
    private ParseUploadFileImpl parseUploadFile;
    
	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	@Autowired
	private WechatChannelDao wechatChannelDao;
	
	@Autowired
	private WechatRegisterDao wechatRegisterDao;
	
	@Autowired
	private WechatQrcodeLogDao wechatQrcodeLogDao;
	
	@Autowired
	WechatQrcodeTicketDao wechatQrcodeTicketDao;
	

    @Override
    public Object uploadFile(String fileUnique, MultipartFormDataInput fileInput) {
        UploadFileVO uploadFileVO = processEachUploadFile(fileUnique, fileInput, false);
        BaseOutput baseOutput = uploadFileVO.getOutput();
        if(baseOutput.getCode() != ApiErrorCode.SUCCESS.getCode()){
            return baseOutput;
        }

        UploadFileProcessVO processVO = uploadFileVO.getProcessVO();
        ImportDataHistory importDataHistory = uploadFileVO.getImportDataHistory();
        importDataHistory.setTotalRows(processVO.getTotalRows());
        importDataHistory.setLegalRows(processVO.getLegalRows());
        importDataHistory.setIllegalRows(processVO.getTotalRows().intValue() - processVO.getLegalRows().intValue());
        importDataHistory.setEmailRows(processVO.getEmailRows());
        importDataHistory.setMobileRows(processVO.getMobileRows());
        importDataHistory.setEmailRows(processVO.getEmailRows());
        importDataHistory.setDuplicateRows(processVO.getDuplicateRows());
        importDataHistory.setNoRecognizeProperty(processVO.getUnrecognizeFields());
        importDataHistory.setFileType(Integer.valueOf(processVO.getFileType()));
        importDataHistory.setSourceFilename(uploadFileVO.getFileName());
        importDataHistory.setDownloadFilename(uploadFileVO.getDownloadFileName());
        importDataHistory.setSource(ApiConstant.IMPORT_FILE_SOURCE);
        importDataHistoryDao.updateById(importDataHistory);

        saveIllegalData(processVO, importDataHistory.getId());


        UploadFileAccordTemplateOut out = new UploadFileAccordTemplateOut();
        out.setLegalRows(processVO.getLegalRows());
        out.setDataTopic(processVO.getDataTopic());
        out.setFileType(processVO.getFileType());
        out.setUnrecognizeFields(processVO.getUnrecognizeFields());
        baseOutput.getData().add(out);
        return Response.ok().entity(baseOutput).build();
    }

    @Override
    public Object uploadRepairFile(String fileUnique, MultipartFormDataInput fileInput) {
        // parse file, insert original
        UploadFileVO uploadFileVO = processEachUploadFile(fileUnique, fileInput, true);
        BaseOutput baseOutput = uploadFileVO.getOutput();
        if(baseOutput.getCode() != ApiErrorCode.SUCCESS.getCode()){
            return baseOutput;
        }

        UploadFileProcessVO processVO = uploadFileVO.getProcessVO();
        if(processVO.getTotalRows() == -1){
            return new BaseOutput(ApiErrorCode.BIZ_ERROR.getCode(),"文件格式非UTF-8编码",ApiConstant.INT_ZERO,null);
        }

        // update import history,insert import log
        ImportDataHistory importDataHistory = uploadFileVO.getImportDataHistory();
        int repairRows = processVO.getLegalRows().intValue();
        if (repairRows > 0) {
            if (importDataHistory.getIllegalRows() != null) {
                importDataHistory.setIllegalRows(importDataHistory.getIllegalRows().intValue() - repairRows);
            } else {
                importDataHistory.setIllegalRows(Integer.valueOf(0));
            }
            if (importDataHistory.getLegalRows() != null) {
                importDataHistory.setLegalRows(importDataHistory.getLegalRows() + repairRows);
            } else {
                importDataHistory.setLegalRows(repairRows);
            }

            // update importDataHistory Illegal data start
            int emailRows = processVO.getEmailRows().intValue();//非法邮箱的记录数
            if (importDataHistory.getEmailRows() != null && importDataHistory.getEmailRows()>0) {
                importDataHistory.setEmailRows(emailRows);
            }

            int mobileRows = processVO.getMobileRows().intValue();//非法手机号的记录数

            if (importDataHistory.getMobileRows() != null && importDataHistory.getMobileRows() > 0) {
                importDataHistory.setMobileRows(mobileRows);
            }

            int duplicateRows = processVO.getDuplicateRows().intValue();//重复数据的记录数

            if (importDataHistory.getDuplicateRows() != null && importDataHistory.getDuplicateRows() > 0) {
                importDataHistory.setDuplicateRows(duplicateRows);
            }
            // update importDataHistory Illegal data end
            
            importDataHistory.setNoRecognizeProperty(processVO.getUnrecognizeFields());
            importDataHistory.setFileType(Integer.valueOf(processVO.getFileType()));
            importDataHistoryDao.updateById(importDataHistory);
        }

        // update and insert illegal data
        illegalDataDao.updateStatusByBatchIdAndType(importDataHistory.getId(),
                importDataHistory.getFileType().toString(), StatusEnum.DELETED.getStatusCode());
        saveIllegalData(processVO, importDataHistory.getId());

        ImportDataModifyLog importDataModifyLog = new ImportDataModifyLog();
        importDataModifyLog.setImportDataId(importDataHistory.getId());
        importDataModifyLog.setHandleTime(new Date());
        importDataModifyLog.setTotalRows(importDataHistory.getTotalRows());
        importDataModifyLog.setModifyRows(processVO.getLegalRows());
        importDataModifyLog.setIllegalRows(importDataHistory.getIllegalRows());
        importDataModifyLog.setModifyFilename(uploadFileVO.getFileName());
        importDataModifyLog.setModifyDownloadFilename(uploadFileVO.getDownloadFileName());
        importDataModifyLog.setSuccess(Byte.valueOf(StatusEnum.ACTIVE.getStatusCode().toString()));
        importDataModifyLogDao.insert(importDataModifyLog);

        return Response.ok().entity(processOut(uploadFileVO, importDataHistory.getId())).build();
    }

    private IllegalDataUploadOut processOut(UploadFileVO uploadFileVO, Long importDataHistoryId) {
        UploadFileProcessVO processVO = uploadFileVO.getProcessVO();
        ImportDataModifyLog queryImportDataModifyLog = new ImportDataModifyLog();
        queryImportDataModifyLog.setImportDataId(importDataHistoryId);
        queryImportDataModifyLog.setPageSize(null);
        queryImportDataModifyLog.setStartIndex(null);
        queryImportDataModifyLog.setOrderField("handle_time");
        queryImportDataModifyLog.setOrderFieldType("desc");
        List<ImportDataModifyLog> allImportDataModifyLogList =
                importDataModifyLogDao.selectList(queryImportDataModifyLog);
        List<IllegalDataUploadModifyLogOut> modifyLogOutList = new ArrayList<>(allImportDataModifyLogList.size());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ImportDataModifyLog tempImportDataModifyLog : allImportDataModifyLogList) {
            IllegalDataUploadModifyLogOut modifyLogOut = new IllegalDataUploadModifyLogOut();
            modifyLogOut.setModifyFilename(tempImportDataModifyLog.getModifyFilename());
            modifyLogOut.setModifyDownloadFilename(tempImportDataModifyLog.getModifyDownloadFilename());
            modifyLogOut.setHandleTime(simpleDateFormat.format(tempImportDataModifyLog.getHandleTime()));
            modifyLogOutList.add(modifyLogOut);
        }

        BaseOutput baseOut = uploadFileVO.getOutput();
        baseOut.getData().add(processVO);

        IllegalDataUploadOut illegalDataUploadOut = new IllegalDataUploadOut();
        illegalDataUploadOut.setCode(baseOut.getCode());
        illegalDataUploadOut.setMsg(baseOut.getMsg());
        illegalDataUploadOut.setTotal(baseOut.getTotal());
        illegalDataUploadOut.getData().add(processVO);
        illegalDataUploadOut.setModifyLog(modifyLogOutList);

        return illegalDataUploadOut;
    }


    private UploadFileVO processEachUploadFile(String fileUnique, MultipartFormDataInput fileInput, boolean isRepair) {
        UploadFileVO uploadFileVO = new UploadFileVO();
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        uploadFileVO.setOutput(baseOutput);

        ImportDataHistory importDataHistory = queryFileUnique(fileUnique);
        uploadFileVO.setImportDataHistory(importDataHistory);
        if(importDataHistory == null){
            baseOutput.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
            baseOutput.setMsg("文件唯一性标识不合法。");
            return uploadFileVO;
        } else if (isRepair) {
            Integer totalRows = importDataHistory.getTotalRows();
            Integer legalRows = importDataHistory.getLegalRows();
            if (totalRows != null && legalRows != null && legalRows.intValue() == totalRows.intValue()) {
                baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                baseOutput.setMsg("没有需要修正的记录。");
                return uploadFileVO;
            }
        }
        UploadFileProcessVO processVO = null;
        Map<String,List<InputPart>> uploadForm = fileInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");
        for(InputPart inputPart : inputParts){
            try {
                String fileName = getFileName(inputPart);
                logger.info("当前上传的文件名称为：" + fileName);
//                        getFileName(inputPart.getHeaders());
                if(!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx") && !fileName.endsWith(".xlsm")){
                    baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                    baseOutput.setMsg("上传的文件不是预定格式");
                    return uploadFileVO;
                }

                String batchId = importDataHistory.getId().toString();
                String[] fileTypeArray = fileName.split("_");
                int fileType = 0;
                if (!isRepair) {
                    if (fileTypeArray.length < 1) {
                        baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                        baseOutput.setMsg("上传的文件名不是预定格式");
                        return uploadFileVO;
                    }
                    try {
                        fileType = Integer.parseInt(fileTypeArray[0].substring(fileTypeArray[0].length()-1));
                    } catch (Exception e) {
                        logger.error("invalid file name,file type parse failed", e);
                    }
                    if (fileType == 0) {
                        baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                        baseOutput.setMsg("上传的文件名不是预定格式");
                        return uploadFileVO;
                    }
                } else {
                    fileType = importDataHistory.getFileType();
                }

                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
//                if (!isUTF8(bytes)) {
//                    baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
//                    baseOutput.setMsg("上传的文件不是UTF-8编码");
//                    return uploadFileVO;
//                }
                // 上传非法数据
                if (isRepair) {
                    // 比较上传文件的记录条数跟数据库中非法数据的记录数
                    int repairDataCount = repairDataLength(bytes);
                    logger.info("上传的文件的记录条数:" + repairDataCount);
                    IllegalData illegalData = new IllegalData();
                    illegalData.setStatus(0);
                    illegalData.setBatchId(importDataHistory.getId());
                    illegalData.setType(String.valueOf(fileType));
                    
                    int count = illegalDataDao.selectListCount(illegalData);
                    if (repairDataCount > count) {
                        baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                        baseOutput.setMsg("上传文件的非法记录多于系统的,请先下载非法数据后再修改csv文件!");
                        return uploadFileVO;
                    }
                    // 比较上传文件的记录条数跟数据库中非法数据的记录数
                }

                processVO = parseUploadFile.parseAndInsertUploadFileByType(fileUnique,fileType, batchId, bytes);
                String downloadFileName = FileUtil.generateFileforDownload(bytes);
                uploadFileVO.setFileName(fileName);
                uploadFileVO.setDownloadFileName(downloadFileName);
            }catch (Exception e){
                logger.error("parse file failed", e);
                baseOutput.setCode(ApiErrorCode.SYSTEM_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.SYSTEM_ERROR.getMsg());
            }
        }
        uploadFileVO.setOutput(baseOutput);
        uploadFileVO.setProcessVO(processVO);
        return uploadFileVO;
    }

    public int repairDataLength(final byte[] dataBytes) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(dataBytes), StandardCharsets.UTF_8));
		int len = 0;
		try{
		    while((bufferedReader.readLine()) != null) {
		    	len++;
		    }
		    	
		 } catch (Exception e){
			 logger.error("文件解析失败!", e);
		 } finally {
		    if (bufferedReader != null) {
		        try {
		            bufferedReader.close();
		        } catch (Exception e) {
		
		        }
		    }
		 }
		return len;
    }

    public boolean isUTF8(final byte[] dataBytes) {
        int expectedLength = 0;
        int testLength = 9;
        if (dataBytes.length <= testLength) {
            testLength =  dataBytes.length;
        }

        for (int i = 0; i < testLength; i++) {
            if ((dataBytes[i] & 0b10000000) == 0b00000000) {
                expectedLength = 1;
            } else if ((dataBytes[i] & 0b11100000) == 0b11000000) {
                expectedLength = 2;
            } else if ((dataBytes[i] & 0b11110000) == 0b11100000) {
                expectedLength = 3;
            } else if ((dataBytes[i] & 0b11111000) == 0b11110000) {
                expectedLength = 4;
            } else if ((dataBytes[i] & 0b11111100) == 0b11111000) {
                expectedLength = 5;
            } else if ((dataBytes[i] & 0b11111110) == 0b11111100) {
                expectedLength = 6;
            } else {
                return false;
            }
            while (--expectedLength > 0) {
                if (++i >= dataBytes.length) {
                    return false;
                }
                if ((dataBytes[i] & 0b11000000) != 0b10000000) {
                    return false;
                }
            }
        }
        return true;
    }

    private void saveIllegalData(UploadFileProcessVO processVO, Long importDataHistoryId) {
        List<String> illegalRawDataList = processVO.getIllegalRawData();
        if (illegalRawDataList.size() < 1) {
            return;
        }
        List<IllegalData> illegalDataList = new ArrayList<>(illegalRawDataList.size() + 1);
        for (String originData : illegalRawDataList) {
            IllegalData illegalData = new IllegalData();
            illegalData.setBatchId(importDataHistoryId);
            illegalData.setType(processVO.getFileType());
            illegalData.setOriginData(originData);
            illegalData.setHeadType(IllegalDataHeadTypeEnum.DATA.getCode());
            illegalDataList.add(illegalData);
        }
        IllegalData illegalData = new IllegalData();
        illegalData.setBatchId(importDataHistoryId);
        illegalData.setType(processVO.getFileType());
        illegalData.setOriginData(processVO.getFileHeader());
        illegalData.setHeadType(IllegalDataHeadTypeEnum.HEADER.getCode());
        illegalDataList.add(illegalData);
        illegalDataDao.batchInsert(illegalDataList);
    }

    private ImportDataHistory queryFileUnique(String fileUnique) {
        if(fileUnique == null) {
            return null;
        }
        ImportDataHistory importDataHistory = new ImportDataHistory();  //判断fileUnique的有效性
        importDataHistory.setFileUnique(fileUnique);
        List<ImportDataHistory> importDataHistoryList = importDataHistoryDao.selectList(importDataHistory);
        if (CollectionUtils.isEmpty(importDataHistoryList)) {
            return null;
        }
        return importDataHistoryList.get(0);
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for(String filename : contentDisposition){
            if((filename.trim().startsWith("filename"))){
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"","");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private String getFileName(InputPart inputPart){
        String fileName = null;
        try{
            Field field = inputPart.getClass().getDeclaredField("bodyPart");
            field.setAccessible(true);
            Object bodyPart = field.get(inputPart);
            Method methodBodyPart = bodyPart.getClass().getMethod("getHeader", new Class[]{});
            Iterable iterable = (Iterable)methodBodyPart.invoke(bodyPart, new Class[]{});
            Object[] content = IteratorUtils.toArray(iterable.iterator());
            Method methodContent = content[0].getClass().getMethod("getRaw", new Class[]{});

            String[] contentDisposition = methodContent.invoke(content[0], new Class[]{}).toString().split(";");

            for (String filename : contentDisposition) {

                if (filename.trim().startsWith("filename")) {
                    String[] name = filename.split("=");
                    fileName = name[1].trim().replaceAll("\"", "");
                }
            }
        }catch (Exception e){

        }
        return fileName;
    }

	@Override
	public BaseOutput uploadFileBatch(String fileUnique, MultipartFormDataInput fileInput) {
		Map<String, List<InputPart>> uploadForm = fileInput.getFormDataMap();
		//List<InputPart> inputParts = uploadForm.get("file");
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
		BaseOutput baseOutput = new BaseOutput();
		baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
		baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		
		List<String> channelNmaeList = new ArrayList<>();
		for(String channelName : channels){
			channelNmaeList.add(channelName);
		}
		
		CsvWriter csvWriter = null;
		for (InputPart inputPart : inputParts) {
			try {
				String fileName = getFileName(inputPart);
				if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
					baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
					baseOutput.setMsg("上传的文件不是预定格式");
					return baseOutput;
				}

				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				byte[] bytes = IOUtils.toByteArray(inputStream);

				InputStream is = new ByteArrayInputStream(bytes);

				Workbook workbook = WorkbookFactory.create(is);
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.rowIterator();
				
				WXMoudelVO wxMoudel= null; 
				List<WXMoudelVO> wxList = new ArrayList<>();
				List<WXMoudelVO> wxSuccessList = new ArrayList<>();
				//List<WXMoudelVO> wxFailList = new ArrayList<>();
				Map<String, WXMoudelVO> wxFailMap = new LinkedHashMap<>();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Integer rowIndex = row.getRowNum();
					if (rowIndex == 0) {
						continue;
					}
					Iterator<Cell> dataCellIterator = row.cellIterator();
					
					wxMoudel= new WXMoudelVO();
					
					int index = 0;
					while (dataCellIterator.hasNext()) {
						Cell dataColumnCell = dataCellIterator.next();
						if(index == 0){
							if(Cell.CELL_TYPE_STRING == dataColumnCell.getCellType()) {
								wxMoudel.setQrName(dataColumnCell.getStringCellValue());
							}else if(Cell.CELL_TYPE_NUMERIC == dataColumnCell.getCellType()){
								DecimalFormat df = new DecimalFormat("0");
								String qrName = df.format(dataColumnCell.getNumericCellValue());
								wxMoudel.setQrName(qrName);
							}
							
						}else if(index == 1){
							if(Cell.CELL_TYPE_STRING == dataColumnCell.getCellType()) {
								wxMoudel.setChannelType(dataColumnCell.getStringCellValue());
							}else if(Cell.CELL_TYPE_NUMERIC == dataColumnCell.getCellType()){
								wxMoudel.setChannelType(String.valueOf(dataColumnCell.getNumericCellValue()));
							}
						}else if(index == 2){
							if(Cell.CELL_TYPE_STRING == dataColumnCell.getCellType()) {
								wxMoudel.setOfficialName(dataColumnCell.getStringCellValue());
							}else if(Cell.CELL_TYPE_NUMERIC == dataColumnCell.getCellType()){
								wxMoudel.setOfficialName(String.valueOf(dataColumnCell.getNumericCellValue()));
							}
						}
						
						index++;
					}
					wxList.add(wxMoudel);
				}
				
				
				for(WXMoudelVO wmo : wxList){
				    //二维码名称
				    String qrName = wmo.getQrName();
					//渠道名字
					String channelType = wmo.getChannelType();
					//查询渠道名字是否存在
					WechatChannel wechatChannel =  new WechatChannel();
					wechatChannel.setChName(channelType);
					List<WechatChannel> channeltList = wechatChannelDao.selectList(wechatChannel);
				
					//int channelTypeCount = wechatChannelDao.selectListCount(wechatChannel);
					if(channeltList.isEmpty() && !channelNmaeList.contains(channelType)){
						wxFailMap.put(qrName + "渠道名字不存在" + channelType, wmo);
						continue;
					}
					
					//公众号名称不是必填，可以为空，有值就要校验是否存在
					String officialName = wmo.getOfficialName();
					if(officialName != null && officialName.length() >0){
						WechatRegister wechatRegister = new WechatRegister();
						wechatRegister.setName(officialName);
						int officialNameCount = wechatRegisterDao.selectListCount(wechatRegister);
						if(officialNameCount <= 0){
							wxFailMap.put(qrName+ "公众号名字不存在" + officialName, wmo);
							continue;
						}
					}
					
					//新建的二维码，在同一公众号、同一渠道下不可以重复
					Integer chCode = null;
					WechatQrcode wechatQrcode = new WechatQrcode();
					wechatQrcode.setWxName(officialName);
                    if(channeltList != null && channeltList.size() > 0){
                        chCode = channeltList.get(0).getId();
                    }
                    wechatQrcode.setChCode(chCode);
                    wechatQrcode.setQrcodeName(qrName);
                    wechatQrcode.setStatus((byte) 1);
                    int qrListCount = wechatQrcodeDao.selectListCount(wechatQrcode);
                    if(qrListCount > 0) {
                        wxFailMap.put(qrName+ "在同一公众号同一渠道下重复", wmo);
                        continue;
                    }
                    
					//符合条件的数据
					wxSuccessList.add(wmo);
				}
				
				//保存成功的数据
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String bachId = simpleDateFormat.format(new Date());
				
				//初始化Ticket对象
				WechatQrcodeTicket wechatQrcodeTicket = new WechatQrcodeTicket();
				wechatQrcodeTicket.setState(0);
				wechatQrcodeTicket.setOrderField("id");
				wechatQrcodeTicket.setOrderFieldType("ASC");
				
				// 保存该批次二维码图片
				List<String> qrcodePicLists = new ArrayList<String>();
				// 保存二维码名用于重命名zip包内二维码名
				List<String> picNewName = new ArrayList<String>();
				
				for(WXMoudelVO wmo : wxSuccessList){
					WechatQrcode wq = new WechatQrcode();
					wq.setBatchId(bachId);
					wq.setQrcodeName(wmo.getQrName());
					wq.setStatus((byte)0);//保存的时候默认未用
					wq.setWxName(wmo.getOfficialName());
					Integer chCode = null;
					WechatChannel wc = new WechatChannel();
					wc.setChName(wmo.getChannelType());
					List<WechatChannel> selectList = wechatChannelDao.selectList(wc);
					if(selectList != null && selectList.size() > 0){
						chCode = selectList.get(0).getId();
					}
					wq.setChCode(chCode);
					wq.setIsAudience((byte) 0);
					//wq.setStatus((byte) 1);
					
                    WechatRegister wechatRegister = new WechatRegister();
                    wechatRegister.setName(wmo.getOfficialName());
					List<WechatRegister> registerList = wechatRegisterDao.selectList(wechatRegister);
					WechatRegister register = registerList.get(0);
					if(register != null){
					    wq.setWxAcct(register.getWxAcct());
					}
					
					List<WechatQrcodeTicket> wechatQrcodeTickets = wechatQrcodeTicketDao.selectList(wechatQrcodeTicket);
					if(wechatQrcodeTickets!=null && wechatQrcodeTickets.size()>0){
						WechatQrcodeTicket wechatQrcodeTicketTemp = wechatQrcodeTickets.get(0);
						wq.setQrcodeUrl(wechatQrcodeTicketTemp.getUrl());
						wq.setQrcodePic(String.valueOf(wechatQrcodeTicketTemp.getId()) + ".jpg");
						wq.setTicket(String.valueOf(wechatQrcodeTicketTemp.getId()));
						wechatQrcodeTicketTemp.setState(1);
						wechatQrcodeTicketDao.updateById(wechatQrcodeTicketTemp);
					}
					wechatQrcodeDao.insert(wq);
					qrcodePicLists.add(wq.getQrcodePic());
					picNewName.add(wq.getQrcodeName());
				}
				
				// 生成该批次，二维码图片的zip包，并写入到服务器
				if(qrcodePicLists.size() > 0 && qrcodePicLists.size() == picNewName.size()) {
					createQrocdePicZip(qrcodePicLists, picNewName, bachId);
				} else {
                    logger.info("创建二维码zip包失败，批次号={} ; 二维码地址数量={} ; 二维码名字数量={}", bachId, qrcodePicLists.size(),
                            picNewName.size());
                }
				
				//生成错误文件csv并把错误文件写到服务器
				//csvWriter = new CsvWriter(new FileWriter(new File("e:\\fail.csv")), CSV_WRITER_SEPARATOR);
				String failFile = FAIL_FILE_PATH + bachId + UPLOADED_FAIL_FILE_NAME;
				csvWriter = new CsvWriter(failFile, CSV_WRITER_SEPARATOR, Charset.forName("GBK"));
				
				//把头写进去
				csvWriter.write("二维码名称(必填，20字以内，不可重复)");
				csvWriter.write("渠道分类(必填，须系统中渠道分类选框中已存在的分类，选择其一)");
				csvWriter.write("公众号名称");
				csvWriter.write("失败原因");
				csvWriter.endRecord();
				
				Set<Entry<String, WXMoudelVO>> entrySet = wxFailMap.entrySet();
				
				for(Entry<String, WXMoudelVO> entry : entrySet){
					WXMoudelVO value = entry.getValue();
					csvWriter.write(value.getQrName());
					csvWriter.write(value.getChannelType());
					csvWriter.write(value.getOfficialName());
					csvWriter.write(entry.getKey());
					csvWriter.endRecord();
				}
				
				//上传文件到服务器
				fileName = UPLOADED_FILE_PATH + bachId + UPLOADED_FILE_NAME;
	             writeFile(bytes,fileName);
	             
	           //往log表写数据
				WechatQrcodeLog wql = new WechatQrcodeLog();
				wql.setSourceFilename(fileName);
				wql.setTotalRows(wxList.size());
				wql.setSuccess((byte)wxSuccessList.size());
				wql.setStatus((byte)0);
				wql.setBatchId(bachId);
				wechatQrcodeLogDao.insert(wql);
				
				baseOutput.setTotal(1);
				Map<String, Object> map = new HashMap<>();
				map.put("succ_count", wxSuccessList.size());
				map.put("fail_count", wxFailMap.size());
				map.put("batch_id", bachId);
				String failNamePath = DOWN_LOAD_FAIL_FILE_PATH + bachId + UPLOADED_FAIL_FILE_NAME;
				map.put("fail_url", failNamePath);
				baseOutput.getData().add(map);
			} catch (Exception e) {
				e.printStackTrace();
				baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
				baseOutput.setMsg("系统异常");
				return baseOutput;
			}finally{
				if (csvWriter != null) {
					csvWriter.close();
				}
			}
		}
		return baseOutput;
	}
	
	 private void writeFile(byte[] content, String fileName) throws IOException{
	        File file = new File(fileName);
	        if(!file.exists()){
	            file.createNewFile();
	        }
	        FileOutputStream fop = new FileOutputStream(file);
	        fop.write(content);
	        fop.flush();
	        fop.close();
	    }
	 
	 /**
	  * 根据批次号，和该批次二维码图片，给二维码图片打包
	  * 
	  * @param qrcodePicLists
	  * @param bachId
	  * @author shuiyangyang
	  * @Data 2016.09.26
	  */
	 private void createQrocdePicZip(List<String> qrcodePicLists, List<String> picNewName, String bachId) {
		 
		 // 此处利用了需要压缩的文件和压缩后文件放在同一路径下的特殊情况
		 String[] fileNamePaths = {ApiConstant.UPLOAD_IMG_PATH_LARGE, 
								 ApiConstant.UPLOAD_IMG_PATH_MIDDLE, 
								 ApiConstant.UPLOAD_IMG_PATH_SMALL};
		 
		 int qrcodePicListsSize = qrcodePicLists.size();
		 
		 for(String fileNamePath : fileNamePaths) {
			 
			 // 设置zip文件的全名
			 String fileName = fileNamePath + bachId + ".zip";
			 File[] files = new File[qrcodePicListsSize];
			 // 设置要压缩文件的全名
			 for(int i = 0; i < qrcodePicListsSize; i++) {
				 files[i] = new File(fileNamePath + qrcodePicLists.get(i));
			 }
			 
			 // 开始创建zip包
			try {
				generateZip(files, picNewName, fileName);
			} catch (IOException e) {
				logger.info(e.toString());
			} 
		 }
	 }
	 
	 /**
	  * 功能描述：创建zip文件包并重命名zip包内文件的名字
	  * 
	  * @param files 需要被压缩的文件名数组(需要绝对路径)
	  * @param picNewNameLists zip包内文件的新名字
	  * @param fileName 压缩后zip包的文件名(需要绝对路径)
	  * @throws IOException
	  */
	 private void generateZip(File[] files,List<String> picNewName, String fileName) throws IOException {

	        if (files == null || files.length <= 0) {
	            return;
	        }

	        // 去除文件不存在的文件名
	        List<File> fileLists = new ArrayList<File>();
	        List<String> picNewNameLists = new ArrayList<String>();
	        for (int i = 0; i < files.length; i++) {
	            if (files[i] != null && files[i].isFile() && files[i].exists()) {
	                fileLists.add(files[i]);
	                picNewNameLists.add(picNewName.get(i));
	            }
	        }

	        if (fileLists == null || fileLists.size() <= 0) {
	            return;
	        }

	        OutputStream os = null;
	        ZipOutputStream zos = null;
	        BufferedInputStream bis = null;

	        try {
	            logger.debug("创建zip包开始，包名：{}", fileName);
	            os = new BufferedOutputStream(new FileOutputStream(fileName));
	            zos = new ZipOutputStream(os);
	            byte[] buf = new byte[8192];
	            int len;
	            for (int i = 0; i < fileLists.size(); i++) {
	                File file = fileLists.get(i);
	                ZipEntry ze = new ZipEntry(picNewNameLists.get(i) + ".jpg");
	                zos.putNextEntry(ze);
	                bis = new BufferedInputStream(new FileInputStream(file));
	                while ((len = bis.read(buf)) > 0) {
	                    zos.write(buf, 0, len);
	                }
	                zos.closeEntry();
	            }
	            zos.closeEntry();
	            zos.close();
	            logger.debug("创建zip包成功，包名：{}", fileName);
	        } catch (Exception e) {
	            logger.info(e.toString());
	        } finally {
	            if (os != null) {
	                os.close();
	            }
	            if (zos != null) {
	                zos.close();
	            }
	            if (bis != null) {
	                bis.close();
	            }
	        }
	    }
}

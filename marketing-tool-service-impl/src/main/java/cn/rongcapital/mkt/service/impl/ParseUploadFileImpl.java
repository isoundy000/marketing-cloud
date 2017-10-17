package cn.rongcapital.mkt.service.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.KeyidMapBlock;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.helpers.XSSFFormulaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.rongcapital.mkt.common.enums.GenderEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.service.impl.vo.ParseFileVO;
import cn.rongcapital.mkt.service.impl.vo.UploadFileProcessVO;

/**
 * Created by Yunfeng on 2016-6-13.
 * 把上传上来的文件解析，解析后得到的摘要，数据条数，未识别属性
 */
@Service
public class ParseUploadFileImpl {
    @Autowired
    private ImportTemplateDao importTemplateDao;
    @Autowired
    private OriginalDataPopulationDao originalDataPopulationDao;
    @Autowired
    private OriginalDataArchPointDao originalDataArchPointDao;
    @Autowired
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;
    @Autowired
    private OriginalDataLoginDao originalDataLoginDao;
    @Autowired
    private OriginalDataMemberDao originalDataMemberDao;
    @Autowired
    private OriginalDataPaymentDao originalDataPaymentDao;
    @Autowired
    private OriginalDataShoppingDao originalDataShoppingDao;
    @Autowired
    private KeyidMapBlockDao keyidMapBlockDao;

    private final Integer UNIQUE_ID_ROW_IN_FILE = 0;
    private final Integer INVALID_ROW_IN_FILE = 1;
    private final Integer ATTRIBUTE_NAME_ROW_IN_FILE = 2;
    private String bitmap;

    private static Logger logger = LoggerFactory.getLogger(ParseUploadFileImpl.class);

    public UploadFileProcessVO parseAndInsertUploadFileByType(String fileUnique, int fileType, String batchId, byte[] bytes) {
        ParseFileVO parseFileVO = parseFile(bytes, fileType, batchId, fileUnique);
        ArrayList<Map<String, Object>> legalDataList = parseFileVO.getLegalDataList();
        int effectRows = 0;
        if(legalDataList.size() > 0){
            effectRows = insertParsedData(legalDataList, fileType);
        }

        UploadFileProcessVO uploadFileProcessVO = new UploadFileProcessVO();
        uploadFileProcessVO.setTotalRows(parseFileVO.getTotalRows());
        uploadFileProcessVO.setLegalRows(effectRows);
        uploadFileProcessVO.setIllegalRows(parseFileVO.getTotalRows() - effectRows);
        uploadFileProcessVO.setDataTopic(importTemplateDao.selectTempleNameByType(fileType));
        uploadFileProcessVO.setFileType(fileType + "");
        uploadFileProcessVO.setUnrecognizeFields(parseFileVO.getIllegaColumns());
        uploadFileProcessVO.setFileHeader(parseFileVO.getHeader());
        uploadFileProcessVO.setIllegalRawData(parseFileVO.getIllegaRawDataList());
        uploadFileProcessVO.setEmailRows(parseFileVO.getEmailRows());
        uploadFileProcessVO.setMobileRows(parseFileVO.getMobileRows());
        uploadFileProcessVO.setDuplicateRows(parseFileVO.getDuplicateRows());
        return uploadFileProcessVO;
    }

    private int insertParsedData(ArrayList<Map<String, Object>> insertList, int fileType) {
        int effectRows = 0;
        switch(fileType){
            case ImportConstant.POPULATION_FILE:
                return originalDataPopulationDao.batchInsertUploadFileData(insertList);
            case ImportConstant.CUSTOMER_TAG_FILE:
                return originalDataCustomerTagsDao.batchInsertUploadFileData(insertList);
            case ImportConstant.ARCH_POINT_FILE:
                return originalDataArchPointDao.batchInsertUploadFileData(insertList);
            case ImportConstant.MEMBER_FILE:
                return originalDataMemberDao.batchInsertUploadFileData(insertList);
            case ImportConstant.LOGIN_FILE:
                return originalDataLoginDao.batchInsertUploadFileData(insertList);
            case ImportConstant.PAYMENT_FILE:
                return originalDataPaymentDao.batchInsertUploadFileData(insertList);
            case ImportConstant.SHOPPING_FILE:
                return originalDataShoppingDao.batchInsertUploadFileData(insertList);
            default:
                return effectRows;
        }
    }

    /**
     * parse file
     * @param bytes
     * @param fileType
     * @param batchId
     * @param fileUnique
     * @return data rows ,not include header
     */
    private ParseFileVO parseFile(byte[] bytes, int fileType, String batchId, String fileUnique) {
        ArrayList<Map<String,Object>> legalDataList = new ArrayList<>();
        ArrayList<String> illegaDataList = new ArrayList<>();

        Map<String, String> nameCodeMappingMap = getNameCodeRelationByFileType(fileType);
        Map<String, Integer> codeIndexMap = new HashMap<>();
        Map<String, Integer> dataCheck = new HashMap<>();
        String illegalColumns = null;
        String header = null;

        int totalRows = 0;
        int emailRows = 0;
        int mobileRows = 0;
        int duplicateRows = 0;

//1解析上传的Excel文件
//-------------------------------------------
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            logger.info("begin to parse uploaded file.");
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            for(;rowIterator.hasNext();){
                Row row = rowIterator.next();
                Integer rowIndex = row.getRowNum();
                if(rowIndex == UNIQUE_ID_ROW_IN_FILE){
                    //通过解析文本获取主键
                    char[] tmpBuffer = new char[17];
                    for(int i = 0; i<tmpBuffer.length; i++) tmpBuffer[i] = '0';
                    Iterator<Cell> uniqueIterator = row.cellIterator();
                    for(;uniqueIterator.hasNext();){
                        Cell cell = uniqueIterator.next();
                        if(cell.getColumnIndex() != 0 && !cell.getStringCellValue().equals("")){
                            tmpBuffer[cell.getColumnIndex()-1] = '1';
                        }
                    }
                    bitmap = new String(tmpBuffer);
                    continue;
                }
                logger.info("bitmap: " + bitmap );
                if(bitmap == null) {
                    logger.info("数据文件没有指定唯一列索引，为非法数据文件");
                    break;
                }
                if(rowIndex == INVALID_ROW_IN_FILE) continue;
                //当遍历到上传的文件的第三行时，这里解析到了列名与索引的对应关系
                if(rowIndex == ATTRIBUTE_NAME_ROW_IN_FILE){
                    illegalColumns = parseHeaderInExcel(codeIndexMap,nameCodeMappingMap,row);
                    StringBuffer tmpHeader = new StringBuffer();
                    Iterator<Cell> headerCellIterator = row.cellIterator();
                    for(;headerCellIterator.hasNext();){
                        Cell headerColumnCell = headerCellIterator.next();
                        if(headerColumnCell.getCellType() == Cell.CELL_TYPE_STRING){
                            tmpHeader.append(headerColumnCell.getStringCellValue() + ",");
                        }
                    }
                    header = tmpHeader.substring(0, tmpHeader.length() - 1);
                    continue;
                }

                //解析数据，将数据插入相应的OriginalData表中
                String line = null;
                StringBuffer tmpLine = new StringBuffer();
                Iterator<Cell> dataCellIterator = row.cellIterator();
                for(;dataCellIterator.hasNext();){
                    Cell dataColumnCell = dataCellIterator.next();
                    switch (dataColumnCell.getCellType()){
                        case Cell.CELL_TYPE_STRING:
                            tmpLine.append(dataColumnCell.getStringCellValue() + ",");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(dataColumnCell)){
                                tmpLine.append(DateUtil.getStringFromDate(dataColumnCell.getDateCellValue(),"yyyy-MM-dd HH:mm:ss") + ",");
                            }else{
                                tmpLine.append(dataColumnCell.getNumericCellValue() + ",");
                            }
                            break;
                        default:
                            break;
                    }
                }
                if(tmpLine.length() < 1) continue;           //如果出现空行，则走向下一行
                line = tmpLine.substring(0, tmpLine.length() - 1);

                Integer existsDataIndex = dataCheck.get(line);
                if (existsDataIndex == null) {
                    int validateResult = parseRowDataList(codeIndexMap, legalDataList, row, batchId, fileUnique,fileType,bitmap);
                    if (ImportConstant.VALIDATE_SUCCESS != validateResult) {
                        illegaDataList.add(line);
                        switch (validateResult) {
                            case ImportConstant.VALIDATE_EMAIL_FAILED:
                                emailRows++;
                                break;
                            case ImportConstant.VALIDATE_MOBILE_FAILED :
                                mobileRows++;
                                break;
                        }
                    } else {
                        dataCheck.put(line, Integer.valueOf(legalDataList.size() - 1));
                    }
                } else {
                    illegaDataList.add(line);
                    illegaDataList.add(line);
                    legalDataList.set(existsDataIndex.intValue(), null);
                    duplicateRows++;
                }
                totalRows++;

            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
//-----------------------------------------------------------------
        Iterator<Map<String, Object>> iterator = legalDataList.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> dataMap = iterator.next();
            if (dataMap == null) {
                iterator.remove();
            }
        }

        ParseFileVO parseFileVO = new ParseFileVO();
        parseFileVO.setLegalDataList(legalDataList);
        parseFileVO.setIllegaRawDataList(illegaDataList);
        parseFileVO.setHeader(header);
        parseFileVO.setIllegaColumns(illegalColumns);
        parseFileVO.setTotalRows(Integer.valueOf(totalRows));
        parseFileVO.setEmailRows(emailRows);
        parseFileVO.setMobileRows(mobileRows);
        parseFileVO.setDuplicateRows(duplicateRows);
        return parseFileVO;
    }

    private int parseRowDataList(Map<String, Integer> codeIndexMap,
            ArrayList<Map<String, Object>> rowDataList, Row row, String batchId, String fileUnique, int fileType, String bitmap) {
        Map<String,Object> insertMap = new HashMap<>();
        for(Map.Entry<String, Integer> entry : codeIndexMap.entrySet()){
            String cloumnCode = entry.getKey();
            int cloumnIndex = entry.getValue().intValue();
            try {
                Cell cell = row.getCell(cloumnIndex);
                if(cell != null){
                    if(cloumnCode.endsWith("time") || cloumnCode.endsWith("birthday") || cloumnCode.equals("expire")){
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                            if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
                                Date date = cell.getDateCellValue();
                                insertMap.put(cloumnCode,date);
                            }
                        }else{
                            insertMap.put(cloumnCode,null);
                        }
                    }else{
                        switch (cell.getCellType()){
                            case Cell.CELL_TYPE_STRING:
                                insertMap.put(cloumnCode,cell.getStringCellValue());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if(cloumnCode.endsWith("consignee_tel") || cloumnCode.endsWith("mobile") || cloumnCode.endsWith("identify_no")
                                        || cloumnCode.endsWith("driving_license") || cloumnCode.endsWith("tel") || cloumnCode.endsWith("qq")){
                                    DecimalFormat df = new DecimalFormat("0");
                                    insertMap.put(cloumnCode,df.format(cell.getNumericCellValue()));
                                    break;
                                }
                                insertMap.put(cloumnCode,cell.getNumericCellValue() + "");
                                break;
                            default:
                                insertMap.put(cloumnCode,null);
                                break;
                        }
                    }
                }else{
                    insertMap.put(cloumnCode,null);
                }
            }catch (Exception e){
                logger.debug(e.getMessage());
            }
        }

        int validateResult = validateData(insertMap, fileType, bitmap);
        if(validateResult == ImportConstant.VALIDATE_SUCCESS){
            convertData(insertMap, fileType);
            insertMap.put("file_unique",fileUnique);
            insertMap.put("batch_id",batchId);
            insertMap.put("bitmap",bitmap);      //将IDMap的标志值放入到插入数据Map中
            rowDataList.add(insertMap);
        }
        return validateResult;
    }

    /**
     * @功能简述: 验证数据解析后是否合法
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private int validateData(Map<String, Object> insertMap,int fileType, String bitmap) {
        if(!bitmap.contains("1")) return ImportConstant.VALIDATE_KEYMAPPINGID_FAILED;
        char[] bitmapSequence = bitmap.toCharArray();
        Integer seqIndex = 1;
        for(char seqValue : bitmapSequence){
            if(seqValue == '1'){
                KeyidMapBlock keyidMapBlock = new KeyidMapBlock();
                keyidMapBlock.setSeq(seqIndex);
                keyidMapBlock = keyidMapBlockDao.selectKeyIdMapBolckBySeq(keyidMapBlock);
                String result = (String) insertMap.get(keyidMapBlock.getField());
                if(result == null || "".equals(result)){
                    return ImportConstant.VALIDATE_KEYMAPPINGID_FAILED;
                }
            }
            seqIndex++;
        }

        logger.info("唯一性验证验证通过");
        String email = (String) insertMap.get(ImportConstant.EMAIL_FIELD);
        if(StringUtils.hasText(email)){
            if (!isEmail(email)) {
                return ImportConstant.VALIDATE_EMAIL_FAILED;
            }
        }
        if(fileType == ImportConstant.POPULATION_FILE){
            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            if(!StringUtils.hasLength(mobile) || mobile.trim().length() != ImportConstant.MOBILE_LENGTH){
                return ImportConstant.VALIDATE_MOBILE_FAILED;
            }

            String maritalStatus = (String) insertMap.get(ImportConstant.MARITAL_STATUS_FIELD);
            if (!ImportConstant.MARITAL_STATUS_MARRIED.equals(maritalStatus) &&
                    !ImportConstant.MARITAL_STATUS_SINGLE.equals(maritalStatus) &&
                    !ImportConstant.MARITAL_STATUS_UNKNOWN.equals(maritalStatus)) {
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            String monthlyIncome = (String) insertMap.get(ImportConstant.MONTHLY_INCOME_FIELD);
            String monthlyConsume = (String) insertMap.get(ImportConstant.MONTHLY_CONSUME_FIELD);
            if (!isNumber(monthlyIncome) || !isNumber(monthlyConsume)) {
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }

            return ImportConstant.VALIDATE_SUCCESS;

        }else if(fileType == ImportConstant.SHOPPING_FILE){
            String orderNo = (String) insertMap.get(ImportConstant.ORDER_NO_FIELD);
            String transSerial = (String) insertMap.get(ImportConstant.TRANS_SERIAL_FIELD);
            String discount = (String) insertMap.get(ImportConstant.DISCOUNT_AMT_FIELD);
            String price = (String) insertMap.get(ImportConstant.PRICE_FIELD);
            String source = (String) insertMap.get(ImportConstant.SOURCE_FIELD);

            if(!StringUtils.hasText(orderNo) && !StringUtils.hasText(transSerial)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }

            if(!StringUtils.hasText(source)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }

            if(!isNumber(discount) || !isNumber(price)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            return ImportConstant.VALIDATE_SUCCESS;
        }else if(fileType == ImportConstant.PAYMENT_FILE){
            String orderNo = (String) insertMap.get(ImportConstant.ORDER_NO_FIELD);
            String transSerial = (String) insertMap.get(ImportConstant.TRANS_SERIAL_FIELD);
            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            String incomeAmt = (String) insertMap.get(ImportConstant.INCOME_AMT_FIELD);
            String paidAmt = (String) insertMap.get(ImportConstant.PAID_AMT_FIELD);
            String acctAmt = (String) insertMap.get(ImportConstant.ACCT_AMT_FIELD);
            String source = (String) insertMap.get(ImportConstant.SOURCE_FIELD);
            if(StringUtils.isEmpty(orderNo) && StringUtils.isEmpty(transSerial)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            logger.info("订单号验证通过");
            if(StringUtils.isEmpty(source)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            logger.info("来源验证通过");
            if (!isNumber(incomeAmt) || !isNumber(paidAmt) || !isNumber(acctAmt)) {
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            logger.info("数字验证通过");
            return ImportConstant.VALIDATE_SUCCESS;
        } else {
            if (fileType == ImportConstant.MEMBER_FILE) {
                String cardAmt = (String) insertMap.get(ImportConstant.CARD_AMT_FIELD);
                if (!isNumber(cardAmt)) {
                    return ImportConstant.VALIDATE_OTHER_FAILED;
                }
            } else if (fileType == ImportConstant.CUSTOMER_TAG_FILE) {
                String tagSource = (String) insertMap.get("tag_source");
                String tagTypeLayerOne = (String) insertMap.get("tag_type_layer_one");
                String tagTypeLayerTwo = (String) insertMap.get("tag_type_layer_two");
                String tagTypeLayerThree = (String) insertMap.get("tag_type_layer_three");
                String tagName = (String) insertMap.get("tag_name");
                String source = (String) insertMap.get("source");
                if(!StringUtils.hasText(tagSource) || !StringUtils.hasText(tagTypeLayerOne) || !StringUtils.hasText(tagName) || !StringUtils.hasText(source)){
                    return ImportConstant.VALIDATE_OTHER_FAILED;
                }
                if(StringUtils.hasText(tagTypeLayerOne) && StringUtils.hasText(tagTypeLayerThree) && !StringUtils.hasText(tagTypeLayerTwo)){
                    return ImportConstant.VALIDATE_OTHER_FAILED;
                }

            }

            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            if(!StringUtils.hasLength(mobile) || mobile.trim().length() != ImportConstant.MOBILE_LENGTH){
                return ImportConstant.VALIDATE_MOBILE_FAILED;
            }
            return ImportConstant.VALIDATE_SUCCESS;
        }
    }

    /**
     * @功能简述: 构造出数据库列名与文件中相应列索引的对应关系
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
//    private String parseHeader(Map<String, Integer> codeIndexMap,
//                             Map<String, String> nameCodeMap, String[] uploadFileColumns) {
//        StringBuffer illegalColumns = new StringBuffer();
//        int column = 0;
//        for(String uploadFileColumn : uploadFileColumns){
//            String fieldCode = nameCodeMap.get(uploadFileColumn);
//            if(fieldCode != null){
//                codeIndexMap.put(fieldCode, Integer.valueOf(column));
//            }else{
//                illegalColumns.append(uploadFileColumn + ",");
//            }
//            column ++;
//        }
//
//        if (illegalColumns.length() > 0) {
//            return illegalColumns.substring(0, illegalColumns.length() - 1);
//        }
//        return "";
//    }

    /**
     * @功能简述: 构造出数据库列名与文件中相应列索引的对应关系
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private String parseHeaderInExcel(Map<String, Integer> codeIndexMap,
                               Map<String, String> nameCodeMap, Row headerRow) {
        StringBuffer illegalColumns = new StringBuffer();
        Iterator<Cell> cellIterator = headerRow.cellIterator();
        for(;cellIterator.hasNext();){
            Cell cell = cellIterator.next();
            switch (cell.getCellType()){
                case Cell.CELL_TYPE_STRING:
                    String fieldCode = nameCodeMap.get(cell.getStringCellValue());
                    if(fieldCode != null){
                        if("".equals(fieldCode)) continue;
                        codeIndexMap.put(fieldCode,cell.getColumnIndex());
                    }else{
                        illegalColumns.append(cell.getStringCellValue() + ",");
                    }
                    break;
                default:
                    logger.info("上传列名的数据格式不合法，请检查Excel文件头的编辑");
            }
        }

        if (illegalColumns.length() > 0) {
            return illegalColumns.substring(0, illegalColumns.length() - 1);
        }
        return "";
    }

    /**
     * @功能简述: 根据文件类型获取文件中的汉字列与数据库表的列名的对应关系保存在Map中
     * @param: int fileType
     * @return: Map<String,String> relationMap
     */
    private Map<String, String> getNameCodeRelationByFileType(int fileType) {
        Map<String,String> nameCodeMap = new HashMap<String,String>();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("templ_type",fileType);
        List<Map<String,Object>> fileColumn = importTemplateDao.selectFileColumn(paramMap);
        for(Map<String,Object> map : fileColumn){
            nameCodeMap.put((String)map.remove("field_name"),(String)map.remove("field_code"));
        }
        return nameCodeMap;
    }

    private void convertData(Map<String, Object> insertMap, int fileType) {
        Object gender =  insertMap.get(ImportConstant.GENDER_FIELD);
        if (gender != null) {
            if (GenderEnum.MALE.getDescription().equals(gender)) {
                insertMap.put(ImportConstant.GENDER_FIELD, GenderEnum.MALE.getStatusCode());
            } else if (GenderEnum.FEMALE.getDescription().equals(gender)) {
                insertMap.put(ImportConstant.GENDER_FIELD, GenderEnum.FEMALE.getStatusCode());
            } else if (GenderEnum.OTHER.getDescription().equals(gender)) {
                insertMap.put(ImportConstant.GENDER_FIELD, GenderEnum.OTHER.getStatusCode());
            } else if (GenderEnum.UNSURE.getDescription().equals(gender)) {
                insertMap.put(ImportConstant.GENDER_FIELD, GenderEnum.UNSURE.getStatusCode());
            }
        }

        insertMap.put(ImportConstant.STATUS_FIELD, StatusEnum.DELETED.getStatusCode());

    }

    private boolean isNumber(String valStr) {
        if (!StringUtils.hasText(valStr)) {
            return true;
        }

        Pattern pattern = Pattern.compile("^((\\d*)|(\\d+\\.\\d+))?$");
        Matcher match = pattern.matcher(valStr);
        return match.matches();
    }

    private boolean isEmail(String valStr) {
        if (!StringUtils.hasText(valStr)) {
            return true;
        }

        Pattern pattern = Pattern.compile("^(\\w|-|_|\\.)+@(\\w|-|_|\\.)+[a-zA-Z]+$");
        Matcher match = pattern.matcher(valStr);
        return match.matches();
    }

    interface ImportConstant {

        int POPULATION_FILE = 1;
        int CUSTOMER_TAG_FILE = 2;
        int ARCH_POINT_FILE = 3;
        int MEMBER_FILE = 4;
        int LOGIN_FILE = 5;
        int PAYMENT_FILE = 6;
        int SHOPPING_FILE = 7;

        String GENDER_FIELD = "gender";
        String ORDER_NO_FIELD = "order_no";
        String TRANS_SERIAL_FIELD = "trans_serial";
        String MOBILE_FIELD = "mobile";
        String EMAIL_FIELD = "email";
        String MARITAL_STATUS_FIELD = "marital_status";
        String CARD_AMT_FIELD = "card_amt";
        String INCOME_AMT_FIELD = "income_amt";
        String PAID_AMT_FIELD = "paid_amt";
        String ACCT_AMT_FIELD = "acct_amt";
        String MONTHLY_INCOME_FIELD = "monthly_income";
        String MONTHLY_CONSUME_FIELD = "monthly_consume";
        String DISCOUNT_AMT_FIELD = "discount_amt";
        String PRICE_FIELD = "price";
        String STATUS_FIELD = "status";
        String SOURCE_FIELD = "source";

        int MOBILE_LENGTH = 11;
        String MARITAL_STATUS_SINGLE = "未婚";
        String MARITAL_STATUS_MARRIED = "已婚";
        String MARITAL_STATUS_UNKNOWN = "未知";

        String TAG_TYPE_DATE = "日期型标签";
        String TAG_TYPE_TEXT = "文本型标签";

        int VALIDATE_SUCCESS = 0;
        int VALIDATE_MOBILE_FAILED = 1;
        int VALIDATE_EMAIL_FAILED = 2;
        int VALIDATE_OTHER_FAILED = 3;
        int VALIDATE_KEYMAPPINGID_FAILED =4;


    }

}

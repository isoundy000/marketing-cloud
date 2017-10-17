package cn.rongcapital.mkt.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.common.constant.ApiConstant;

/**
 * Created by Yunfeng on 2016-6-25.
 */
public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static String FILE_SEPERATOR = ",";
	private static String FILE_SUFFIX = ".csv";
	private static int MAX_RETRY_GENERATE_TIMES = 5;

	public static File generateDownloadFile(List<Map<String, Object>> dataSource, String prefixFileName) {
		String fileName = ApiConstant.DOWNLOAD_BASE_DIR + prefixFileName + System.currentTimeMillis() + ".csv"; // 线上服务器文件生成目录
		// String fileName = prefixFileName + System.currentTimeMillis() +
		// ".csv"; 本地测试目录
		File file = new File(fileName);
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			StringBuffer lineBuffer = new StringBuffer();
			// Todo:1.生成文件头
			if (dataSource != null && dataSource.size() > 0) {
				Map<String, Object> rowMap = dataSource.get(0);
				for (String key : rowMap.keySet()) {
					lineBuffer.append(key + ",");
				}
				bufferedWriter.write(lineBuffer.deleteCharAt((lineBuffer.length() - 1)).toString());
				bufferedWriter.newLine();
				lineBuffer.setLength(0);
			}
			// Todo:2.将数据根据文件头写入文件
			for (Map<String, Object> map : dataSource) {
				for (String key : map.keySet()) {
					lineBuffer.append(map.get(key) + ",");
				}
				bufferedWriter.write(lineBuffer.deleteCharAt(lineBuffer.length() - 1).toString());
				bufferedWriter.newLine();
				lineBuffer.setLength(0);
			}
		} catch (IOException e) {
			logger.error("-----generateDownloadFile---" + e);
		} finally {
			try {
				if (null != bufferedWriter)
					bufferedWriter.close();
			} catch (IOException e) {
				logger.error("-----generateDownloadFile---" + e);
			}
		}
		return file;
	}

	/**
	 * 根据PO类型,文件名生成下载的文件 Map的key为数据库字段值,value为对应的字段名
	 * 
	 * @author nianjun
	 * @param dataList
	 * @param fileName
	 * @return
	 */

	// 如果调用该方法生成文件 , 也得用反射的方式把数据存入对象中并update数据库
	public static <E> File generateFileforDownload(List<Map<String, String>> columnNames, List<E> dataList,
			String fileName) {
		StringBuilder pathNameBuilder = new StringBuilder();
		// 当前日期
		// DateTime today = DateTime.now();
		pathNameBuilder.append(ApiConstant.DOWNLOAD_BASE_DIR).append(fileName).append("_")
				// pathNameBuilder.append("/Users/nianjun/Work/logs/").append(fileName).append("_")
				// .append(today.toString(ApiConstant.DATE_FORMAT_yyyy_MM_dd)).append(FILE_SUFFIX);
				.append(new Date().getTime()).append(FILE_SUFFIX);
		File file = new File(pathNameBuilder.toString());
		logger.info("要创建的文件为 : " + file.getAbsolutePath());
		return generateFile(columnNames, dataList, file);
	}

	public static String generateFileforDownload(byte[] content) throws Exception {
		int times = 0;
		String downloadFileName = null;
		while (times < MAX_RETRY_GENERATE_TIMES) {
			downloadFileName = new StringBuilder().append(RandomStringUtils.randomAlphanumeric(10)).append("_")
					.append(System.currentTimeMillis()).append(FILE_SUFFIX).toString();
			String fullPathName = new StringBuilder().append(ApiConstant.DOWNLOAD_BASE_DIR).append(downloadFileName)
					.toString();
			Path file = Paths.get(fullPathName);
			if (Files.exists(file)) {
				continue;
			}
			try {
				Files.createFile(file);
				Files.write(file, content);
			} catch (Exception e) {
				logger.error("----generateFileforDownload----" + e);
				throw new Exception(e);
			}
			times++;
		}
		return downloadFileName;
	}

	public static Path generateFileforDownload(String header, List<String> dataList, String fileName) {
		StringBuilder pathNameBuilder = new StringBuilder();
		pathNameBuilder.append(ApiConstant.DOWNLOAD_BASE_DIR).append(fileName).append("_")
				.append(RandomStringUtils.randomAlphanumeric(6).toUpperCase()).append("_")
				.append(System.currentTimeMillis()).append(FILE_SUFFIX);
		return generateFile(header, dataList, pathNameBuilder.toString().replace("file:", ""));
	}

	private static Path generateFile(String header, List<String> dataList, String filePath) {
		Path file = null;
		try {
			file = Paths.get(filePath);
			if (!Files.exists(file)) {
				Files.createFile(file);
			}
		} catch (IOException e1) {
			logger.error("创建文件失败", e1);
		}

		if (CollectionUtils.isEmpty(dataList)) {
			return file;
		}
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file), "UTF-8"));
			bufferedWriter.write(header);
			bufferedWriter.newLine();
			for (String rowData : dataList) {
				bufferedWriter.write(rowData);
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
		} catch (IOException e) {
			logger.error("生成下载文件时出错", e);
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
			} catch (IOException e) {
				logger.error("关闭资源时出错", e);
			}
		}

		return file;
	}

	public static <E> File generateFile(List<Map<String, String>> columnNames, List<E> poList, File file) {

		if (CollectionUtils.isEmpty(columnNames)) {
			throw new IllegalArgumentException("columnNames为空,需要提供cvs文件的字段名");
		}

		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			logger.info("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				logger.info("创建目标文件所在目录失败！");
			}
		}

		// 没数据的时候生成空文件
		if (CollectionUtils.isEmpty(poList)) {
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
			} catch (IOException e) {
				logger.info("文件无内容时,创建空文件失败");
			}
			return file;
		}

		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK"));
			int rowCount = poList.size();

			// 按数据量取出每一行的数据
			for (int i = 0; i < rowCount; i++) {
				E entity = poList.get(i);
				StringBuilder stringBuilder = new StringBuilder();
				StringBuilder columnTransferNameBuilder = new StringBuilder();
				Field[] fields = entity.getClass().getDeclaredFields();

				// 根据字段名取出每一个字段的值
				for (int j = 0; j < columnNames.size(); j++) {
					// 先取出数据库中的字段名
					Map<String, String> columnMap = columnNames.get(j);
					String columnName = columnMap.keySet().iterator().next();

					if (i == 0) {
						// 如果像import_template表一样,字段名有对应的中文名,则取出中文名
						String transferName = columnMap.get(columnName);
						if (!StringUtils.isEmpty(transferName)) {
							columnTransferNameBuilder.append(transferName);
						} else {
							columnTransferNameBuilder.append(ReflectionUtil.recoverFieldName(columnName));
						}
					}

					// 根据字段名获取对象对应的字段
					Field field = getFieldByName(fields, ReflectionUtil.recoverFieldName(columnName));
					if (field == null) {
						logger.info("无法找到该字段 : {}", ReflectionUtil.recoverFieldName(columnName));
					} else {
						field.setAccessible(true);

						// 这是个日期类型,需要转换
						if (field.getType().getSimpleName().equals(Date.class.getSimpleName())) {
							// joda time的转换比较方便
							DateTime dateTime = new DateTime(field.get(entity));
							stringBuilder.append(dateTime.toString(ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
						} else {
							// stringBuilder.append(field.get(entity));
							// 将导出文件中显示null的单元格内容变为""
							stringBuilder.append(field.get(entity) == null ? "" : field.get(entity));
						}

					}

					if (j == columnNames.size() - 1) {
						break;
					} else {
						stringBuilder.append(FILE_SEPERATOR);
						columnTransferNameBuilder.append(FILE_SEPERATOR);
					}
				}

				if (i == 0) {
					bufferedWriter.write(columnTransferNameBuilder.toString());
					bufferedWriter.newLine();
				}

				bufferedWriter.write(stringBuilder.toString());
				bufferedWriter.newLine();
			}

			bufferedWriter.flush();
			logger.info("下载文件创建完毕 : " + file.getAbsoluteFile());
		} catch (IOException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("生成下载文件时出错", e);
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (IOException e) {
				logger.error("生成下载文件后关闭资源时出错", e);
			}
		}

		return file;
	}

	// 根据字段名称,获取对象中对应的Field对象
	private static Field getFieldByName(Field[] fields, String name) {
		if (fields == null || fields.length < 1) {
			throw new IllegalArgumentException("fields对象为空");
		}

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field != null && field.getName().equalsIgnoreCase(name)) {
				return field;
			}
		}

		return null;
	}

	/**
	 * 用于转换列名参数,将list转换为map,转换后map的key为list<String>的string,value为""
	 * 
	 * @author nianjun
	 * @param columnNames
	 * @return
	 */
	public static List<Map<String, String>> transferNameListtoMap(List<String> columnNames) {
		List<Map<String, String>> columnNamesMap = new ArrayList<>();
		if (CollectionUtils.isEmpty(columnNames)) {
			Map<String, String> emptyMap = new HashMap<>();
			emptyMap.put("", "");
			columnNamesMap.add(emptyMap);
		} else {
			for (String columnName : columnNames) {
				Map<String, String> map = new HashMap<>();
				map.put(columnName, "");
				columnNamesMap.add(map);
			}
		}

		return columnNamesMap;
	}
	

	/**
	   * 下载文件到本地
	   *
	   * @param urlString
	   *          被下载的文件地址
	   * @param filename
	   *          本地文件名
	   * @throws Exception
	   *           各种异常
	   */
	public static void download(String urlString,String folder, String filename) throws Exception {
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    // 输入流
	    InputStream is = con.getInputStream();
	    // 1K的数据缓冲
	    byte[] bs = new byte[10240];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	    OutputStream os = null;
	    String filenameNew = folder+filename;	    
	    try {
			os = new FileOutputStream(filenameNew);
			// 开始读取
			if(os!=null){
				while ((len = is.read(bs)) != -1) {
			      os.write(bs, 0, len);
			    }
			    // 完毕，关闭所有链接
			    os.close();
			    is.close();
			}
		} catch (FileNotFoundException e) {	
				createDir(folder);
				download(urlString,folder,filename);
		}catch (Exception e) {					
			logger.info(e.getMessage());
		}	    
	} 
	
    public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {            
            return false;  
        }  
        logger.info(File.separator);
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {             
            return true;  
        } else {           
            return false;  
        }  
    } 
	
	public static void main(String[] args) throws Exception {
	    download("http://mmbiz.qpic.cn/mmbiz_jpg/OiawiaOqXEjkqcB210t2A7h5m270KPIbA6KneI5VGZwYqbFRu9zDe5GyexyrOt5AE10DiazAy3hYZn3hzWcCMMW6w/0?wx_fmt=jpeg", "E:/workspace_doc/","laozizhu.com1.jpeg");
	}
}

/**
 * 描述：根据excel往mongo写入数据
 * 
 * @author shuiyangyang
 * @date 2016.09.27
 */
package cn.rongcapital.mkt.service;

import java.io.IOException;
import java.util.List;

import cn.rongcapital.mkt.po.mongodb.TagTree;


public interface AnalysisTagFileService {
	
	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 * @author shuiyangyang
	 * @date 2016.09.27
	 */
	public List<TagTree> readXlsx(String path) throws IOException;
}

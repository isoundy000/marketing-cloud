package cn.rongcapital.mkt.common.util;

import java.io.File;  
import java.io.IOException;  
import java.util.Hashtable;  
 
import com.google.zxing.BarcodeFormat;  
import com.google.zxing.EncodeHintType;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.WriterException;  
import com.google.zxing.common.BitMatrix;

public class QrCreater {
	
//	public static void main(String[] args) {
//		generateQr("afadfasfd","d:/55.jpg");
//	}
	
	/**
	 * 生成二维码
	 * @param contents:二维码内容
	 * @param filePath:保存路径需要(.jpg)
	 * @return
	 */
	public static boolean generateQr(String contents, String filePath) {


		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();

		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix matrix = null;

		try {

			matrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, 200, 200, hints);

		} catch (WriterException e) {
			
			e.printStackTrace();
			return false;
		}

		File file = new File(filePath);

		try {

			MatrixToImageWriter.writeToFile(matrix, "jpg", file);

		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

}
package cn.rongcapital.mkt.common.util;

public class NumUtil {

	public static byte int2OneByte(int num) {  
        return (byte) (num & 0x000000ff);  
    }

}

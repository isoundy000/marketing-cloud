package cn.rongcapital.mkt.common.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 根据长链接返回段链接
 * @author 
 * @Date 2016.09.02
 *
 */
public class ShortUrlCreator{
    
    
    static String key = "marketingcloud.ruixuesoft.com";
    
    static char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8' , '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
        "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
        "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
        "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
        "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
        "U" , "V" , "W" , "X" , "Y" , "Z"}; 
    
    public static String generateShortUrl(String url) {
        
        String sMD5EncryptResult = getMd5(key + url);
        String hex = sMD5EncryptResult; 
        
        String[] resUrl = new String[1];
 
        for ( int i = 0; i < 1; i++) {
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            String outChars = "" ;
            for ( int j = 0; j < 6; j++) {
               long index = 0x0000003D & lHexLong;
               outChars += chars[( int ) index];
               lHexLong = lHexLong >> 5;
            }
            resUrl[i] = outChars;
        }
        return resUrl[0]; 
    }
     
    private static String getMd5(String s) {
        byte[] b = s.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            byte[] b2 = md.digest();
            char str[] = new char[b2.length << 1];
            int len = 0;
            for (int i = 0; i < b2.length; i++) {
                byte val = b2[i];
                str[len++] = hexChar[(val >>> 4) & 0xf];
                str[len++] = hexChar[val & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    
}

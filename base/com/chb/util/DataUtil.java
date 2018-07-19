package com.chb.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class DataUtil {
	
	
	public String formatMoney(BigDecimal money) {
		DecimalFormat df = new DecimalFormat("#0.0");
		return df.format(money.divide(new BigDecimal(10),1,BigDecimal.ROUND_HALF_UP));
	}
	
	public String unicodeToStr(String unicode) {
        String returnStr = "";
    	byte[] bytes = new byte[unicode.length() / 2];
    	String n = "";
        for (int i = 0; i < bytes.length; i++) {
        	n = unicode.substring(i*2,i*2+2);
            returnStr += (char) Integer.valueOf(n, 16).intValue();
        }
        return returnStr;
    }

	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * 
	 * @param hexStr
	 * @return
	 */
	public String hexStr2Str(String hexStr) {
		String str = "0123456789abcdef";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * 
	 * @param str
	 * @return
	 */
	public String str2HexStr(String str) {
		char[] chars = "0123456789abcdef".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			// sb.append(' ');
		}
		return sb.toString().trim().toLowerCase();
	}
}

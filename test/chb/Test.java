package chb;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(",\r\n\"21\":\"111111111111\"");
//		sb.append(",\r\n\"22\":\"222222222222\"");
//		String jsonStr = sb.toString();
//		writeFile2("C:\\ProgramFiles\\workspace\\chb\\src\\config\\transaction.json",jsonStr);
//		List<JsonVO> list = new ArrayList<>();
//		JsonVO vo = new JsonVO();
//		vo.setKey("1");
//		vo.setValue("鱼");
//		JsonVO vo2 = new JsonVO();
//		vo2.setKey("2");
//		vo2.setValue("鱼2");
//		list.add(vo);
//		list.add(vo2);
		String str = "73646164";
		System.out.println(hexStr2Str(str));
	}
	
	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * 
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
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
	
	public static String hexStr2Str(String hexStr) {
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
	
	private static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
          returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }
	
	public static Object getMaxKey(Map<String,Object> map) {
		 if (map == null) return null;
		 Set<String> set = map.keySet();
		 Set<Integer> intSet = new HashSet<Integer>();
		 for (String str : set) { 
			 intSet.add(Integer.valueOf(str));
		} 
		 Object[] obj = intSet.toArray();
		 Arrays.sort(obj);
		 return obj[obj.length - 1];
	}
	
	// 把json格式的字符串写到文件
    public static boolean writeFile2(String filePath, String sets) {
        try {
        	RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw");
        	// 文件长度，字节数
        	long fileLength = randomFile.length();
        	// 将写文件指针移到文件尾。
        	randomFile.seek(fileLength - 5);
        	randomFile.writeBytes(sets);
        	randomFile.writeBytes("\r\n}");
        	randomFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}



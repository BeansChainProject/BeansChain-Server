package com.chb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class JsonUil {
	
	@Autowired
	private ServletContext servletContext;
	 /**
     * json读取
     * 
     * @param request
     * @param path
     * @return
     */
    public Map<String,Object> JsonRead(String path) {
    	 Map<String, Object> map = null;
		try (InputStream in = servletContext.getResourceAsStream(path);) {
			StringBuffer string = new StringBuffer();
			String temp = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ((temp = reader.readLine()) != null) {
				string.append(temp);
			}
			reader.close();
			map = JSON.parseObject(string.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
    }
    
 // 向json文件中追加内容
    public boolean appendToJson(String filePath, String sets) {
        try {
        	RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw");
        	// 文件长度，字节数
        	long fileLength = randomFile.length();
        	// 将写文件指针移到json文件的括号前
        	randomFile.seek(fileLength - 3);
        	randomFile.write(sets.getBytes());
        	randomFile.write("\r\n}".getBytes());
        	randomFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Object getMaxKey(Map<String,Object> map) {
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
}

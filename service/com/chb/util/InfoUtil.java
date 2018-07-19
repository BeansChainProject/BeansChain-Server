package com.chb.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chb.home.ResultVO;

@Component
public class InfoUtil {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
    private UrlConf urlConf;
	
	/**
   	 * post订单数据
   	 * @return
   	 */
   	public ResultVO postOrderInfo(String params) {
   		String method = urlConf.getSendTransaction();
   		String data = "0x8b46b019" + params;
   		String to = urlConf.getTo();
   		String from = urlConf.getFrom();
   		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"from\": \""+from+"\",\"gas\":\"0xfffff\",\"gasPrice\":\"0x430e23400\", \"data\": \""+data+"\"}], \"id\": 1}";
   		return common(requestJson,"存储订单信息...");
   	}
   	
   	
   	/**
   	 * 记录某个Tx的订单号 
   	 * @return
   	 */
   	public ResultVO saveOrderNoAndHash(String params) {
   		String method = urlConf.getSendTransaction();
   		String data = "0x167e119e" + params;
   		String to = urlConf.getTo();
   		String from = urlConf.getFrom();
   		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"from\": \""+from+"\", \"data\": \""+data+"\"}], \"id\": 1}";
   		return common(requestJson,"记录订单号...");
   	}
   	
   	/**
	 * 处理统计数据-ResultVO
	 * @param requestJson
	 * @param dataType
	 * @return
	 */
	public ResultVO common(String requestJson,String dataType) {
		log.info(requestJson);
		 ResultVO result = null;
		try {
			String url = urlConf.getUrl();
			if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(requestJson) && StringUtils.isNotEmpty(dataType)) {
				HttpHeaders headers = new HttpHeaders();
			     MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			     headers.setContentType(type);
			     HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			     result = restTemplate.postForObject(url,entity,ResultVO.class);
			     if(result == null || !StringUtils.isNotEmpty(result.getResult())) {
			    	 log.error(dataType+"获取失败，[result="+result.getResult()+"]");
			     }
			}else {
				log.error(dataType+"获取失败，[url="+url+",requestJson="+requestJson+"]");
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
		}
		 
       return result;
	}
}

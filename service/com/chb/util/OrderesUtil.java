package com.chb.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chb.home.OrderVO;
import com.chb.home.ResultVO;

@Component
public class OrderesUtil {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
    private UrlConf urlConf;
	
	@Autowired
    private DataUtil dataUtil;
	
	@Autowired
    private DateUtil dateUtil;
	
	/**
   	 * 订单
   	 * @param value
   	 * @return
   	 */
	public OrderVO getOrderes(String orderNo) {
		String method = urlConf.getGetOrderesList();
		String to = urlConf.getTo();
   		String len =  StringUtils.leftPad(String.valueOf(orderNo.length()),64, "0");
   		String params = dataUtil.str2HexStr(orderNo);
   		String data = "0x019866a00000000000000000000000000000000000000000000000000000000000000020"+len+ StringUtils.rightPad(params,64, "0");
   		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \""+data+"\"}, \"latest\"], \"id\": 1}";
   		return orderes(requestJson,"获取订单列表,[orderNo="+orderNo+"]");
	}
	
	/**
   	 * 依据订单号返回相应的 txHash
   	 * @param value
   	 * @return
   	 */
	public String getTxHashByOrder(String orderNo) {
		String method = urlConf.getGetOrderesList();
		String to = urlConf.getTo();
   		String len =  StringUtils.leftPad(String.valueOf(orderNo.length()),64, "0");
   		String params = dataUtil.str2HexStr(orderNo);
   		String data = "0x0eae2f9a0000000000000000000000000000000000000000000000000000000000000020"+len+ StringUtils.rightPad(params,64, "0");
   		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \""+data+"\"}, \"latest\"], \"id\": 1}";
   		return getTxHash(requestJson,"依据订单号获取txHash,[orderNo="+orderNo+"]");
	}
	
	
	/**
	 * 处理订单返回值
	 * @param requestJson
	 * @param dataType
	 * @return
	 */
	private OrderVO orderes(String requestJson,String dataType){
		log.info(requestJson+dataType);
		OrderVO orderes = new OrderVO();
		try {
			String url = urlConf.getUrl();
			if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(requestJson) && StringUtils.isNotEmpty(dataType)) {
				HttpHeaders headers = new HttpHeaders();
			    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			    headers.setContentType(type);
			    HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			    ResultVO result = restTemplate.postForObject(url,entity,ResultVO.class);
			    if(result != null && result.getResult() != null) {
			    	String str = result.getResult().substring(2);
			    	 int len = 64;//分割长度
			    	 for(int i = 0; i < 3;i++) {
			    		 
			    		 String subStr = str.substring(i*len, len*(i+1));
			    		 String num = String.valueOf(Long.valueOf(subStr,16));
			    		 switch(i) {
			    		 case 0:
			    			 log.info(str+">>"+num+">>"+dateUtil.getDate(Long.valueOf(num), 0));
			    			 orderes.setTimetmp(num);
			    			 orderes.setOrderTime(dateUtil.getDate(Long.valueOf(num), 0));
			    			 break;
			    		 case 1:orderes.setOrderAmount(dataUtil.formatMoney(new BigDecimal(num)));break;
			    		 case 2:orderes.setOrderDishesCount(num);break;
			    		 }
			    	 }
			    	 
			    	 
			    }else {
					log.error(dataType+"获取失败");
			     }
			}else {
				log.error("请求失败，[url="+url+",requestJson="+requestJson+"]");
			}
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
		}
		return orderes;
		
	}
	
	/**
	 * 处理 txHash返回值
	 * @param requestJson
	 * @param dataType
	 * @return
	 */
	private String getTxHash(String requestJson,String dataType) {
		log.info(requestJson);
		String txHash = "";
		try {
			String url = urlConf.getUrl();
			if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(requestJson) && StringUtils.isNotEmpty(dataType)) {
				HttpHeaders headers = new HttpHeaders();
			     MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			     headers.setContentType(type);
			     HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			     ResultVO result = restTemplate.postForObject(url,entity,ResultVO.class);
			     if(result != null && StringUtils.isNotEmpty(result.getResult())) {
			    	 txHash = result.getResult();
			        }else {
			        	log.error(dataType+"获取失败，[result="+result.getResult()+"]");
			        }
			}else {
				log.error(dataType+"获取失败，[url="+url+",requestJson="+requestJson+"]");
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
		}
       return txHash;
	}
}

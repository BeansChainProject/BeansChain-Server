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

import com.chb.home.DishesVO;
import com.chb.home.ResultVO;

@Component
public class DishesUtil {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
    private UrlConf urlConf;
	
	@Autowired
    private DataUtil dataUtil;
	
	/**
   	 * 热销菜品
   	 * @return
   	 */
   	public DishesVO getDishes(String dishesNo) {
   		String method = urlConf.getGetDishesList();
   		String st = Long.toHexString(Long.valueOf(dishesNo)).toLowerCase();
   		String data = "0xc464e906" + StringUtils.leftPad(st,64, "0");
   		String to = urlConf.getTo();
   		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \""+data+"\"}, \"latest\"], \"id\": 1}";
   		return dishes(requestJson,"获取热销菜品列表,[dishesNo="+dishesNo+"]");
   	}
	
   	/**
   	 * 处理热销菜品返回值
   	 * @param requestJson
   	 * @param dataType
   	 * @return
   	 */
	private DishesVO dishes(String requestJson,String dataType){
		log.info(requestJson);
		DishesVO dishes = new DishesVO();
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
			    	 for(int i = 0; i < 5;i++) {
			    		 String subStr = str.substring(i*len, len*(i+1));
			    		 String num = String.valueOf(Long.valueOf(subStr,16));
			    		 //log.info("num:"+num+",i:"+num+",Price:"+dataUtil.formatMoney(new BigDecimal(num)));
			    		 switch(i) {
			    		 case 0:dishes.setRestaurantNum(num);break;
			    		 case 1:dishes.setPerPrice(dataUtil.formatMoney(new BigDecimal(num)));break;
			    		 case 2:dishes.setFloorPrice(dataUtil.formatMoney(new BigDecimal(num)));break;
			    		 case 3:dishes.setCeilPrice(dataUtil.formatMoney(new BigDecimal(num)));break;
			    		 case 4:dishes.setSaleNum(num);break;
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
		
		//log.info("perPrice:"+dishes.getPerPrice()+",FloorPrice:"+dishes.getFloorPrice()+",CeilPrice:"+dishes.getCeilPrice());
		return dishes;
	}
}

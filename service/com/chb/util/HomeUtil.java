package com.chb.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chb.home.BlockInfoVO;
import com.chb.home.ResultBlockVO;
import com.chb.home.ResultTransactionVO;
import com.chb.home.ResultVO;
import com.chb.home.TransactionVO;
import com.chb.response.BaseResponse;

@Component
public class HomeUtil {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
    private DateUtil dateUtil;
	
	@Autowired
    private DataUtil dataUtil;
	
	@Autowired
    private UrlConf urlConf;
	
	@Autowired
	private JsonUil jsonUil;
	/**
	 * 1订单总数
	 * @return
	 */
	public ResultVO totalOrder() {
		String method = urlConf.getGetOrderTotal();
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \"0x049cf1ca\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"订单总数");
	}
	
	/**
	 * 2订单总价
	 * @return
	 */
	public ResultVO totalOrderPrice() {
		String method = urlConf.getGetOrderTotalprice();
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \"0x5a7a71f6\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"订单总价");
	}
	
	/**
	 * 3菜品总数
	 * @return
	 */
	public ResultVO dishesNumber() {
		String method = urlConf.getGetDishesAllCount();
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \"0xe5f66545\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"菜品总数");
	}
	
	/**
	 * 4块的高度
	 * @return
	 */
	public ResultVO blockNumber() {
		String method = urlConf.getGetBlockNumber();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[],\"id\":1}";
		return common(requestJson,"块的高度");
	}
	
	
	/**
	 * 5菜品均价
	 * @return
	 */
	public ResultVO dishesPrice() {
		String method = urlConf.getGetDishesPerPrice();
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \"0x8ae8bced\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"菜品均价");
	}
	
	/**
	 * 6交易金额
	 * @return
	 */
	public ResultVO amount() {
		String method = urlConf.getGetAmount();
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \"0x5a7a71f6\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"交易金额");
	}
	
	/**
	 * 7getBlockByNumber
	 * @return
	 */
	public BlockInfoVO getBlockByNumber(String number) {
		String method = urlConf.getGetBlockByNumber();
		String param = "0x" + number;
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[\""+param+"\", true],\"id\":1}";
		return block(requestJson,"获取块信息,[params="+param+"]");
	}
	
	/**
	* 8getBlockByNumber -->transaction
	* @return
	*/
	public List<TransactionVO> getTransactionList(String number) {
		String method = urlConf.getGetBlockByNumber();
		String param = "0x" + number;
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[\""+param+"\", true],\"id\":1}";
		return transactionList(requestJson,"获取块信息中的transaction,[params="+param+"]");
	}
	
	/**
	 * 9获取transaction 菜品种类
	 * @param hash
	 * @return
	 */
	public ResultVO getDishesType(String hash) {
		String method = urlConf.getGetDishesType();
		String data = "0xbaa54400" + hash;
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \""+data+"\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"获取dishesType,[hash="+hash+"]");
	}
	
	/**
	 * 10获取transaction 菜品总数
	 * @param hash
	 * @return
	 */
	public ResultVO getDishesCount(String hash){
		String method = urlConf.getGetDishesAllCount();
		String data = "0x0c0171ea" + hash;
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \""+data+"\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"获取dishesCount,[hash="+hash+"]");
	}
	
	/**
	 * 11get transaction Price
	 * @return
	 */
	public ResultVO getTransactionPrice(String hash) {
		String method = urlConf.getGetTransactionPrice();
		String data = "0xa3d252eb" + hash;
		String to = urlConf.getTo();
		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \""+data+"\"}, \"latest\"], \"id\": 1}";
		return common(requestJson,"获取transactionPrice,[hash="+hash+"]");
	}
	
	/**
   	 * getTransactionByHash
   	 * @return
   	 */
   	public TransactionVO getTransactionByHash(String hash) {
   		String method = urlConf.getGetTransactionDetail();
   		String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[\""+hash+"\"],\"id\":1}";
   		return transaction(requestJson,"获取transactionDetail,[hash="+hash+"]");
   	}
		
		
		
	
	/**
	 * 处理块的transaction列表-List<TransactionVO>
	 * @param requestJson
	 * @param dataType
	 * @return
	 */
	private List<TransactionVO> transactionList(String requestJson,String dataType) {
		log.info(requestJson);
		List<TransactionVO> resultList = new ArrayList<>();
		try {
			String url = urlConf.getUrl();
			if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(requestJson) && StringUtils.isNotEmpty(dataType)) {
				HttpHeaders headers = new HttpHeaders();
			     MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			     headers.setContentType(type);
			     HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			     ResultBlockVO result = restTemplate.postForObject(url,entity,ResultBlockVO.class);
			     if(result != null && result.getResult() != null) {
			    	    BlockInfoVO bvo = result.getResult();
			    	    String transaction_time = "";
			    	    String blockNum = "";
			    	  //number
			    	    if(StringUtils.isNotEmpty(bvo.getNumber())) {
			    	    	String numberStr = bvo.getNumber().substring(2);
			    	    	blockNum = String.valueOf(Long.valueOf(numberStr,16));
			    	    }
			    	    //time
			    	    if(StringUtils.isNotEmpty(bvo.getTimestamp())) {
			    	    	String timeStr = bvo.getTimestamp().substring(2);
			    	    	transaction_time = dateUtil.getDate(Long.valueOf(timeStr,16), 0);
			    	    }
			    	    
			    	    if(bvo.getTransactions() != null && bvo.getTransactions().size() > 0) {
			    	    	List<Map<String,Object>> mapList = bvo.getTransactions();
			    	    	List<Object>  transactionList = mapListToObjectList(mapList,TransactionVO.class);
			    	    	for(Object obj:transactionList) {
			    	    		TransactionVO tvo = (TransactionVO) obj;
			    	    		tvo.setTimestamp(transaction_time);
			    	    		tvo.setBlockNumber(blockNum);
			    	    		if(tvo.getHash() != null && tvo.getHash().length() > 0) {
			    	    			String hash = tvo.getHash().substring(2);
			    	    			ResultVO vo =  getTransactionPrice(hash);
			    	    			if(vo != null) {
			    	    				tvo.setTransactionPrice(dataUtil.formatMoney(new BigDecimal(vo.getResult())));
			    	    			}
			    	    			
			    	    			//菜品总数
					    			ResultVO dishesCountVO =  getDishesCount(hash);
					    			if(dishesCountVO != null) {
					    				tvo.setDishesNumer(dishesCountVO.getResult());
					    			}
			    	    		}else {
			    	    			tvo.setTransactionPrice("--");
			    	    		}
			    	    		resultList.add(tvo);
			    	    	}
			    	    }
			        }else {
						log.error(dataType+"获取失败,[url="+url+",requestJson="+requestJson+"]");
			        }
			}else {
				log.error(dataType+"获取失败,[url="+url+",requestJson="+requestJson+"]");
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
		}
		 
       return resultList;
	}
	
	/**
	 * 处理交易-TransactionVO
	 * @param requestJson
	 * @param dataType
	 * @return
	 */
	private TransactionVO transaction(String requestJson,String dataType) {
		log.info(requestJson);
		TransactionVO transaction = null;
		try {
			String url = urlConf.getUrl();
			if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(requestJson) && StringUtils.isNotEmpty(dataType)) {
				HttpHeaders headers = new HttpHeaders();
			     MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			     headers.setContentType(type);
			     HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			     ResultTransactionVO result = restTemplate.postForObject(url,entity,ResultTransactionVO.class);
			     if(result != null && result.getResult() != null) {
			    	transaction = result.getResult();
			    	transaction.setStatus("成功");
			    	
			    	//blockNumber
		    	    if(StringUtils.isNotEmpty(transaction.getBlockNumber())) {
		    	    	String numberStr = transaction.getBlockNumber().substring(2);
		    	    	transaction.setBlockNumber(String.valueOf(Long.valueOf(numberStr,16)));
		    	    }
			    	//价格
			    	String hash = transaction.getHash().substring(2);
			    	ResultVO priceVO =  getTransactionPrice(hash);
	    			if(priceVO != null) {
	    				transaction.setTransactionPrice(dataUtil.formatMoney(new BigDecimal(priceVO.getResult())));
	    			}
	    			//种类
	    			ResultVO typeVO =  getDishesType(hash);
	    			if(typeVO != null) {
	    				transaction.setDishesType(typeVO.getResult());
	    			}
	    			
	    			//菜品总数
	    			ResultVO dishesCountVO =  getDishesCount(hash);
	    			if(dishesCountVO != null) {
	    				transaction.setDishesNumer(dishesCountVO.getResult());
	    			}
	    			
	    			//转化input内容
	    			if(StringUtils.isNotEmpty(transaction.getInput())) {
	    				transaction.setInput(conversionInput(transaction.getInput()));
	    			}
	    			
			        }else {
						log.error(dataType+"获取失败,[url="+url+",requestJson="+requestJson+"]");
			        }
			}else {
				log.error(dataType+"获取失败,[url="+url+",requestJson="+requestJson+"]");
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
		}
		 
       return transaction;
		
	}
	
	/**
	 * 处理区块-BlockInfoVO
	 * @param requestJson
	 * @param dataType
	 * @return
	 */
	private BlockInfoVO block(String requestJson,String dataType) {
		log.info(requestJson);
		BaseResponse<Object> response = new BaseResponse<>();
		BlockInfoVO bvo = null;
		try {
			String url = urlConf.getUrl();
			if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(requestJson) && StringUtils.isNotEmpty(dataType)) {
				HttpHeaders headers = new HttpHeaders();
			     MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			     headers.setContentType(type);
			     HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			     ResultBlockVO result = restTemplate.postForObject(url,entity,ResultBlockVO.class);
			     if(result != null && result.getResult() != null) {
			    	     bvo = result.getResult();
			    	    //number
			    	    if(StringUtils.isNotEmpty(bvo.getNumber())) {
			    	    	String numberStr = bvo.getNumber().substring(2);
			    	       bvo.setNumber(String.valueOf(Long.valueOf(numberStr,16)));
			    	    }
			    	  //size
			    	    if(StringUtils.isNotEmpty(bvo.getSize())) {
			    	    	String sizeStr = bvo.getSize().substring(2);
			    	       bvo.setSize(String.valueOf(Long.valueOf(sizeStr,16)));
			    	    }
			    	    //time
			    	    if(StringUtils.isNotEmpty(bvo.getTimestamp())) {
			    	    	String timeStr = bvo.getTimestamp().substring(2);
			    	    	String timeStamp = dateUtil.getDate(Long.valueOf(timeStr,16), 0);
			    	       bvo.setTimestamp(timeStamp);
			    	       String[] timeAttr = timeStamp.split(" ");
			    	       bvo.setDay(timeAttr[0]);
			    	       bvo.setHour(timeAttr[1]);
			    	    }
			    	   //Transactions_size
			    	    if(bvo.getTransactions() != null) {
			    	       bvo.setTransactions_size(bvo.getTransactions().size());
			    	       countBlockAmount(bvo);
			    	    }
			        }else {
						log.error(dataType+"获取失败");
			        }
			}else {
				response.getInvalidParams();
				log.error(dataType+"获取失败,[url="+url+",requestJson="+requestJson+"]");
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
		}
		 
       return bvo;
	}
	
	/**
	 * 添加块的订单总价和均价
	 * @param bvo
	 */
	private void countBlockAmount(BlockInfoVO bvo) {
		List<Map<String,Object>> mapList = bvo.getTransactions();
		BigDecimal total = new BigDecimal("0");
		BigDecimal count = new BigDecimal("0");
		if(mapList.size() > 0) {
			try {
				List<Object> transactionList = mapListToObjectList(mapList,TransactionVO.class);
				for(Object obj:transactionList) {
		    		TransactionVO tvo = (TransactionVO) obj;
		    		if(tvo.getHash() != null && tvo.getHash().length() > 0) {
    	    			String hash = tvo.getHash().substring(2);
    	    			//总价
    	    			ResultVO priceVO =  getTransactionPrice(hash);
    	    			if(priceVO != null) {
    	    				total = total.add(new BigDecimal(priceVO.getResult()));
    	    			}
    	    			//菜品总数
    	    			ResultVO dishesCountVO =  getDishesCount(hash);
    	    			if(dishesCountVO != null) {
    	    				count = count.add(new BigDecimal(dishesCountVO.getResult()));
    	    			}
    	    		}
		    	}
				bvo.setDishesCount(count);
				bvo.setBlockAmount(dataUtil.formatMoney(total));
				bvo.setBlockPerAmount(dataUtil.formatMoney(total.divide(new BigDecimal(mapList.size()),1,BigDecimal.ROUND_HALF_UP)));
			} catch (Exception e) {
				log.error("处理块的订单总价和均价出错,[块Number="+bvo.getNumber()+"]" + e.getMessage(),e);
				e.printStackTrace();
			}
		}
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
			     if(result != null && StringUtils.isNotEmpty(result.getResult())) {
			        	String resultStr = result.getResult().substring(2);
			        	if(StringUtils.isNotEmpty(resultStr)) {
			        		result.setResult(String.valueOf(Long.valueOf(resultStr,16)));
			        	}else {
			        		result.setResult("--");
			        		log.error(dataType+"获取失败，[result="+resultStr+"]");
			        	}
			        	
			        }else {
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
	
	/***
     * mapList转换成对象List
     */
	private List<Object> mapListToObjectList(
            List<Map<String, Object>> mapList, Class<?> beanClass)
            throws Exception {
        if (mapList == null)
            return null;

        List<Object> objList = new ArrayList<Object>();
        for (Map<String, Object> map : mapList) {
            Object obj = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(obj, map.get(property.getName()));
                }
            }
            objList.add(obj);
        }
        return objList;
    }
	
	private String conversionInput(String str) {
		String funName = str.substring(0, 10);
		if("0x167e119e".equals(funName)) {
			String content = str.substring(10 +64 * 3);
			System.out.println(content);
			str = "记录交易订单号："+dataUtil.hexStr2Str(content);
		}
		else if("0x8b46b019".equals(funName)){
			//获取数据位置信息
			String addrInfo = str.substring(10 +64 * 4,10 +64 * 5);
			String mealNameInfo = str.substring(10 +64 * 5,10 +64 * 6);
			String priceInfo = str.substring(10 +64 * 6,10 +64 * 7);
			int addrIndex = Integer.parseInt(addrInfo,16)/32;
			int mealNameIndex = Integer.parseInt(mealNameInfo,16)/32;
			int priceIndex = Integer.parseInt(priceInfo,16)/32;
			
			//计算地址和菜品名称的占用行
			int addrRow = mealNameIndex - addrIndex;
			int mealNameRow = priceIndex - mealNameIndex;
			
			String time = str.substring(10 +64 * 2,10 +64 * 3);
			time = dateUtil.getDate(Long.valueOf(time,16), 0);
			
			
			String content = str.substring(10 +64 * 8);//纯数据内容
			String no = content.substring(64,64*2);
			String addr = content.substring(64*3,64*(2 + addrRow));
			//菜品
			String mealNameStr = "[";
			if(mealNameRow > 0) {
				Map<String,Object> map = jsonUil.JsonRead("/WEB-INF/json/dishesDict.json");
				for(int i = 1;i <= mealNameRow; i++) {
					String mealName = content.substring(64*(2+addrRow+i),64*(3+addrRow+i));
					String key = String.valueOf(Integer.parseInt(mealName,16));
					if(String.valueOf(map.get(key)) != "null" && mealNameStr.indexOf(String.valueOf(map.get(key))) == -1) {
						mealNameStr += String.valueOf(map.get(key))+",";
					}
				}
			}
			mealNameStr = mealNameStr.substring(0,mealNameStr.length() - 1) +"]";
			str = "订单号："+dataUtil.hexStr2Str(no)
				+"; 时间："+time
				+"; 地址："+dataUtil.hexStr2Str(addr)
				+"; 菜品："+mealNameStr;
		}
		return str;
		
	}
}

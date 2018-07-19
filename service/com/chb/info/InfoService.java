package com.chb.info;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chb.home.OrderVO;
import com.chb.home.ResultVO;
import com.chb.response.BaseResponse;
import com.chb.util.DataUtil;
import com.chb.util.DateUtil;
import com.chb.util.InfoUtil;
import com.chb.util.JsonUil;

@RestController
@RequestMapping("/info")
public class InfoService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private InfoUtil infoUtil;
	
	@Autowired
    private DataUtil dataUtil;
	
	@Autowired
    private DateUtil dateUtil;
	
	@Autowired
	private JsonUil jsonUil;
	
	@Autowired
	private ServletContext servletContext;
	/**
	 * post订单数据
	 * @param info
	 * @return
	 */
	@PostMapping("/sendOrderInfo")
	private BaseResponse<Object> sendOrderInfo(@RequestBody JsonOrderVO sourcevo){
		log.info("/api/info/sendOrderInfo");
		BaseResponse<Object> response = new BaseResponse<Object>();
		try {
			if(sourcevo != null) {
				OrderVO info = dealJsonOrderVO(sourcevo);
				int index = 0;//参数内容Index
				StringBuffer params = new StringBuffer();
				//拼装params
				
				//no
				int noRow = 2;
				int noLen = info.getOrderNo().length();
				String no = dataUtil.str2HexStr(info.getOrderNo());
				if(no.length() > 64 && no.length() % 64 > 0) {
					noRow += 1;
				}
				String param_no = StringUtils.leftPad(String.valueOf(noLen),64, "0")
						+ StringUtils.rightPad(String.valueOf(no),64*(noRow - 1), "0");
				
				//addr
				int addrRow = 2;
				int addrLen = info.getAddress().length();
				String addr = dataUtil.str2HexStr(info.getAddress());
				if(addr.length() > 64 && addr.length() % 64 > 0) {
					addrRow += 1;
				}
				String param_addr = StringUtils.leftPad(String.valueOf(addrLen),64, "0")
						+ StringUtils.rightPad(String.valueOf(addr),64*(addrRow - 1), "0");
				
				//mealNameID、mealPrice、mealNum等长数组
				int listSize = info.getDishesNameId().size();
				String param_mealNameId = StringUtils.leftPad(String.valueOf(listSize),64, "0")
						+ listToStr(info.getDishesNameId());
				//mealPrice
				String param_mealPrice = StringUtils.leftPad(String.valueOf(listSize),64, "0")
						+ listToStr(info.getDishesPrice());
				//mealNum
				String param_mealNum = StringUtils.leftPad(String.valueOf(listSize),64, "0")
						+ listToStr(info.getDishesNum());
				
				//1-协议规则拼接
				/*no*/
				index = 32 * 8;
				String noIndex = String.valueOf(Integer.toHexString(index).toLowerCase());
				params.append(StringUtils.leftPad(noIndex,64, "0"));
				/*price*/
				String price = Long.toHexString(Long.valueOf(info.getOrderAmount())).toLowerCase();
				params.append(StringUtils.leftPad(price,64, "0"));
				/*time*/
				long long_time = dateUtil.getTimeLongByFormat(info.getOrderTime(),0);
				String time = Long.toHexString(long_time).toLowerCase();
				params.append(StringUtils.leftPad(time,64, "0"));
				/*specific time*/
				long long_sptime = dateUtil.getTimeLongByFormat(info.getOrderSpTime(),3);
				String sptime = Long.toHexString(long_sptime).toLowerCase();
				params.append(StringUtils.leftPad(sptime,64, "0"));
				/*addr*/
				index = index + noRow * 32;
				String addrIndex = String.valueOf(Integer.toHexString(index).toLowerCase());
				params.append(StringUtils.leftPad(addrIndex,64, "0"));
				/*mealNameId*/
				index = index + addrRow * 32;
				String mealNameIndex = String.valueOf(Integer.toHexString(index).toLowerCase());
				params.append(StringUtils.leftPad(mealNameIndex,64, "0"));
				/*mealPrice*/
				index = index + (listSize + 1) * 32;
				String mealPriceIndex = String.valueOf(Integer.toHexString(index).toLowerCase());
				params.append(StringUtils.leftPad(mealPriceIndex,64, "0"));
				/*mealNum*/
				index = index + (listSize + 1) * 32;
				String mealNumIndex = String.valueOf(Integer.toHexString(index).toLowerCase());
				params.append(StringUtils.leftPad(mealNumIndex,64, "0"));
				
				//2-不定长参数值
				params.append(param_no);
				params.append(param_addr);
				params.append(param_mealNameId);
				params.append(param_mealPrice);
				params.append(param_mealNum);
				
				ResultVO result = infoUtil.postOrderInfo(params.toString());
				if(result != null) {
					writeOrderNo(info.getOrderNo());
					log.info("orderNO :"+info.getOrderNo()+"|tx Hash:" + result.getResult());
					saveOrderNoAndHash(result.getResult(),param_no,info.getOrderNo());
					response.setStatus("ok");
					response.setData(result.getResult());
					response.setMessage("post订单数据成功");
				}
			}else {
				response.getInvalidParams();
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		return response;
		
	}
	
	/**
	 * 记录某个Tx的订单号 
	 * @param txHash
	 * @param noStr
	 * @param no
	 */
	private void saveOrderNoAndHash(String txHash,String noStr,String no){
		try {
			String params = txHash.substring(2)+"0000000000000000000000000000000000000000000000000000000000000040"+noStr;
			infoUtil.saveOrderNoAndHash(params);
		}catch(Exception e) {
			log.error("记录订单号失败,["+no+"]");
		}
	}
	
	/**
	 * 记录菜品名称
	 * @param vo
	 * @return
	 */
	private OrderVO dealJsonOrderVO(JsonOrderVO vo) {
		boolean firstFlag = false;
		StringBuffer loginfo = new StringBuffer();
		StringBuffer loginfo2 = new StringBuffer();
		Map<String,Object> map = jsonUil.JsonRead("/WEB-INF/json/dishesDict.json");
		String dishesPath =  servletContext.getRealPath("/WEB-INF/json/dishesDict.json");
		OrderVO order = new OrderVO();
		order.setOrderNo(vo.getOrderNumber());
		BigDecimal price = vo.getPrice().multiply(new BigDecimal(10));
		order.setOrderAmount(String.valueOf(price.intValue()));
		order.setOrderTime(vo.getCreateTime());
		String createPxTime = vo.getCreateTime().split(" ")[0];
		order.setOrderSpTime(createPxTime);
		order.setAddress(vo.getShopAddress());
		loginfo.append("{'OrderNumber':"+vo.getOrderNumber()+",");
		loginfo.append("'CreateTime':"+vo.getCreateTime()+",");
		loginfo.append("'Price':"+vo.getPrice()+",");
		loginfo2.append("'Price':"+price+",");
		loginfo.append("'ShopAddress':"+vo.getShopAddress()+",");
		loginfo.append("'Info':[");
		List<String> dishesNameId = new ArrayList<>();
		List<String> dishesPrice = new ArrayList<>();
		List<String> dishesNum = new ArrayList<>();
		int key = 0;
		if(!map.isEmpty()) {
			key = (int) jsonUil.getMaxKey(map);
		}
		
		// 获取菜品id
		for(JsonDishVO dish:vo.getInfo()) {
			loginfo.append("'{'ShopMenuName':"+dish.getShopMenuName()+",");
			loginfo.append("'PaymentPrice':"+dish.getPaymentPrice()+",");
			loginfo.append("'MenuNumber':"+dish.getMenuNumber()+"}");
			boolean flag = false;
			if(!map.isEmpty() || firstFlag) {
				for(Map.Entry<String,Object>  entry : map.entrySet()) {
					if(dish.getShopMenuName().equals(entry.getValue())) {
						dishesNameId.add(entry.getKey());
						flag = true;
						break;
					}
				}
				if(!flag) {
					StringBuffer sb = new StringBuffer();
					key = key +1;
					sb.append(",\r\n\""+key+"\":\""+dish.getShopMenuName()+"\"");
					String jsonStr = sb.toString();
					boolean dishFlag =jsonUil.appendToJson(dishesPath,jsonStr);
					if(!dishFlag) {
						log.error("记录菜品名称失败，【菜品名称="+dish.getShopMenuName()+"]");
					}else {
						dishesNameId.add(String.valueOf(key));
						key++;
					}
				}
			}else {
				StringBuffer sb = new StringBuffer();
				sb.append(",\r\n\"1\":\""+dish.getShopMenuName()+"\"");
				String jsonStr = sb.toString();
				firstFlag =jsonUil.appendToJson(dishesPath,jsonStr);
				if(!firstFlag) {
					log.error("首次记录菜品名称失败，【菜品名称="+dish.getShopMenuName()+"]");
				}else {
					dishesNameId.add("1");
					key = 1;
				}
			}
			BigDecimal dishprice = dish.getPaymentPrice().multiply(new BigDecimal(10));
			dishesPrice.add(String.valueOf(dishprice.intValue()));
			loginfo2.append(dish.getShopMenuName()+" 'PaymentPrice':"+dishprice+",");
			dishesNum.add(String.valueOf(dish.getMenuNumber()));
			
		}
		
		order.setDishesNameId(dishesNameId);
		order.setDishesPrice(dishesPrice);
		order.setDishesNum(dishesNum);
		loginfo.append("]}");
		log.info(loginfo.toString());
		log.info(loginfo2.toString());
		return order;
		
	}
	
	/**
	 * 记录交易hash
	 * @param txHash
	 * @return
	 */
	private boolean writeOrderNo(String orderNo) {
		boolean flag = false;
		Map<String,Object> txmap = jsonUil.JsonRead("/WEB-INF/json/transaction.json");
		String transactionPath =  servletContext.getRealPath("/WEB-INF/json/transaction.json");
		if(!txmap.isEmpty()) {
			int key = (int) jsonUil.getMaxKey(txmap) + 1;
			StringBuffer sb = new StringBuffer();
			sb.append(",\r\n\""+key+"\":\""+orderNo+"\"");
			String jsonStr = sb.toString();
			boolean txFlag =jsonUil.appendToJson(transactionPath,jsonStr);
			if(!txFlag) {
				log.error("首次记录orderNo失败，【orderNo="+orderNo+"]");
			}
		}else {
			StringBuffer sb = new StringBuffer();
			sb.append("\r\n\"1\":\""+orderNo+"\"");
			String jsonStr = sb.toString();
			boolean txFlag =jsonUil.appendToJson(transactionPath,jsonStr);
			if(!txFlag) {
				log.error("首次记录orderNo失败，【orderNo="+orderNo+"]");
			}
		}
		return flag;
	}
	
	private String listToStr(List<String> list) {
		StringBuffer sb = new StringBuffer();
		for(String string:list) {
			sb.append(StringUtils.leftPad(Integer.toHexString(Integer.valueOf(string)),64, "0"));
		}
		return sb.toString();
	}
}

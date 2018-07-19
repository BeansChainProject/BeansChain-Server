package com.chb.detail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chb.base.BaseVO;
import com.chb.home.BlockInfoVO;
import com.chb.home.DishesVO;
import com.chb.home.OrderVO;
import com.chb.home.TransactionVO;
import com.chb.response.BaseResponse;
import com.chb.util.DishesUtil;
import com.chb.util.HomeUtil;
import com.chb.util.JsonUil;
import com.chb.util.OrderesUtil;

@RestController
@RequestMapping("/detail")
public class DetailService {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private HomeUtil homeUtil;
	@Autowired
	private DishesUtil dishesUtil;
	@Autowired
	private OrderesUtil orderesUtil;
	@Autowired
	private JsonUil jsonUil;
	
	
	/**
	 * 订单详情
	 * @param orderes
	 * @return
	 */
	@PostMapping("/orderes")
	public BaseResponse<Object> orderes(@RequestBody BaseVO query){
		log.info("/api/detail/orderes");
		BaseResponse<Object> response = new BaseResponse<Object>();
		try {
			List<OrderVO> list = new ArrayList<>();
			Map<String,Object> map = jsonUil.JsonRead("/WEB-INF/json/transaction.json");
			int maxkey = (int) jsonUil.getMaxKey(map) + 1;
			if(map != null) {
				for(Map.Entry<String,Object>  entry : map.entrySet()) {
					int key = Integer.valueOf(entry.getKey());
					int startKey = maxkey - (query.getPageNumber() -1) * query.getPageSize();
					int endKey = startKey - query.getPageSize();
					if(key <= startKey && key > endKey) {
						OrderVO order =  orderesUtil.getOrderes(String.valueOf(entry.getValue()));
						String txHash = orderesUtil.getTxHashByOrder(String.valueOf(entry.getValue()));
						if(order != null) {
							if("0.0".equals(order.getOrderAmount()) && "0".equals(order.getOrderDishesCount())) {
								order.setOrderTime("--");
								order.setOrderAmount("--");
								order.setOrderDishesCount("--");
							}
							order.setOrderNo(String.valueOf(entry.getValue()));
							order.setTransactionHash(txHash);
							list.add(order);
						}
					}
				}
			}else {
				response.getIntelalError();
				response.setMessage("获取订单失败,JsonRead error");
			}
			
			if(list.size() > 0) {
				compareToOrderAmount(list);
				response.setStatus("ok");
				response.setData(list);
				response.setMessage("获取订单成功");
			}
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		return response;
		
	}
	
	/**
	 * 热销菜品
	 * @param dishesNo
	 * @return
	 */
	@PostMapping("/hotDishes")
	public BaseResponse<Object> hotDishes(@RequestBody BaseVO query){
		log.info("/api/detail/hotDishes");
		BaseResponse<Object> response = new BaseResponse<Object>();
		try {
			List<DishesVO> list = new ArrayList<>();
			Map<String,Object> map = jsonUil.JsonRead("/WEB-INF/json/dishesDict.json");
			if(map != null) {
				for(Map.Entry<String,Object>  entry : map.entrySet()) {
					int key = Integer.valueOf(entry.getKey());
					int startKey = (query.getPageNumber() -1) * query.getPageSize();
					int endKey = startKey + query.getPageSize();
					if(key > startKey && key <= endKey) {
						DishesVO dishes =  dishesUtil.getDishes(entry.getKey());
						if(dishes != null && (!"0".equals(dishes.getSaleNum()) && !"1".equals(dishes.getSaleNum()))) {
							dishes.setDishName(String.valueOf(entry.getValue()));
							list.add(dishes);
						}else {
							response.getInvalidParams();
							response.setMessage("获取热销菜品失败,[dishesNo="+entry.getKey()+"]");
						}
					}
				}
			}else {
				response.getIntelalError();
				response.setMessage("获取热销菜品失败,JsonRead error");
			}
			
			if(list.size() > 0) {
				compareToSaleNum(list);
				response.setStatus("ok");
				response.setData(list);
				response.setMessage("获取热销菜品成功");
			}
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		return response;
		
	}
	
	/**
	 * 块详情
	 * @param blockNum
	 * @return
	 */
	@GetMapping("/block")
	public BaseResponse<Object> block(String blockNum){
		log.info("/api/detail/block");
		BaseResponse<Object> response = new BaseResponse<Object>();
		try {
			if(StringUtils.isNotEmpty(blockNum)) {
				String iHex = Integer.toHexString(Integer.valueOf(blockNum)).toLowerCase();
				BlockInfoVO block =  homeUtil.getBlockByNumber(iHex);
				if(block != null) {
					response.setStatus("ok");
					response.setData(block);
					response.setMessage("获取块详情成功");
				}
			}else {
				response.setStatus("failed");
				response.setMessage("获取块详情失败,[blockNum="+blockNum+"]");
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		
		return response;
	}
	
	/**
	 * 交易详情
	 * @param hash
	 * @return
	 */
	@GetMapping("/transaction")
	public BaseResponse<Object> transaction(String hash){
		log.info("/api/detail/transaction");
		BaseResponse<Object> response = new BaseResponse<Object>();
		try {
			if(StringUtils.isNotEmpty(hash)) {
				TransactionVO transaction =  homeUtil.getTransactionByHash(hash);
				if(transaction != null) {
					response.setStatus("ok");
					response.setData(transaction);
					response.setMessage("获取transaction详情成功");
				}
			}else {
				response.setStatus("failed");
				response.setMessage("获取transaction详情失败,[hash="+hash+"]");
			}
			
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		
		return response;
	}
	
	
	/**
	 * 按OrderVO 订单总价降序
	 * @param resultList
	 */
	private void compareToOrderAmount(List<OrderVO> resultList) {
		Collections.sort(resultList, new Comparator<OrderVO>() {
			@Override
			public int compare(OrderVO arg0, OrderVO arg1) {
				if(new BigDecimal(arg0.getTimetmp()).compareTo(new BigDecimal(arg1.getTimetmp())) == -1) {
					return 1;
				}else {
					return -1;
				}
			}
			
		});
	}
	
	/**
	 * 按DishesVO 销量降序
	 * @param resultList
	 */
	private void compareToSaleNum(List<DishesVO> resultList) {
		Collections.sort(resultList, new Comparator<DishesVO>() {
			@Override
			public int compare(DishesVO arg0, DishesVO arg1) {
				if(Integer.valueOf(arg0.getSaleNum()) < Integer.valueOf(arg1.getSaleNum())) {
					return 1;
				}else {
					return -1;
				}
			}
			
		});
	}
}

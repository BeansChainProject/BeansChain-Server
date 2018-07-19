package com.chb.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chb.base.BaseVO;
import com.chb.response.BaseResponse;
import com.chb.util.DataUtil;
import com.chb.util.HomeUtil;

@RestController
@RequestMapping("/home")
public class HomeService {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private HomeUtil homeUtil;
	@Autowired
	private DataUtil dataUtil;
	
	@Resource
	private Map<String, String> configProperties;
	
	/**
	 * 综合获取统计数据
	 * @return
	 */
	@GetMapping("/countData")
	public BaseResponse<Object> countData(){
		log.info("/api/home/countData");
		BaseResponse<Object> response = new BaseResponse<Object>();
		try {
			Map<String,String> map = new HashMap<>();
			map.put("totalOrder",null);
			map.put("totalOrderPrice",null);
			map.put("dishesNumber",null);
			map.put("blockNumber",null);
			map.put("dishesPrice",null);
			map.put("amount",null);
			
			ResultVO totalOrder =  homeUtil.totalOrder();
			ResultVO totalOrderPrice =  homeUtil.totalOrderPrice();
			ResultVO dishesNumber =  homeUtil.dishesNumber();
			ResultVO blockNumber =  homeUtil.blockNumber();
			ResultVO dishesPrice =  homeUtil.dishesPrice();
			ResultVO amount =  homeUtil.amount();
			if(totalOrder != null) {
				map.put("totalOrder",totalOrder.getResult());
			}
			if(totalOrderPrice != null) {
				if(totalOrderPrice.getResult() != null && !"--".equals(totalOrderPrice.getResult())) {
					BigDecimal totalOrderPrice_b = new BigDecimal(totalOrderPrice.getResult());
					map.put("totalOrderPrice",dataUtil.formatMoney(totalOrderPrice_b));
				}
			}
			if(dishesNumber != null) {
				map.put("dishesNumber",dishesNumber.getResult());
			}
			if(blockNumber != null) {
				map.put("blockNumber",blockNumber.getResult());
			}
			if(dishesPrice != null) {
				if(dishesPrice.getResult() != null && !"--".equals(dishesPrice.getResult())) {
					BigDecimal dishesPrice_b = new BigDecimal(dishesPrice.getResult());
					map.put("dishesPrice",dataUtil.formatMoney(dishesPrice_b));
				}
			}
			if(amount != null) {
				if(amount.getResult() != null && !"--".equals(amount.getResult())) {
					BigDecimal amount_b = new BigDecimal(amount.getResult());
					map.put("amount",dataUtil.formatMoney(amount_b));
				}
			}
			
			response.setStatus("ok");
			response.setData(map);
			response.setMessage("获取统计数据成功");
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		
		return response;
	}
	
	/**
	 * 依据Number获取块
	 * @return
	 */
	@PostMapping("/blockList")
	public BaseResponse<Object> blockList(@RequestBody BaseVO query){
		log.info("/api/home/blockList");
		BaseResponse<Object> response = new BaseResponse<Object>();
		Map<String,Object> map = new HashMap<>();
		List<BlockInfoVO> list = new ArrayList<>();
		try {
			ResultVO blockNumber =  homeUtil.blockNumber();
			if(blockNumber != null) {
				int total = Integer.valueOf(blockNumber.getResult());
				int num =  Integer.valueOf(blockNumber.getResult()) - (query.getPageNumber() -1) * query.getPageSize();
				int endNum = num - query.getPageSize();
				endNum = endNum >= 0 ?endNum : 0;
				for(int i = num; i > endNum;i--) {
					String iHex = Integer.toHexString(i).toUpperCase();
					BlockInfoVO block =  homeUtil.getBlockByNumber(iHex);
					if(block != null) {
						list.add(block);
					}
				}
				
				if(list.size() >= 0) {
					map.put("total", total);
					map.put("rows", list);
					response.setStatus("ok");
					response.setData(map);
					response.setMessage("获取区块列表成功");
				}else {
					response.setStatus("failed");
					response.setData(map);
					response.setMessage("获取区块列表失败");
				}
			}
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		return response;
	}
	
	/**
	 * 获取块的transaction列表
	 * @return
	 */
	@GetMapping("/transactionList")
	public BaseResponse<Object> transactionList(String blockNums){
		log.info("/api/home/transactionList");
		BaseResponse<Object> response = new BaseResponse<Object>();
		Map<String,Object> map = new HashMap<>();
		List<TransactionVO> list = new ArrayList<>();
		blockNums = "";
		try {
			ResultVO blockNumber =  homeUtil.blockNumber();
			if(blockNumber != null) {
				int total = Integer.valueOf(blockNumber.getResult());
				for(int num = total;num > total - 300;num--) {
					System.out.println(num);
					String iHex = Integer.toHexString(Integer.valueOf(num)).toUpperCase();
					List<TransactionVO> tlist = homeUtil.getTransactionList(iHex);
					if(tlist != null) {
						list.addAll(tlist);
					}
					
					if(list.size() >= 20) {
						break;
					}
				}
			}
			if(list.size() >= 0) {
				map.put("total", list.size());
				map.put("rows", list);
				response.setStatus("ok");
				response.setData(map);
				response.setMessage("获取transaction列表成功");
			}else {
				response.setStatus("failed");
				response.setData(map);
				response.setMessage("获取transaction列表失败");
			}
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		return response;
	}
	
	/**
	 * 根据块分页获取transaction列表
	 * @return
	 */
	@PostMapping("/allTransactionList")
	public BaseResponse<Object> allTransactionList(@RequestBody BaseVO query){
		log.info("/api/home/allTransactionList");
		BaseResponse<Object> response = new BaseResponse<Object>();
		try {
			ResultVO blockNumber =  homeUtil.blockNumber();
			if(blockNumber != null) {
				String blockNums = "";
				int total = Integer.valueOf(blockNumber.getResult());
				int num =  Integer.valueOf(blockNumber.getResult()) - (query.getPageNumber() -1) * query.getPageSize();
				int endNum = num - query.getPageSize();
				endNum = endNum >= 0 ?endNum : 0;
				for(int i = num; i > endNum;i--) {
					blockNums += i +",";
				}
				blockNums = blockNums.substring(0,blockNums.length() - 1);
				response = transactionList(blockNums);
			}
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		return response;
	}
}

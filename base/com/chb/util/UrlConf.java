package com.chb.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UrlConf {
private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private Map<String, String> configProperties;
	
	private String url = "";
	private String to = "";
	private String from = "";
	private String getBlockNumber = "";
	private String getOrderTotal = "";
	private String getOrderTotalprice = "";
	private String getOrderDay = "";
	private String getDishesAllCount = "";
	private String getDishesPerPrice = "";
	private String getAmount = "";
	private String getTransactionPrice = "";
	private String getTransactionDetail = "";
	private String getBlockByNumber = "";
	private String getDishesType = "";
	private String getDishesCount = "";
	private String getDishesList = "";
	private String getOrderesList = "";
	private String sendTransaction = "";
	
	
	@PostConstruct
	private void init() throws IllegalAccessException, InvocationTargetException {
		log.info("load chb conf...");
		if("url_1101".equals(configProperties.get("url_switch"))) {
			this.url = configProperties.get("url_1101");
			this.to = configProperties.get("to_1101");
			this.from = configProperties.get("from_1101");
			this.getBlockNumber = configProperties.get("getBlockNumber");
			this.getOrderTotal = configProperties.get("getOrderTotal");
			this.getOrderTotalprice = configProperties.get("getOrderTotalprice");
			this.getOrderDay = configProperties.get("getOrderDay");
			this.getDishesAllCount = configProperties.get("getDishesAllCount");
			this.getDishesPerPrice = configProperties.get("getDishesPerPrice");
			this.getAmount = configProperties.get("getAmount");
			this.getTransactionPrice = configProperties.get("getTransactionPrice");
			this.getTransactionDetail = configProperties.get("getTransactionDetail");
			this.getBlockByNumber = configProperties.get("getBlockByNumber");
			this.getDishesType = configProperties.get("getDishesType");
			this.getDishesCount = configProperties.get("getDishesCount");
			this.getDishesList = configProperties.get("getDishesList");
			this.getOrderesList = configProperties.get("getOrderesList");
			this.sendTransaction = configProperties.get("sendTransaction");
		}else {
			this.url = configProperties.get("url_sz");
			this.to = configProperties.get("to_sz");
			this.from = configProperties.get("from_sz");
			this.getBlockNumber = configProperties.get("getBlockNumber_sz");
			this.getOrderTotal = configProperties.get("getOrderTotal_sz");
			this.getOrderTotalprice = configProperties.get("getOrderTotalprice_sz");
			this.getOrderDay = configProperties.get("getOrderDay_sz");
			this.getDishesAllCount = configProperties.get("getDishesAllCount_sz");
			this.getDishesPerPrice = configProperties.get("getDishesPerPrice_sz");
			this.getAmount = configProperties.get("getAmount_sz");
			this.getTransactionPrice = configProperties.get("getTransactionPrice_sz");
			this.getTransactionDetail = configProperties.get("getTransactionDetail_sz");
			this.getBlockByNumber = configProperties.get("getBlockByNumber_sz");
			this.getDishesType = configProperties.get("getDishesType_sz");
			this.getDishesCount = configProperties.get("getDishesCount_sz");
			this.getDishesList = configProperties.get("getDishesList_sz");
			this.getOrderesList = configProperties.get("getOrderesList_sz");
			this.sendTransaction = configProperties.get("sendTransaction_sz");
		}
	}

	public String getGetBlockNumber() {
		return getBlockNumber;
	}

	public void setGetBlockNumber(String getBlockNumber) {
		this.getBlockNumber = getBlockNumber;
	}

	public String getGetOrderTotal() {
		return getOrderTotal;
	}

	public void setGetOrderTotal(String getOrderTotal) {
		this.getOrderTotal = getOrderTotal;
	}

	public String getGetOrderTotalprice() {
		return getOrderTotalprice;
	}

	public void setGetOrderTotalprice(String getOrderTotalprice) {
		this.getOrderTotalprice = getOrderTotalprice;
	}

	public String getGetOrderDay() {
		return getOrderDay;
	}

	public void setGetOrderDay(String getOrderDay) {
		this.getOrderDay = getOrderDay;
	}

	public String getGetDishesAllCount() {
		return getDishesAllCount;
	}

	public void setGetDishesAllCount(String getDishesAllCount) {
		this.getDishesAllCount = getDishesAllCount;
	}

	public String getGetDishesPerPrice() {
		return getDishesPerPrice;
	}

	public void setGetDishesPerPrice(String getDishesPerPrice) {
		this.getDishesPerPrice = getDishesPerPrice;
	}

	public String getGetAmount() {
		return getAmount;
	}

	public void setGetAmount(String getAmount) {
		this.getAmount = getAmount;
	}

	public String getGetTransactionPrice() {
		return getTransactionPrice;
	}

	public void setGetTransactionPrice(String getTransactionPrice) {
		this.getTransactionPrice = getTransactionPrice;
	}

	public String getGetTransactionDetail() {
		return getTransactionDetail;
	}

	public void setGetTransactionDetail(String getTransactionDetail) {
		this.getTransactionDetail = getTransactionDetail;
	}

	public String getGetBlockByNumber() {
		return getBlockByNumber;
	}

	public void setGetBlockByNumber(String getBlockByNumber) {
		this.getBlockByNumber = getBlockByNumber;
	}

	public String getGetDishesType() {
		return getDishesType;
	}

	public void setGetDishesType(String getDishesType) {
		this.getDishesType = getDishesType;
	}

	public String getGetDishesCount() {
		return getDishesCount;
	}

	public void setGetDishesCount(String getDishesCount) {
		this.getDishesCount = getDishesCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGetDishesList() {
		return getDishesList;
	}

	public void setGetDishesList(String getDishesList) {
		this.getDishesList = getDishesList;
	}

	public String getGetOrderesList() {
		return getOrderesList;
	}

	public void setGetOrderesList(String getOrderesList) {
		this.getOrderesList = getOrderesList;
	}

	public String getSendTransaction() {
		return sendTransaction;
	}

	public void setSendTransaction(String sendTransaction) {
		this.sendTransaction = sendTransaction;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	
	
}

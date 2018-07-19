package com.chb.home;

import java.util.List;

public class OrderVO {
	private String orderNo;//订单序号
	private String timetmp;//订单时间戳
	private String orderTime;//订单时间
	private String orderSpTime;//订单时间（年月日）
	private String orderAmount;//订单总价
	private String orderDishesCount;//菜品种类
	private String transactionHash;//交易hash
	
	private String address;//地址
	private List<String> dishesNameId;//菜品ID
	private List<String> dishesPrice;//菜品价格
	private List<String> dishesNum;//菜品份数
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderDishesCount() {
		return orderDishesCount;
	}
	public void setOrderDishesCount(String orderDishesCount) {
		this.orderDishesCount = orderDishesCount;
	}
	public String getTransactionHash() {
		return transactionHash;
	}
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	public List<String> getDishesNameId() {
		return dishesNameId;
	}
	public void setDishesNameId(List<String> dishesNameId) {
		this.dishesNameId = dishesNameId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getDishesPrice() {
		return dishesPrice;
	}
	public void setDishesPrice(List<String> dishesPrice) {
		this.dishesPrice = dishesPrice;
	}
	public List<String> getDishesNum() {
		return dishesNum;
	}
	public void setDishesNum(List<String> dishesNum) {
		this.dishesNum = dishesNum;
	}
	public String getOrderSpTime() {
		return orderSpTime;
	}
	public void setOrderSpTime(String orderSpTime) {
		this.orderSpTime = orderSpTime;
	}
	public String getTimetmp() {
		return timetmp;
	}
	public void setTimetmp(String timetmp) {
		this.timetmp = timetmp;
	}
	
}

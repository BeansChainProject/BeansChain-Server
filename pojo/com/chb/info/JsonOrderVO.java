package com.chb.info;

import java.math.BigDecimal;
import java.util.List;

public class JsonOrderVO {
	private String OrderNumber;
	private String CreateTime;
	private String CreatePxTime;
	private BigDecimal Price;
	private BigDecimal SalePrice;
	private String PayType;
	private String Ask;
	private BigDecimal PeopleNum;
	private String ShopAddress;
	private List<JsonDishVO> Info;
	public String getOrderNumber() {
		return OrderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		OrderNumber = orderNumber;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public BigDecimal getPrice() {
		return Price;
	}
	public void setPrice(BigDecimal price) {
		Price = price;
	}
	public BigDecimal getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		SalePrice = salePrice;
	}
	public String getPayType() {
		return PayType;
	}
	public void setPayType(String payType) {
		PayType = payType;
	}
	public String getAsk() {
		return Ask;
	}
	public void setAsk(String ask) {
		Ask = ask;
	}
	public BigDecimal getPeopleNum() {
		return PeopleNum;
	}
	public void setPeopleNum(BigDecimal peopleNum) {
		PeopleNum = peopleNum;
	}
	public String getShopAddress() {
		return ShopAddress;
	}
	public void setShopAddress(String shopAddress) {
		ShopAddress = shopAddress;
	}
	public List<JsonDishVO> getInfo() {
		return Info;
	}
	public void setInfo(List<JsonDishVO> info) {
		Info = info;
	}
	public String getCreatePxTime() {
		return CreatePxTime;
	}
	public void setCreatePxTime(String createPxTime) {
		CreatePxTime = createPxTime;
	}
	
	
}

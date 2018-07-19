package com.chb.info;

import java.math.BigDecimal;

public class JsonDishVO {
	private String ShopMenuName;
	private BigDecimal PaymentPrice;
	private BigDecimal MenuUnit;
	private BigDecimal IsAdd;
	private BigDecimal MenuNumber;
	private String Taboos;
	private BigDecimal UnitPrice;
	public String getShopMenuName() {
		return ShopMenuName;
	}
	public void setShopMenuName(String shopMenuName) {
		ShopMenuName = shopMenuName;
	}
	public BigDecimal getPaymentPrice() {
		return PaymentPrice;
	}
	public void setPaymentPrice(BigDecimal paymentPrice) {
		PaymentPrice = paymentPrice;
	}
	public BigDecimal getMenuUnit() {
		return MenuUnit;
	}
	public void setMenuUnit(BigDecimal menuUnit) {
		MenuUnit = menuUnit;
	}
	public BigDecimal getIsAdd() {
		return IsAdd;
	}
	public void setIsAdd(BigDecimal isAdd) {
		IsAdd = isAdd;
	}
	public BigDecimal getMenuNumber() {
		return MenuNumber;
	}
	public void setMenuNumber(BigDecimal menuNumber) {
		MenuNumber = menuNumber;
	}
	public String getTaboos() {
		return Taboos;
	}
	public void setTaboos(String taboos) {
		Taboos = taboos;
	}
	public BigDecimal getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		UnitPrice = unitPrice;
	}
	
	
}

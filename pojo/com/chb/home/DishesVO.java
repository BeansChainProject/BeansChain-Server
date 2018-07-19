package com.chb.home;

/**
 * 菜品列表
 * @author houzy
 *
 */
public class DishesVO {
	private String dishName;//菜品名称
	private String restaurantNum;//在售餐厅数
	private String perPrice;//均价
	private String floorPrice;//最低价
	private String ceilPrice;//最高价
	private String saleNum;//销售数量
	public String getRestaurantNum() {
		return restaurantNum;
	}
	public void setRestaurantNum(String restaurantNum) {
		this.restaurantNum = restaurantNum;
	}
	public String getPerPrice() {
		return perPrice;
	}
	public void setPerPrice(String perPrice) {
		this.perPrice = perPrice;
	}
	public String getFloorPrice() {
		return floorPrice;
	}
	public void setFloorPrice(String floorPrice) {
		this.floorPrice = floorPrice;
	}
	public String getCeilPrice() {
		return ceilPrice;
	}
	public void setCeilPrice(String ceilPrice) {
		this.ceilPrice = ceilPrice;
	}
	public String getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(String saleNum) {
		this.saleNum = saleNum;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	
	
}

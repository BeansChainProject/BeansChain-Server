package com.chb.base;

/**
 * 表格控件Ajax默认查询基础信息类
 */
public class BaseVO {
	private int pageSize = 10;
	private int pageNumber = 1;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
}

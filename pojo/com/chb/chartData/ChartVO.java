package com.chb.chartData;

import java.util.List;

public class ChartVO {
	private List<String> xData;
	
	private List<Long> timeTmp;
	
	private List<Long> yData;

	public List<String> getxData() {
		return xData;
	}

	public void setxData(List<String> xData) {
		this.xData = xData;
	}

	public List<Long> getyData() {
		return yData;
	}

	public void setyData(List<Long> yData) {
		this.yData = yData;
	}

	public List<Long> getTimeTmp() {
		return timeTmp;
	}

	public void setTimeTmp(List<Long> timeTmp) {
		this.timeTmp = timeTmp;
	}

}

package com.chb.chartData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chb.home.ResultVO;
import com.chb.response.BaseResponse;
import com.chb.util.DateUtil;
import com.chb.util.HomeUtil;
import com.chb.util.UrlConf;


@RestController
@RequestMapping("/chart")
public class ChartData {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private HomeUtil homeUtil;
	@Resource
	private Map<String, String> configProperties;
	@Autowired
    private UrlConf urlConf;
	
	/**
	 * 一周的订单曲线数据
	 * @return
	 */
	@GetMapping("/data")
	public BaseResponse<Object> chartData() {
		log.info("/api/chart/data");
		BaseResponse<Object> response = new BaseResponse<>();
		try {
			//TODO  获取并处理接口数据
			DateTime day = new DateTime().minusDays(5);
			long timeTmp = day.minusDays(1).getMillis() / 1000;
			timeTmp = DateUtil.getDayTime(timeTmp);
			
			//封装前端应用数据
			List<String> xData = new ArrayList<String>();
			List<Long> yData = new ArrayList<Long>();
			xData.add(EnStr(day.getDayOfWeek()));
			yData.add(chartDataOfDay(timeTmp));
			for(int i = 1;i <= 6;i++) {
				 day = day.plusDays(1);
				timeTmp += 24 * 3600;
				timeTmp = DateUtil.getDayTime(timeTmp);
				log.info(timeTmp);
				xData.add(EnStr(day.getDayOfWeek()));
				yData.add(chartDataOfDay(timeTmp));
			}
			ChartVO chart = new ChartVO();
			chart.setxData(xData);
			chart.setyData(yData);
			response.setStatus("ok");
			response.setData(chart);
			response.setMessage("获取chart数据");
		}catch(Exception e) {
			log.error("error exception:" + e.getMessage(),e);
			response.getIntelalError();
		}
		return response;
	}
	
	/**
	 * 周天转换
	 * @param dayOfWeek
	 * @return
	 */
	private String EnStr(int dayOfWeek) {
		String dayStr =  "未知";
		switch(dayOfWeek) { 
		  case 1: 
			  dayStr = "SUN";
		      break; 
		  case 2: 
			  dayStr = "MON";
		     break; 
		 case 3: 
			 dayStr = "TUES";
		     break; 
		 case 4: 
			 dayStr = "WED";
		     break; 
		 case 5: 
			 dayStr = "THUR";
		     break; 
		 case 6: 
			 dayStr = "FRI";
		     break; 
		 case 7: 
			 dayStr = "SAT";
		    break; 
		}
		return dayStr;
	}
	
	
	/**
	 * 获取每天的订单数
	 * @param time
	 * @return
	 */
	private long chartDataOfDay(long time) {
		long num = 0;
		String st = Long.toHexString(time).toLowerCase();
		if(StringUtils.isNotEmpty(st)) {
			String data = "0x5d067073" + StringUtils.leftPad(st,64, "0");
			String to = urlConf.getTo();
			String method = configProperties.get("getOrderDay");
			String requestJson = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\",\"params\":[{\"to\": \""+to+"\", \"data\": \""+data+"\"}, \"latest\"], \"id\": 1}";
			ResultVO result =  homeUtil.common(requestJson,"时间["+time+"]订单数");
			
			if(result != null && result.getResult() != null && !"--".equals(result.getResult())) {
				num = Long.valueOf(result.getResult());
			}
		}
		
		return num;
	}
}

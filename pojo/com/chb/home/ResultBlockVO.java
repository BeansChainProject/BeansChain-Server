package com.chb.home;

public class ResultBlockVO {
	private long id;
	private String jsonrpc;
	private BlockInfoVO result;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJsonrpc() {
		return jsonrpc;
	}
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	public BlockInfoVO getResult() {
		return result;
	}
	public void setResult(BlockInfoVO result) {
		this.result = result;
	}
	
	
}

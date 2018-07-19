package com.chb.home;

public class ResultTransactionVO {
	private long id;
	private String jsonrpc;
	private TransactionVO result;
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
	public TransactionVO getResult() {
		return result;
	}
	public void setResult(TransactionVO result) {
		this.result = result;
	}
	
	
}

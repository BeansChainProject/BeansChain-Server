package com.chb.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BlockInfoVO{
	private String difficulty;
	private String extraData;
	private String gasLimit;
	private String gasUsed;
	private String hash;
	private String logsBloom;
	private String miner;
	private String mixHash;
	private String nonce;
	private String number;
	private String parentHash;
	private String receiptsRoot;
	private String sha3Uncles;
	private String size;
	private String stateRoot;
	private  String timestamp;
	private String totalDifficulty;
	private List<Object> transactions = new ArrayList<Object>();
	private String transactionsRoot;
	private List<Object> uncles = new ArrayList<Object>();
	
	private long transactions_size;
	private String BlockAmount;
	private String BlockPerAmount;
	private BigDecimal dishesCount;//块的菜品总数
	
	private String Day;//年-月-日
	private String hour;//时-分-秒
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getExtraData() {
		return extraData;
	}
	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
	public String getGasLimit() {
		return gasLimit;
	}
	public void setGasLimit(String gasLimit) {
		this.gasLimit = gasLimit;
	}
	public String getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getLogsBloom() {
		return logsBloom;
	}
	public void setLogsBloom(String logsBloom) {
		this.logsBloom = logsBloom;
	}
	public String getMiner() {
		return miner;
	}
	public void setMiner(String miner) {
		this.miner = miner;
	}
	public String getMixHash() {
		return mixHash;
	}
	public void setMixHash(String mixHash) {
		this.mixHash = mixHash;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getParentHash() {
		return parentHash;
	}
	public void setParentHash(String parentHash) {
		this.parentHash = parentHash;
	}
	public String getReceiptsRoot() {
		return receiptsRoot;
	}
	public void setReceiptsRoot(String receiptsRoot) {
		this.receiptsRoot = receiptsRoot;
	}
	public String getSha3Uncles() {
		return sha3Uncles;
	}
	public void setSha3Uncles(String sha3Uncles) {
		this.sha3Uncles = sha3Uncles;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getStateRoot() {
		return stateRoot;
	}
	public void setStateRoot(String stateRoot) {
		this.stateRoot = stateRoot;
	}
	public String getTotalDifficulty() {
		return totalDifficulty;
	}
	public void setTotalDifficulty(String totalDifficulty) {
		this.totalDifficulty = totalDifficulty;
	}
	public List getTransactions() {
		return transactions;
	}
	public void setTransactions(List transactions) {
		this.transactions = transactions;
	}
	public String getTransactionsRoot() {
		return transactionsRoot;
	}
	public void setTransactionsRoot(String transactionsRoot) {
		this.transactionsRoot = transactionsRoot;
	}
	public List getUncles() {
		return uncles;
	}
	public void setUncles(List uncles) {
		this.uncles = uncles;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public long getTransactions_size() {
		return transactions_size;
	}
	public void setTransactions_size(long transactions_size) {
		this.transactions_size = transactions_size;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getBlockAmount() {
		return BlockAmount;
	}
	public void setBlockAmount(String blockAmount) {
		BlockAmount = blockAmount;
	}
	public String getBlockPerAmount() {
		return BlockPerAmount;
	}
	public void setBlockPerAmount(String blockPerAmount) {
		BlockPerAmount = blockPerAmount;
	}
	public BigDecimal getDishesCount() {
		return dishesCount;
	}
	public void setDishesCount(BigDecimal dishesCount) {
		this.dishesCount = dishesCount;
	}
	public String getDay() {
		return Day;
	}
	public void setDay(String day) {
		Day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	
}

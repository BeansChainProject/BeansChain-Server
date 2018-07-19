package com.chb.response;

import java.io.Serializable;

/**
 * API接口返回通用消息体
 * 
 * @author Aris
 *
 */
public class BaseResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6976459601257886981L;

	/**
	 * 状态： ok、failed
	 */
	private String status;

	/**
	 * HTTP状态码
	 * 
	 */
	private String code;

	/**
	 * 状态码消息
	 */
	private String message;

	/**
	 * 实体数据
	 */
	private T data;

	/**
	 * 前端用来判断是否包含权限
	 */
	private boolean permission = true;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 服务器发生内部错误
	 * 
	 * @return
	 */
	public void getIntelalError() {
		this.setStatus("failed");
		this.setMessage("服务器发生内部错误");
		this.setCode("500");
	}

	/**
	 * 无效cookies
	 */
	public void getInvalidCookiesError() {
		this.setStatus("failed");
		this.setMessage("无效Cookie");
		this.setCode("404");
	}

	/**
	 * 无效参数
	 */
	public void getInvalidParams() {
		this.setStatus("failed");
		this.setMessage("无效参数");
		this.setCode("404");
	}

	public void addSuccess() {
		this.setStatus("ok");
		this.setMessage("新增成功");
		this.setCode("200");
	}

	public void addFailed(String message) {
		this.setStatus("failed");
		this.setMessage("新增失败,原因:" + message);
		this.setCode("404");
	}

	public void modifySuccess() {
		this.setStatus("ok");
		this.setMessage("修改成功");
		this.setCode("200");
	}

	public void modifyFailed(String message) {
		this.setStatus("failed");
		this.setMessage("修改失败,原因:" + message);
		this.setCode("404");
	}

	public void deleteSuccess() {
		this.setStatus("ok");
		this.setMessage("删除成功");
		this.setCode("200");
	}

	public void deleteFailed(String message) {
		this.setStatus("failed");
		this.setMessage("删除失败,原因:" + message);
		this.setCode("404");
	}

	public void uploadFailed() {
		this.setStatus("failed");
		this.setMessage("上传失败");
		this.setCode("500");
	}

	public void uploadSuccess() {
		this.setStatus("ok");
		this.setMessage("上传成功");
		this.setCode("200");
	}

	public void listImgSuccess() {
		this.setStatus("ok");
		this.setMessage("");
		this.setCode("200");
	}

	public void listImgFailed() {
		this.setStatus("failed");
		this.setMessage("获取图片失败");
		this.setCode("500");
	}

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}
}

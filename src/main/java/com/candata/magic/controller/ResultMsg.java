package com.candata.magic.controller;


import com.alibaba.fastjson.JSONObject;

public class ResultMsg {
	private String msg;
	private int count;
	private JSONObject jsonMsg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public JSONObject getJsonMsg() {
		return jsonMsg;
	}
	public void setJsonMsg(JSONObject jsonMsg) {
		this.jsonMsg = jsonMsg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}

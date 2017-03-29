package com.candata.magic.controller;

import java.util.List;

import com.candata.magic.pojo.Portal;

public class ResultMsg {
	private String msg;
	private int count;
	private List<Portal> list;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Portal> getList() {
		return list;
	}
	public void setList(List<Portal> list) {
		this.list = list;
	}
}

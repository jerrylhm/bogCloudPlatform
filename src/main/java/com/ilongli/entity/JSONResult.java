package com.ilongli.entity;

import java.util.HashMap;
import java.util.Map;

public class JSONResult {
	private int code;   //返回码 非0即失败
    private String msg; //消息提示
    private Map<String, Object> data; //返回的数据

    public JSONResult(){};
 
    public JSONResult(int code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
 
    public static JSONResult success() {
        return success(new HashMap<>(0));
    }
    public static JSONResult success(Map<String, Object> data) {
        return new JSONResult(0, "解析成功", data);
    }
    public static JSONResult failed() {
        return failed("解析失败");
    }
    public static JSONResult failed(String msg) {
        return failed(-1, msg);
    }
    public static JSONResult failed(int code, String msg) {
        return new JSONResult(code, msg, new HashMap<>(0));
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}

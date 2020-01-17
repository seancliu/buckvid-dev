package com.buckvid.utils;

/**
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：success
 * 				500：error w/ error msg
 * 				501：bean verification error, return via map
 * 				502：token error intercepted by interceptor
 * 				555：malicious thrown msg
 */
public class BuckvidJSONResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;
    
    private String ok;	// 不使用

    public static BuckvidJSONResult build(Integer status, String msg, Object data) {
        return new BuckvidJSONResult(status, msg, data);
    }

    public static BuckvidJSONResult ok(Object data) {
        return new BuckvidJSONResult(data);
    }

    public static BuckvidJSONResult ok() {
        return new BuckvidJSONResult(null);
    }
    
    public static BuckvidJSONResult errorMsg(String msg) {
        return new BuckvidJSONResult(500, msg, null);
    }
    
    public static BuckvidJSONResult errorMap(Object data) {
        return new BuckvidJSONResult(501, "error", data);
    }
    
    public static BuckvidJSONResult errorTokenMsg(String msg) {
        return new BuckvidJSONResult(502, msg, null);
    }
    
    public static BuckvidJSONResult errorException(String msg) {
        return new BuckvidJSONResult(555, msg, null);
    }

    public BuckvidJSONResult() {

    }

    public BuckvidJSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BuckvidJSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

}

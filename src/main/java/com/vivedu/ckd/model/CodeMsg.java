package com.vivedu.ckd.model;

public enum CodeMsg {

    SERVER_EXCEPTION(-1 ,"系统错误"),
    BIND_SUCESS(0, "请求成功"),
    PARARM_EXCEPTION(10001,"参数错误"),
    CODE_EXCEPTION(10002,"代码错误"),
    SHUJU_EXCEPTION(10003,"数据库错误"),
    TIME_EXCEPTION(10004, "请求过期"),
    REQUEST_EXCEPTION(10005, "请求非法");
    private int code;
    private String message;

    CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

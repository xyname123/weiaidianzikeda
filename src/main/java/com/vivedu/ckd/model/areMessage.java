package com.vivedu.ckd.model;

public enum areMessage {

    SERVER(0 ,"全部"),
    BIND(1, "Fitouch70吋大屏×2"),
    PARARM(2,"希沃86吋大屏+希沃65吋大屏×4"),
    CODE(3,"希沃86吋大屏+希沃70吋大屏×6"),
    SHUJU(4,"希沃86吋大屏+希沃70吋大屏×4"),
    TIME(5, "索尼6000lm激光投影×2+爱课堂多视窗"),
    REQUEST(6, "索尼6000lm激光投影×2+爱课堂多视窗");
    private int code;
    private String message;

    areMessage(int code, String message) {
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

package com.vivedu.ckd.model;

import lombok.Data;

import javax.xml.ws.Response;

@Data
public class response {

    private  int code;
    private  String  message;
    private  Object  data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }

    public response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static response success(int code, String message, Object data) {
        return  new response(code,message,data);
    }
}

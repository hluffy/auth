package com.welleplus.result;

import com.welleplus.myenum.ReturnStatus;

public class Result {
    private String code;
    private String status;
    private String msg;
    private Object data;
    private ReturnStatus description;

    public String getCode() {
        return this.description.getCode();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return this.description.getMsg();
    }

    public void setStatus(String status) {
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

    public ReturnStatus getDescription() {
        return description;
    }

    public void setDescription(ReturnStatus description) {
        this.description = description;
    }
}

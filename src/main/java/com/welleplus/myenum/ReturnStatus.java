package com.welleplus.myenum;



public enum ReturnStatus {
    SYSTEM_ERROR("10000","system error"),
    NO_LOGIN("10001","no login"),
    NO_PROMISE("10002","no promise"),
    PARAM_NOT_EMPTY("10003","param not empty"),
    NO_USERNAME("10004","no username"),
    ERROR_PASSWORD("10005","password error"),
    USER_EXIST("10006","username existed"),
    DISABLED_ACCOUNT("10007","account disabled"),
    LOCKED_ACCOUNT("10008","account locked"),
    SUCCESS("0","success");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ReturnStatus(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}

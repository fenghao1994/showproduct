package com.shfound.showproduct.controller.result;

public class UserResult {
    /**
     * 1000 成功 1001 失败
     */
    private int code;
    private String msg;

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
}

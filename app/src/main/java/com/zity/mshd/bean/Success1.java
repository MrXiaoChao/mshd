package com.zity.mshd.bean;

/**
 * Created by luochao on 2017/7/20.
 */

public class Success1 {

    /**
     * message : 验证码超时!
     * success : false
     */

    private String message;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

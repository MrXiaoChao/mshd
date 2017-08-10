package com.zity.mshd.bean;

/**
 * Created by luochao on 2017/8/1.
 * eventBus消息传递
 */

public class MessageEvent {

    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

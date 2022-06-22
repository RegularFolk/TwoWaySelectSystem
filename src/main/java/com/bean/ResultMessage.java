package com.bean;

import java.io.Serializable;

/**
 * 封装返回结果
 */
public class ResultMessage implements Serializable {
    private boolean flag;//执行结果，true为执行成功 false为执行失败
    private String message;//返回结果信息
    private Object data;//返回数据

    public ResultMessage(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public ResultMessage(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public ResultMessage(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "flag=" + flag +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

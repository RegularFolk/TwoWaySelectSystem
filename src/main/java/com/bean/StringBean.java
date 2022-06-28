package com.bean;

public class StringBean {
    String string;

    public StringBean(String string) {
        this.string = string;
    }

    public StringBean() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "StringBean{" +
                "string='" + string + '\'' +
                '}';
    }
}

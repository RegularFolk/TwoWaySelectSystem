package com.bean;

public class IntBean {
    Integer id;

    @Override
    public String toString() {
        return "IntBean{" +
                "id=" + id +
                '}';
    }

    public IntBean() {
    }

    public IntBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

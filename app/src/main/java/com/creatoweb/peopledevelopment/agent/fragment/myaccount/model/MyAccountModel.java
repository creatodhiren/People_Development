package com.creatoweb.peopledevelopment.agent.fragment.myaccount.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyAccountModel {

    @SerializedName("Message")
    private String message;

    @SerializedName("data")
    private List<Data> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
package com.creatoweb.peopledevelopment.agent.activity.login.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}

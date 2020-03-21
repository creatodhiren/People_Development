package com.creatoweb.peopledevelopment.agent.fragment.registration.accountAmountModel;

import com.google.gson.annotations.SerializedName;

public class AccountAmountModel {

    @SerializedName("Message")
    private String message;

    @SerializedName("data")
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
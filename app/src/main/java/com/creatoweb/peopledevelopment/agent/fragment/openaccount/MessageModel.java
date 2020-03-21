package com.creatoweb.peopledevelopment.agent.fragment.openaccount;

import com.google.gson.annotations.SerializedName;

public class MessageModel {

    @SerializedName("Message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
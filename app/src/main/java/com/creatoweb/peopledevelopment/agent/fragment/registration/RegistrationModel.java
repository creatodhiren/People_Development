package com.creatoweb.peopledevelopment.agent.fragment.registration;

import com.google.gson.annotations.SerializedName;

public class RegistrationModel {

    @SerializedName("Message")
    private String message;
    @SerializedName("data")
    private Boolean data;
    @SerializedName("status")
    private String status;

    @SerializedName("memberActId")
    private String memberActId;

    public String getMemberActId() {
        return memberActId;
    }

    public void setMemberActId(String memberActId) {
        this.memberActId = memberActId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
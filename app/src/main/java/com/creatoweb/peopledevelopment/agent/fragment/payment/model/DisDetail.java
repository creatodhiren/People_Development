package com.creatoweb.peopledevelopment.agent.fragment.payment.model;

import com.google.gson.annotations.SerializedName;

public class DisDetail {

    @SerializedName("disCode")
    private String disCode;

    @SerializedName("disDetailsId")
    private String disId;

    public String getDisId() {
        return disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
    }

    public String getDisCode() {
        return disCode;
    }

    public void setDisCode(String disCode) {
        this.disCode = disCode;
    }

}

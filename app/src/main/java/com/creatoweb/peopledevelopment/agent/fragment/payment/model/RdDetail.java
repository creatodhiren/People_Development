package com.creatoweb.peopledevelopment.agent.fragment.payment.model;

import com.google.gson.annotations.SerializedName;

public class RdDetail {

    @SerializedName("rdCode")
    private String rdCode;

    @SerializedName("rd_id")
    private String rdId;

    public String getRdId() {
        return rdId;
    }

    public void setRdId(String rdId) {
        this.rdId = rdId;
    }

    public String getRdCode() {
        return rdCode;
    }

    public void setRdCode(String rdCode) {
        this.rdCode = rdCode;
    }

}

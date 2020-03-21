package com.creatoweb.peopledevelopment.agent.fragment.payment.model;

import com.google.gson.annotations.SerializedName;

public class SavingDetailTemp {

    @SerializedName("savingCode")
    private String savingCode;

    @SerializedName("saving_id")
    private String savingId;

    public String getSavingId() {
        return savingId;
    }

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }

    public String getSavingCode() {
        return savingCode;
    }

    public void setSavingCode(String savingCode) {
        this.savingCode = savingCode;
    }

}

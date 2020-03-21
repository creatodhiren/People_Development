package com.creatoweb.peopledevelopment.agent.fragment.payment.model;

import com.google.gson.annotations.SerializedName;

public class Goldloan {

    @SerializedName("goldloanCode")
    private String goldloanCode;

    @SerializedName("goldloanId")
    private String goldloanId;

    public String getGoldloanId() {
        return goldloanId;
    }

    public void setGoldloanId(String goldloanId) {
        this.goldloanId = goldloanId;
    }

    public String getGoldloanCode() {
        return goldloanCode;
    }

    public void setGoldloanCode(String goldloanCode) {
        this.goldloanCode = goldloanCode;
    }

}

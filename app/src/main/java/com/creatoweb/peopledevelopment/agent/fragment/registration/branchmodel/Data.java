package com.creatoweb.peopledevelopment.agent.fragment.registration.branchmodel;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("touchbeeBranchId")
    private String touchbeeBranchId;

    @SerializedName("touchbeeBranchName")
    private String touchbeeBranchName;

    public String getTouchbeeBranchId() {
        return touchbeeBranchId;
    }

    public void setTouchbeeBranchId(String touchbeeBranchId) {
        this.touchbeeBranchId = touchbeeBranchId;
    }

    public String getTouchbeeBranchName() {
        return touchbeeBranchName;
    }

    public void setTouchbeeBranchName(String touchbeeBranchName) {
        this.touchbeeBranchName = touchbeeBranchName;
    }

}

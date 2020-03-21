package com.creatoweb.peopledevelopment.agent.fragment.registration.employeemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeModel {

    @SerializedName("Message")
    private String message;

    @SerializedName("savingCode")
    private String savingCode;

    @SerializedName("ddsCode")
    private String ddsCode;

    @SerializedName("fdCode")
    private String fdCode;

    @SerializedName("rdCode")
    private String rdCode;

    @SerializedName("data")
    private List<Data> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDdsCode() {
        return ddsCode;
    }

    public void setDdsCode(String ddsCode) {
        this.ddsCode = ddsCode;
    }

    public String getFdCode() {
        return fdCode;
    }

    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    public String getRdCode() {
        return rdCode;
    }

    public void setRdCode(String rdCode) {
        this.rdCode = rdCode;
    }

    public String getSavingCode() {
        return savingCode;
    }

    public void setSavingCode(String savingCode) {
        this.savingCode = savingCode;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
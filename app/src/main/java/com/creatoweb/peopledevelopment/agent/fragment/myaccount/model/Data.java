package com.creatoweb.peopledevelopment.agent.fragment.myaccount.model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("memberActId")
    private String memberActId;

    @SerializedName("member_name")
    private String memberName;

    @SerializedName("member_contact1")
    private String memberContact1;

    @SerializedName("disCode")
    private String ddscode;

    @SerializedName("available_balance")
    private String avalablebalance;

    @SerializedName("savingCode")
    private String savingCode;

    @SerializedName("rdCode")
    private String rdCode;

    @SerializedName("fdCode")
    private String fdCode;

    @SerializedName("clientname")
    private String clientName;

    @SerializedName("agent_name")
    private String agentName;

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

    public String getDdscode() {
        return ddscode;
    }

    public void setDdscode(String ddscode) {
        this.ddscode = ddscode;
    }

    public String getAvalablebalance() {
        return avalablebalance;
    }

    public void setAvalablebalance(String avalablebalance) {
        this.avalablebalance = avalablebalance;
    }

    public String getSavingCode() {
        return savingCode;
    }

    public void setSavingCode(String savingCode) {
        this.savingCode = savingCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMemberActId() {
        return memberActId;
    }

    public void setMemberActId(String memberActId) {
        this.memberActId = memberActId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberContact1() {
        return memberContact1;
    }

    public void setMemberContact1(String memberContact1) {
        this.memberContact1 = memberContact1;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

}

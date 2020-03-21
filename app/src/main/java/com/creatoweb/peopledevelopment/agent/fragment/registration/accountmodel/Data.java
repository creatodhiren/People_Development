package com.creatoweb.peopledevelopment.agent.fragment.registration.accountmodel;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("fdCode")
    private String fdCode;

    @SerializedName("fd_id")
    private String fdId;

    @SerializedName("rd_id")
    private String rdId;

    @SerializedName("saving_id")
    private String savingId;

    @SerializedName("disDetailsId")
    private String ddsId;

    @SerializedName("loanId")
    private String loanId;

    @SerializedName("loanCode")
    private String loanCode;

    @SerializedName("rdCode")
    private String rdCode;

    @SerializedName("savingCode")
    private String savingCode;

    @SerializedName("member_name")
    private String memberName;

    @SerializedName("member_id")
    private String member_id;

    @SerializedName("memberId")
    private String member1Id;

    @SerializedName("member_contact1")
    private String member_contact;

    @SerializedName("memberActid")
    private String member_actid;

    @SerializedName("disCode")
    private String ddsCode;

    @SerializedName("clientname")
    private String clientname;

    @SerializedName("amount")
    private String amount;

    @SerializedName("withdrawal_status")
    private String withdrawal_status;

    @SerializedName("available_balance")
    private String available_balance;

    public String getWithdrawal_status() {
        return withdrawal_status;
    }

    public void setWithdrawal_status(String withdrawal_status) {
        this.withdrawal_status = withdrawal_status;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getMember1Id() {
        return member1Id;
    }

    public void setMember1Id(String member1Id) {
        this.member1Id = member1Id;
    }

    public String getDdsId() {
        return ddsId;
    }

    public void setDdsId(String ddsId) {
        this.ddsId = ddsId;
    }

    public String getDdsCode() {
        return ddsCode;
    }

    public void setDdsCode(String ddsCode) {
        this.ddsCode = ddsCode;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getRdId() {
        return rdId;
    }

    public void setRdId(String rdId) {
        this.rdId = rdId;
    }

    public String getSavingId() {
        return savingId;
    }

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_contact() {
        return member_contact;
    }

    public void setMember_contact(String member_contact) {
        this.member_contact = member_contact;
    }

    public String getMember_actid() {
        return member_actid;
    }

    public void setMember_actid(String member_actid) {
        this.member_actid = member_actid;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSavingCode() {
        return savingCode;
    }

    public void setSavingCode(String savingCode) {
        this.savingCode = savingCode;
    }

    public String getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(String available_balance) {
        this.available_balance = available_balance;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }
}

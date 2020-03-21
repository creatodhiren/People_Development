package com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist.model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("disDetailsId")
    private String disDetailsId;

    @SerializedName("disCode")
    private String disCode;

    @SerializedName("clientname")
    private String clientName;

    @SerializedName("savingCode")
    private String savingCode;

    @SerializedName("paid_amount")
    private String paidAmount;

    @SerializedName("payment_date")
    private String payment_date;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("member_name")
    private String memberName;

    @SerializedName("member_contact1")
    private String memberContact1;

    @SerializedName("agentID")
    private String agentID;

    @SerializedName("installment")
    private String installment;

    @SerializedName("installmentDate")
    private String installmentDate;

    @SerializedName("available_balance")
    private String availableBalance;


    @SerializedName("transaction_type")
    private String transaction_type;

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSavingCode() {
        return savingCode;
    }

    public void setSavingCode(String savingCode) {
        this.savingCode = savingCode;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDisDetailsId() {
        return disDetailsId;
    }

    public void setDisDetailsId(String disDetailsId) {
        this.disDetailsId = disDetailsId;
    }

    public String getDisCode() {
        return disCode;
    }

    public void setDisCode(String disCode) {
        this.disCode = disCode;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getInstallmentDate() {
        return installmentDate;
    }

    public void setInstallmentDate(String installmentDate) {
        this.installmentDate = installmentDate;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

}

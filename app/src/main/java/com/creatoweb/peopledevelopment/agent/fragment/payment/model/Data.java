package com.creatoweb.peopledevelopment.agent.fragment.payment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("loandetail")
    private List<Loandetail> loandetail = null;

    @SerializedName("fd_detail")
    private List<FdDetail> fdDetail = null;

    @SerializedName("disDetails")
    private List<DisDetail> disDetails = null;

    @SerializedName("rd_detail")
    private List<RdDetail> rdDetail = null;

    @SerializedName("goldloan")
    private List<Goldloan> goldloan = null;

    @SerializedName("saving_detail_temp")
    private List<SavingDetailTemp> savingDetailTemp = null;

    public List<Loandetail> getLoandetail() {
        return loandetail;
    }

    public void setLoandetail(List<Loandetail> loandetail) {
        this.loandetail = loandetail;
    }

    public List<FdDetail> getFdDetail() {
        return fdDetail;
    }

    public void setFdDetail(List<FdDetail> fdDetail) {
        this.fdDetail = fdDetail;
    }

    public List<DisDetail> getDisDetails() {
        return disDetails;
    }

    public void setDisDetails(List<DisDetail> disDetails) {
        this.disDetails = disDetails;
    }

    public List<RdDetail> getRdDetail() {
        return rdDetail;
    }

    public void setRdDetail(List<RdDetail> rdDetail) {
        this.rdDetail = rdDetail;
    }

    public List<Goldloan> getGoldloan() {
        return goldloan;
    }

    public void setGoldloan(List<Goldloan> goldloan) {
        this.goldloan = goldloan;
    }

    public List<SavingDetailTemp> getSavingDetailTemp() {
        return savingDetailTemp;
    }

    public void setSavingDetailTemp(List<SavingDetailTemp> savingDetailTemp) {
        this.savingDetailTemp = savingDetailTemp;
    }

}

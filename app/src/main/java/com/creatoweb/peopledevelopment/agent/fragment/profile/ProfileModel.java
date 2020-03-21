package com.creatoweb.peopledevelopment.agent.fragment.profile;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("Message")
    private String message;

    @SerializedName("data")
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("fathersName")
        private String fathersName;
        @SerializedName("email")
        private String email;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("mobile2")
        private String mobile2;
        @SerializedName("BankName")
        private String bankName;
        @SerializedName("CancelCheckNo")
        private String cancelCheckNo;
        @SerializedName("address")
        private String address;
        @SerializedName("city")
        private String city;
        @SerializedName("street")
        private String street;
        @SerializedName("state")
        private String state;
        @SerializedName("age")
        private String age;
        @SerializedName("profilePic")
        private String profilePic;
        @SerializedName("signaturePic")
        private String signaturePic;
        @SerializedName("id_proof_pic")
        private String id_proof_pic;

        public String getId_proof_pic() {
            return id_proof_pic;
        }

        public void setId_proof_pic(String id_proof_pic) {
            this.id_proof_pic = id_proof_pic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFathersName() {
            return fathersName;
        }

        public void setFathersName(String fathersName) {
            this.fathersName = fathersName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile2() {
            return mobile2;
        }

        public void setMobile2(String mobile2) {
            this.mobile2 = mobile2;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCancelCheckNo() {
            return cancelCheckNo;
        }

        public void setCancelCheckNo(String cancelCheckNo) {
            this.cancelCheckNo = cancelCheckNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getSignaturePic() {
            return signaturePic;
        }

        public void setSignaturePic(String signaturePic) {
            this.signaturePic = signaturePic;
        }

    }
}

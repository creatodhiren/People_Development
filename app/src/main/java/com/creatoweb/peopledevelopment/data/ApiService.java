package com.creatoweb.peopledevelopment.data;


import com.creatoweb.peopledevelopment.agent.activity.login.model.LoginModel;
import com.creatoweb.peopledevelopment.agent.fragment.myaccount.model.MyAccountModel;
import com.creatoweb.peopledevelopment.agent.fragment.openaccount.MessageModel;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.AccountListModel;
import com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist.model.PaymentListModel;
import com.creatoweb.peopledevelopment.agent.fragment.profile.ProfileModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.RegistrationModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.accountmodel.AccountDetailModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.agentmodel.AgentModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.branchmodel.BranchModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.citymodel.CityModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.employeemodel.EmployeeModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.statemodel.StateModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    //--------------------- Login and Registration api -------------------
    @FormUrlEncoded
    @POST("agentLogin.php")
    Call<LoginModel> login(@Field("id") String id, @Field("password") String password);


    @FormUrlEncoded
    @POST("getProfile.php")
    Call<ProfileModel> getProfile(@Field("user_id") String userId);

    @POST("getState.php")
    Call<StateModel> getState();

    @POST("getBranch.php")
    Call<BranchModel> getBranch();

    //    @POST("getEmployeeList.php")
//    Call<EmployeeModel> getEmployee();
//
    @FormUrlEncoded
    @POST("addSAVING.php")
    Call<EmployeeModel> saveDetails(@Field("memberActId") String memberActId,
                                    @Field("member_id") String member_id,
                                    @Field("clientname") String clientname,
                                    @Field("agentID") String agentID,
                                    @Field("saving_entry_date") String saving_entry_date,
                                    @Field("saving_amount") String saving_amount,
                                    @Field("saving_intrest") String saving_intrest,
                                    @Field("member_contact1") String member_contact1);

    @POST("getAgentList.php")
    Call<AgentModel> getAgentList();

    @FormUrlEncoded
    @POST("getCity.php")
    Call<CityModel> getCity(@Field("state_id") String stateId);

    @FormUrlEncoded
    @POST("getAccount.php")
    Call<AccountDetailModel> getAccountDetail(@Field("type") String accountType);

    @FormUrlEncoded
    @POST("alldetails.php")
    Call<AccountListModel> getAccountList(@Field("memberActId") String memberActId);

    @FormUrlEncoded
    @POST("allinstallment.php")
    Call<MessageModel> sendPaymentJson(@Field("detail") String paymentjson);


    @FormUrlEncoded
    @POST("savingPayment.php")
    Call<MessageModel> sendSavingPayment(@Field("savingId") String savingId, @Field("type") String type, @Field("agent_id") String agentId, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("savingWithdrawal.php")
    Call<MessageModel> sendSavingWithdrawPayment(@Field("savingId") String savingId, @Field("agent_id") String agentId, @Field("amount") String amount, @Field("description") String description);


    @FormUrlEncoded
    @POST("RdPayment.php")
    Call<MessageModel> sendRdPayment(@Field("rd_id") String savingId, @Field("type") String type, @Field("agent_id") String agentId, @Field("amount") String amount, @Field("member_id") String memberid);

    @FormUrlEncoded
    @POST("loanPayment.php")
    Call<MessageModel> sendLoanPayment(@Field("loan_id") String savingId, @Field("type") String type, @Field("agent_id") String agentId, @Field("amount") String amount, @Field("member_id") String memberid);


    @FormUrlEncoded
    @POST("getAgentAccountList.php")
    Call<MyAccountModel> getAgentMyAccount(@Field("agent_id") String agentId, @Field("type") String type);


    @FormUrlEncoded
    @POST("getAgentPaymentList.php")
    Call<PaymentListModel> getPaymentList(@Field("agent_id") String agentId, @Field("type") String type, @Field("from_date") String fromdate, @Field("to_date") String toDate);

    @FormUrlEncoded
    @POST("DdsPayment.php")
    Call<MessageModel> sendDdsPayment(@Field("disDetailsId") String ddsId, @Field("type") String type, @Field("agent_id") String agentId, @Field("installment") String amount, @Field("description") String description, @Field("payment_type") String paymentType, @Field("member_id") String memberId, @Field("date") String date, @Field("penalty_amount") String paneltyAmount);

    @FormUrlEncoded
    @POST("register.php")
    Call<RegistrationModel> sendRegistration(@Field("member_name") String memberName,
                                             @Field("member_fathers_name") String memberFatherName,
                                             @Field("member_email") String memberEmail,
                                             @Field("member_contact1") String memberContact1,
                                             @Field("member_contact2") String memberContact2,
                                             @Field("member_address") String memberAddress,
                                             @Field("member_city") String memberCity,
                                             @Field("member_street") String memberStreet,
                                             @Field("member_state") String memberState,
                                             @Field("member_added_date") String memberAddedDate,
                                             @Field("member_profile_pic") String memberProfilePic,
                                             @Field("signature_pic") String SignaturePic,
                                             @Field("idproof_pic") String idProofPic,
                                             @Field("member_nominee") String memberNominee,
                                             @Field("member_age") String memberAge,
                                             @Field("agent_name") String agentName,
                                             @Field("touchbeeBranchId") String branchId,
                                             @Field("membershipfee") String memberShipFee,
                                             @Field("member_relation") String memberRelation,
                                             @Field("member_id_proof") String memberIdProof,
                                             @Field("dob") String dob,
                                             @Field("idproof_data") String idProofData,
                                             @Field("signature_data") String SignatureData,
                                             @Field("member_profile_data") String memberProfileData,
                                             @Field("document") String document_json);

    @FormUrlEncoded
    @POST("addDDS.php")
    Call<EmployeeModel> sendDDSDetail(@Field("memberActId") String memberActiId,
                                      @Field("member_name") String memberName,
                                      @Field("agent_name") String agentName,
                                      @Field("member_id") String memberId,
                                      @Field("amount") String depositeAmount,
                                      @Field("roi") String roi,
                                      @Field("month") String Days,
                                      @Field("opening_date") String openingDate,
                                      @Field("closing_date") String closingDate,
                                      @Field("closing_amount") String closingAmount,
                                      @Field("agentId") String employeeId,
                                      @Field("per_month_roi") String perMonthRoi,
                                      @Field("nominee") String nominee,
                                      @Field("nomineeAge") String nomineeAge,
                                      @Field("member_contact1") String mobileno
    );

    @FormUrlEncoded
    @POST("addMIS.php")
    Call<MessageModel> sendMISDetail(@Field("memberActId") String memberActiId,
                                     @Field("member_name") String memberName,
                                     @Field("agent_name") String agentName,
                                     @Field("member_id") String memberId,
                                     @Field("depositeAmount") String depositeAmount,
                                     @Field("roi") String roi,
                                     @Field("fdMonth") String Days,
                                     @Field("opening_date") String openingDate,
                                     @Field("closing_date") String closingDate,
                                     @Field("closing_amount") String closingAmount,
                                     @Field("employee_id") String employeeId,
                                     @Field("per_month_roi") String perMonthRoi,
                                     @Field("nominee") String nominee,
                                     @Field("nomineeAge") String nomineeAge
    );

    @FormUrlEncoded
    @POST("addRD.php")
    Call<EmployeeModel> sendRDDetail(@Field("memberActId") String memberActiId,
                                     @Field("member_name") String memberName,
                                     @Field("agent_name") String agentName,
                                     @Field("member_id") String memberId,
                                     @Field("depositeAmount") String depositeAmount,
                                     @Field("roi") String roi,
                                     @Field("fdMonth") String Days,
                                     @Field("opening_date") String openingDate,
                                     @Field("closing_date") String closingDate,
                                     @Field("closing_amount") String closingAmount,
                                     @Field("nominee") String nominee,
                                     @Field("nomineeAge") String nomineeAge,
                                     @Field("member_contact1") String mobile
    );

    @FormUrlEncoded
    @POST("addFD.php")
    Call<EmployeeModel> sendFdDetail(@Field("memberActId") String memberActiId,
                                     @Field("member_name") String memberName,
                                     @Field("agent_name") String agentName,
                                     @Field("member_id") String memberId,
                                     @Field("depositeAmount") String depositeAmount,
                                     @Field("member_contact1") String mobileno,
                                     @Field("roi") String roi,
                                     @Field("fdMonth") String Days,
                                     @Field("opening_date") String openingDate,
                                     @Field("closing_date") String closingDate,
                                     @Field("closing_amount") String closingAmount,
                                     @Field("nominee") String nominee,
                                     @Field("nomineeAge") String nomineeAge
    );
}
package com.creatoweb.peopledevelopment.agent.fragment.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.SplashActivity;
import com.creatoweb.peopledevelopment.agent.fragment.openaccount.MessageModel;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.AccountListModel;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.DisDetail;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.FdDetail;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.Goldloan;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.Loandetail;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.RdDetail;
import com.creatoweb.peopledevelopment.agent.fragment.payment.model.SavingDetailTemp;
import com.creatoweb.peopledevelopment.agent.fragment.registration.accountmodel.AccountDetailModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.accountmodel.Data;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.databinding.FragmentPaymentBinding;
import com.creatoweb.peopledevelopment.utils.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.*;


public class PaymentFragment extends Fragment {

    private Context context;
    private Activity activity;
    private FragmentPaymentBinding binding;
    private ObservableInt status = new ObservableInt();


    private List<Data> accountdetail_datalist = new ArrayList<>();
    private List<String> code_datalist = new ArrayList<>();
    private List<String> name_datalist = new ArrayList<>();
    private List<String> mobile_datalist = new ArrayList<>();
    private List<String> amount_datalist = new ArrayList<>();
    private List<String> memberid_datalist = new ArrayList<>();

    private List<SavingDetailTemp> saving_datalist = new ArrayList<>();
    private List<DisDetail> dds_datalist = new ArrayList<>();
    private List<Goldloan> goldloan_datalist = new ArrayList<>();
    private List<Loandetail> loan_datalist = new ArrayList<>();
    private List<RdDetail> rd_datalist = new ArrayList<>();
    private List<FdDetail> fd_datalist = new ArrayList<>();

    private JSONArray jsonPaymentArray = new JSONArray();

    private String memberId = "";

    private ProgressDialog progressDialog;

    private boolean checklist = false;

    public PaymentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("loading");

        getAccountDetail("4");

        binding.tvSeemore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("tag child count", "------------------------------------------" + binding.llAccountlist.getChildCount());
                if (checklist == false) {
                    checklist = true;
                    binding.tvSeemore.setText("See More");
                    for (int j = 0; j < binding.llAccountlist.getChildCount(); j++) {
                        if (j >= 2) {
                            Log.v("tag child count", "------------------------------------------");
                            Log.v("tag child count", "------------------------------------------" + j);
                            View view = binding.llAccountlist.getChildAt(j);
                            LinearLayout linearLayout = view.findViewById(R.id.ll_field);
                            linearLayout.setVisibility(GONE);
                        }
                    }
                } else {
                    if (checklist == true) {
                        checklist = false;
                        binding.tvSeemore.setText("See Less");
                        for (int j = 0; j < binding.llAccountlist.getChildCount(); j++) {
                            if (j >= 2) {
                                Log.v("tag child count", "------------------------------------------");
                                Log.v("tag child count", "------------------------------------------" + j);
                                View view = binding.llAccountlist.getChildAt(j);
                                LinearLayout linearLayout = view.findViewById(R.id.ll_field);
                                linearLayout.setVisibility(VISIBLE);
                            }
                        }
                    }
                }
            }
        });

        binding.autocompletetextId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                resetAll();

                int position = code_datalist.indexOf("" + arg0.getItemAtPosition(arg2));

                binding.autocompletetextName.setText(name_datalist.get(position));
                if (mobile_datalist.size() != 0) {
                    binding.autocompletetextMobileno.setText(mobile_datalist.get(position));
                }
                if (memberid_datalist.size() != 0) {
                    memberId = memberid_datalist.get(position);
                }
                binding.llAccountlist.removeAllViews();
                getAccountList(code_datalist.get(position));
                binding.cardPayment.setVisibility(VISIBLE);
                if (checklist == false) {
                    checklist = true;
                    binding.tvSeemore.setText("See More");
                }
            }
        });

        binding.autocompletetextName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                resetAll();

                int position = name_datalist.indexOf("" + arg0.getItemAtPosition(arg2));

                binding.autocompletetextId.setText(code_datalist.get(position));
                if (mobile_datalist.size() != 0) {
                    binding.autocompletetextMobileno.setText(mobile_datalist.get(position));
                }

                if (memberid_datalist.size() != 0) {
                    memberId = memberid_datalist.get(position);
                    Log.v("memberId", memberId);
                }
            }
        });


        binding.autocompletetextMobileno.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                resetAll();

                int position = mobile_datalist.indexOf("" + arg0.getItemAtPosition(arg2));

                binding.autocompletetextId.setText(code_datalist.get(position));
                binding.autocompletetextName.setText(name_datalist.get(position));

                if (memberid_datalist.size() != 0) {
                    memberId = memberid_datalist.get(position);
                    Log.v("tag", memberId);
                }
                binding.llAccountlist.removeAllViews();
                getAccountList(code_datalist.get(position));
                binding.cardPayment.setVisibility(VISIBLE);
                if (checklist == false) {
                    checklist = true;
                    binding.tvSeemore.setText("See More");
                }
            }
        });

        binding.btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonPaymentArray = new JSONArray();
                AppPref appPref = AppPref.getInstance(activity);
                String agentId = appPref.getString(AppPref.Key.ID, "");
                for (int i = 0; i < binding.llAccountlist.getChildCount(); i++) {

                    if (dds_datalist.size() > 0) {
                        for (int j = 0; j < dds_datalist.size(); j++) {
                            JSONObject jsonCash = new JSONObject();
                            EditText amount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_cash);
                            EditText paneltyamount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_panelty);
                            if (!amount.getText().toString().equals("")) {
                                try {
                                    jsonCash.put("installment", amount.getText().toString());
                                    jsonCash.put("penalty_amount", paneltyamount.getText().toString());
                                    jsonCash.put("code", dds_datalist.get(j).getDisId());
                                    jsonCash.put("memberActId", memberId);
                                    jsonCash.put("agent_id", agentId);
                                    jsonCash.put("type", "disDetails");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonPaymentArray.put(jsonCash);
                            }
                            i++;
                        }
                    }

                    if (loan_datalist.size() > 0) {
                        for (int j = 0; j < loan_datalist.size(); j++) {
                            JSONObject jsonCash = new JSONObject();
                            EditText amount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_cash);
                            EditText paneltyamount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_panelty);
                            if (!amount.getText().toString().equals("")) {
                                try {
                                    jsonCash.put("installment", amount.getText().toString());
                                    jsonCash.put("penalty_amount", paneltyamount.getText().toString());
                                    jsonCash.put("code", loan_datalist.get(j).getLoanId());
                                    jsonCash.put("memberActId", memberId);
                                    jsonCash.put("agent_id", agentId);
                                    jsonCash.put("type", "loandetail");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonPaymentArray.put(jsonCash);
                            }
                            i++;
                        }
                    }

                    if (goldloan_datalist.size() > 0) {
                        for (int j = 0; j < goldloan_datalist.size(); j++) {
                            JSONObject jsonCash = new JSONObject();
                            EditText amount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_cash);
                            EditText paneltyamount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_panelty);
                            if (!amount.getText().toString().equals("")) {
                                try {
                                    jsonCash.put("installment", amount.getText().toString());
                                    jsonCash.put("penalty_amount", paneltyamount.getText().toString());
                                    jsonCash.put("code", goldloan_datalist.get(j).getGoldloanId());
                                    jsonCash.put("memberActId", memberId);
                                    jsonCash.put("agent_id", agentId);
                                    jsonCash.put("type", "goldloan");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonPaymentArray.put(jsonCash);
                            }
                            i++;
                        }
                    }

                    if (rd_datalist.size() > 0) {
                        for (int j = 0; j < rd_datalist.size(); j++) {
                            JSONObject jsonCash = new JSONObject();
                            EditText amount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_cash);
                            EditText paneltyamount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_panelty);
                            if (!amount.getText().toString().equals("")) {
                                try {
                                    jsonCash.put("installment", amount.getText().toString());
                                    jsonCash.put("penalty_amount", paneltyamount.getText().toString());
                                    jsonCash.put("code", rd_datalist.get(j).getRdId());
                                    jsonCash.put("memberActId", memberId);
                                    jsonCash.put("agent_id", agentId);
                                    jsonCash.put("type", "rd_detail");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonPaymentArray.put(jsonCash);
                            }
                            i++;
                        }
                    }

                    if (fd_datalist.size() > 0) {
                        for (int j = 0; j < fd_datalist.size(); j++) {
                            JSONObject jsonCash = new JSONObject();
                            EditText amount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_cash);
                            EditText paneltyamount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_panelty);
                            if (!amount.getText().toString().equals("")) {
                                try {
                                    jsonCash.put("installment", amount.getText().toString());
                                    jsonCash.put("penalty_amount", paneltyamount.getText().toString());
                                    jsonCash.put("code", fd_datalist.get(j).getFdId());
                                    jsonCash.put("memberActId", memberId);
                                    jsonCash.put("agent_id", agentId);
                                    jsonCash.put("type", "fd_detail");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonPaymentArray.put(jsonCash);
                            }
                            i++;
                        }
                    }

                    if (saving_datalist.size() > 0) {
                        for (int j = 0; j < saving_datalist.size(); j++) {
                            JSONObject jsonCash = new JSONObject();
                            EditText amount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_cash);
                            EditText paneltyamount = binding.llAccountlist.getChildAt(i).findViewById(R.id.et_panelty);
                            if (!amount.getText().toString().equals("")) {
                                try {
                                    jsonCash.put("installment", amount.getText().toString());
                                    jsonCash.put("penalty_amount", paneltyamount.getText().toString());
                                    jsonCash.put("code", saving_datalist.get(j).getSavingId());
                                    jsonCash.put("memberActId", memberId);
                                    jsonCash.put("agent_id", agentId);
                                    jsonCash.put("type", "saving_detail_temp");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonPaymentArray.put(jsonCash);
                            }
                            i++;
                        }
                    }
                    Log.v("tag", jsonPaymentArray.toString());
                }
                sendPaymentJson(view);
            }
        });

        return binding.getRoot();
    }

    private void onAddField(String accountType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = Objects.requireNonNull(inflater).inflate(R.layout.view_field, null);
        TextView textView = rowView.findViewById(R.id.tv_accounttype);
        textView.setText(accountType);
        // Add the new row before the add field button.
        Log.v("tag child count", binding.llAccountlist.getChildCount() + "");
        binding.llAccountlist.addView(rowView, binding.llAccountlist.getChildCount());
    }

//    private void paymentJsonCreate() {
//        Log.v("tag", "" + binding.btnCash.isChecked());
//        Log.v("tag", "" + binding.btnCheque.isChecked());
//        Log.v("tag", "" + binding.btnNeft.isChecked());
//
//        if (binding.btnCash.isChecked() == true) {
//            if (!binding.etCash.getText().toString().equalsIgnoreCase("")) {
//                JSONObject jsonCash1 = new JSONObject();
//                try {
//                    jsonCash1.put("amount", binding.etCash.getText().toString());
//                    jsonCash1.put("image", "No Image");
//                    jsonCash1.put("image_data", "No Image");
//                    jsonCash1.put("description", binding.etDescription.getText().toString());
//                    jsonCash1.put("payment_type", "cash");
//                    if (accountTypeValue.equalsIgnoreCase("2") || accountTypeValue.equalsIgnoreCase("6")) {
//                        jsonCash1.put("penalty_amount", binding.etPenaltyamount.getText().toString());
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                jsonPaymentArray.put(jsonCash1);
//            }
//        }
//
//        if (binding.btnCheque.isChecked() == true) {
//            if (!binding.etCheque.getText().toString().equalsIgnoreCase("")) {
//                JSONObject jsonCash2 = new JSONObject();
//                try {
//                    jsonCash2.put("amount", binding.etCheque.getText().toString());
//                    jsonCash2.put("image", image1name);
//                    jsonCash2.put("image_data", image1Bitmap);
//                    jsonCash2.put("description", binding.etChequeno.getText().toString());
//                    jsonCash2.put("payment_type", "cheque");
//                    if (accountTypeValue.equalsIgnoreCase("2") || accountTypeValue.equalsIgnoreCase("6")) {
//                        jsonCash2.put("penalty_amount", "0");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                jsonPaymentArray.put(jsonCash2);
//            }
//        }
//
//        if (binding.btnNeft.isChecked() == true) {
//            if (!binding.etNeft.getText().toString().equalsIgnoreCase("")) {
//
//                JSONObject jsonCash3 = new JSONObject();
//                try {
//                    jsonCash3.put("amount", binding.etNeft.getText().toString());
//                    jsonCash3.put("image", image2name);
//                    jsonCash3.put("image_data", image2Bitmap);
//                    jsonCash3.put("description", binding.etNeftno.getText().toString());
//                    jsonCash3.put("payment_type", "neft");
//                    if (accountTypeValue.equalsIgnoreCase("2") || accountTypeValue.equalsIgnoreCase("6")) {
//                        jsonCash3.put("penalty_amount", "0");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                jsonPaymentArray.put(jsonCash3);
//            }
//
//        }
//    }

    private void resetAll() {
//        binding.llCash.setVisibility(View.VISIBLE);
//        binding.btnCash.setChecked(true);
//        binding.btnCheque.setChecked(false);
//        binding.btnNeft.setChecked(false);
//        binding.llCheque.setVisibility(View.GONE);
//        binding.llChequeImage.setVisibility(View.GONE);
//        binding.llNeft.setVisibility(View.GONE);
//        binding.llNeftImage.setVisibility(View.GONE);
//        binding.etCash.setText("");
//        binding.etCheque.setText("");
//        binding.etNeft.setText("");
//        binding.etDescription.setText("");
//        binding.etChequeno.setText("");
//        binding.etNeftno.setText("");
    }

    private void getAccountDetail(String accountType) {
        Call<AccountDetailModel> call = ApiClient.getApiClient().getAccountDetail(accountType);
        call.enqueue(new Callback<AccountDetailModel>() {
            @Override
            public void onResponse(Call<AccountDetailModel> call, Response<AccountDetailModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountDetailModel accountDetailModel = response.body();
                    if (accountDetailModel.getMessage().equalsIgnoreCase("success!")) {
                        accountdetail_datalist = accountDetailModel.getData();
                        name_datalist.clear();
                        memberid_datalist.clear();
                        mobile_datalist.clear();
                        if (accountType.equalsIgnoreCase("4")) {
                            for (int i = 0; i < accountdetail_datalist.size(); i++) {
                                memberid_datalist.add(accountdetail_datalist.get(i).getMember_id());
                                code_datalist.add(accountdetail_datalist.get(i).getMember_actid());
                                name_datalist.add(accountdetail_datalist.get(i).getMemberName());
                                mobile_datalist.add(accountdetail_datalist.get(i).getMember_contact());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, code_datalist);
                            binding.autocompletetextId.setThreshold(1);
                            binding.autocompletetextId.setAdapter(adapter);

                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, mobile_datalist);
                            binding.autocompletetextMobileno.setThreshold(1);
                            binding.autocompletetextMobileno.setAdapter(adapter1);
                        }
                    } else if (accountDetailModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<AccountDetailModel> call, Throwable t) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAccountList(String memberActId) {
        Call<AccountListModel> call = ApiClient.getApiClient().getAccountList(memberActId);
        call.enqueue(new Callback<AccountListModel>() {
            @Override
            public void onResponse(Call<AccountListModel> call, Response<AccountListModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountListModel accountDetailModel = response.body();

                    saving_datalist.clear();
                    dds_datalist.clear();
                    fd_datalist.clear();
                    goldloan_datalist.clear();
                    loan_datalist.clear();
                    rd_datalist.clear();

                    if (accountDetailModel.getMessage().equalsIgnoreCase("success!")) {
                        dds_datalist = accountDetailModel.getData().getDisDetails();
                        saving_datalist = accountDetailModel.getData().getSavingDetailTemp();
                        fd_datalist = accountDetailModel.getData().getFdDetail();
                        goldloan_datalist = accountDetailModel.getData().getGoldloan();
                        loan_datalist = accountDetailModel.getData().getLoandetail();
                        rd_datalist = accountDetailModel.getData().getRdDetail();


                        if (dds_datalist.size() > 0) {
                            for (int i = 0; i < dds_datalist.size(); i++) {
                                if (i == 0) {
                                    onAddField("DDS");
                                } else {
                                    onAddField("DDS" + i);
                                }
                            }
                        }

                        if (loan_datalist.size() > 0) {
                            for (int i = 0; i < loan_datalist.size(); i++) {
                                if (i == 0) {
                                    onAddField("Loan");
                                } else {
                                    onAddField("Loan" + i);
                                }
                            }
                        }

                        if (goldloan_datalist.size() > 0) {
                            for (int i = 0; i < goldloan_datalist.size(); i++) {
                                if (i == 0) {
                                    onAddField("Gold Loan");
                                } else {
                                    onAddField("Gold Loan" + i);
                                }
                            }
                        }

                        if (rd_datalist.size() > 0) {
                            for (int i = 0; i < rd_datalist.size(); i++) {
                                if (i == 0) {
                                    onAddField("RD");
                                } else {
                                    onAddField("RD" + i);
                                }
                            }
                        }

                        if (fd_datalist.size() > 0) {
                            for (int i = 0; i < fd_datalist.size(); i++) {
                                if (i == 0) {
                                    onAddField("FD");
                                } else {
                                    onAddField("FD" + i);
                                }
                            }
                        }

                        if (saving_datalist.size() > 0) {
                            for (int i = 0; i < saving_datalist.size(); i++) {
                                if (i == 0) {
                                    onAddField("Saving");
                                } else {
                                    onAddField("Saving" + i);
                                }
                            }
                        }

                        for (int j = 0; j < binding.llAccountlist.getChildCount(); j++) {
                            if (j >= 2) {
                                Log.v("tag child count", "------------------------------------------");
                                Log.v("tag child count", "------------------------------------------" + j);
                                View view = binding.llAccountlist.getChildAt(j);
                                LinearLayout linearLayout = view.findViewById(R.id.ll_field);
                                linearLayout.setVisibility(GONE);
                            }
                        }
                    } else if (accountDetailModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<AccountListModel> call, Throwable t) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendPaymentJson(View view) {
        progressDialog.show();
        Log.v("tag", "" + jsonPaymentArray);

        Call<MessageModel> call = ApiClient.getApiClient().sendPaymentJson("" + jsonPaymentArray);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                progressDialog.hide();
                if (response.isSuccessful() && response.body() != null) {
                    MessageModel messageModel = response.body();
                    if (messageModel.getMessage().equalsIgnoreCase("Success!")) {
                        Navigation.findNavController(view).navigate(R.id.dashboard);
                        Toast.makeText(context, "Data Save Sucessfully", Toast.LENGTH_SHORT).show();
                    } else if (messageModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "Data Not Save Sucessfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Data Not Save Sucessfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(context, "Api failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.activity = activity;
    }
}

package com.creatoweb.peopledevelopment.agent.fragment.openaccount;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;


import com.creatoweb.peopledevelopment.agent.fragment.registration.employeemodel.Data;
import com.creatoweb.peopledevelopment.databinding.FragmentLoanaccountBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoanFragment extends Fragment {

    private Context context;
    private Activity activity;
    private FragmentLoanaccountBinding binding;
    private ObservableInt status = new ObservableInt();

    public static final String TAG = "DDSFragment";

    ArrayList<String> hide_visibe_list = new ArrayList<>();
    List<Data> employee_datalist = new ArrayList<>();
    List<String> state_ArrayList = new ArrayList<>();
    List<String> branch_ArrayList = new ArrayList<>();
    List<String> employee_ArrayList = new ArrayList<>();
    List<String> agent_ArrayList = new ArrayList<>();

    String memberCity = "", memberState = "", memberBranch = "", memberAgent = "", memberEmployee = "";

    private ProgressDialog progressDialog;


    public LoanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoanaccountBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("loading");

        state_ArrayList.clear();
        branch_ArrayList.clear();
        employee_ArrayList.clear();
        agent_ArrayList.clear();




        hide_visibe_list.add("0");
        hide_visibe_list.add("0");
        hide_visibe_list.add("0");
        hide_visibe_list.add("0");
        hide_visibe_list.add("0");


        return binding.getRoot();
    }

//    private void sendRegistration() {
//
//        Log.v(TAG,""+memberCity);
//        Log.v(TAG,""+memberState);
//        Log.v(TAG,""+ profile_name);
//        Log.v(TAG,""+signature_name);
//        Log.v(TAG,""+idproof_name);
//        Log.v(TAG,""+memberAgent);
//        Log.v(TAG,""+memberBranch);
//        Call<RegistrationModel> call = ApiClient.getApiClient().sendRegistration(binding.etMembername.getText().toString(), binding.etLoginpassword.getText().toString(),
//                binding.etFathername.getText().toString(), binding.etMemberemail.getText().toString(), binding.etContact1.getText().toString(),
//                binding.etContact2.getText().toString(), binding.etBankname.getText().toString(), binding.etCancelcheckno.getText().toString(),
//                binding.etAddress.getText().toString(), memberCity, "abc", memberState, binding.etAdddate.getText().toString(),
//                "abc", profile_name, signature_name, idproof_name, binding.etNominee.getText().toString(),
//                binding.etAge.getText().toString(), memberAgent, memberBranch, binding.etMembershipfee.getText().toString(), binding.etRelations.getText().toString(),
//                "abc", binding.etDateofbirth.getText().toString(), idproof_data, signature_data, profile_data, ""+image_array);
//        call.enqueue(new Callback<RegistrationModel>() {
//            @Override
//            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
//
//                if (response.isSuccessful() && response.body() != null) {
//                    RegistrationModel registrationModel = response.body();
//                    if (registrationModel.getMessage().equalsIgnoreCase("success!")) {
//                        Boolean data = registrationModel.getData();
//
//                        if (data == true) {
//                            Toast.makeText(context, "Registration Sucessfully...", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else if (registrationModel.getMessage().equalsIgnoreCase("No Record Found!")) {
//                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegistrationModel> call, Throwable t) {
//                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.activity = activity;
    }
}

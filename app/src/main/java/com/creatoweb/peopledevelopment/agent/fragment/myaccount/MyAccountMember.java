package com.creatoweb.peopledevelopment.agent.fragment.myaccount;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.creatoweb.peopledevelopment.agent.fragment.myaccount.model.Data;
import com.creatoweb.peopledevelopment.agent.fragment.myaccount.model.MyAccountModel;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.data.Constants;
import com.creatoweb.peopledevelopment.databinding.FragmentMyaccountmemberBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountMember extends Fragment {

    public static final String TAG = "MyAccount";
    private Context context;
    private ObservableInt status = new ObservableInt(0);
    FragmentMyaccountmemberBinding binding;

    private String type = "";

    MyaccountAdapter myaccountAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyaccountmemberBinding.inflate(inflater, container, false);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        binding.setStatus(status);

        if (getArguments() != null) {
            type = getArguments().getString(Constants.TYPE);
        }

        sendSavingData();

        return binding.getRoot();
    }

    private void sendSavingData() {
        status.set(1);
        AppPref appPref = AppPref.getInstance(context);
        String agentId = appPref.getString(AppPref.Key.ID, "");

        Log.v("tag", agentId);

        Call<MyAccountModel> call = ApiClient.getApiClient().getAgentMyAccount(agentId, type);
        call.enqueue(new Callback<MyAccountModel>() {
            @Override
            public void onResponse(Call<MyAccountModel> call, Response<MyAccountModel> response) {
                status.set(0);
                if (response.isSuccessful() && response.body() != null) {
                    status.set(0);
                    MyAccountModel myAccountModel = response.body();
                    if (myAccountModel.getMessage().equalsIgnoreCase("Success!")) {
                        setAdapter(myAccountModel.getData());
                    } else if (myAccountModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        status.set(2);
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    status.set(2);
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyAccountModel> call, Throwable t) {
                status.set(2);
                Toast.makeText(context, "Api Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(List<Data> list) {
        myaccountAdapter = new MyaccountAdapter(context, list, type);
        binding.recyclerview.setAdapter(myaccountAdapter);

        myaccountAdapter.setClickListener(new MyaccountAdapter.ItemClickListener() {
            @Override
            public void onClick(int position, View view) {

            }
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

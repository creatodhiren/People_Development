package com.touchbee.android.agent.fragment.openaccount;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.touchbee.android.R;
import com.touchbee.android.agent.fragment.openaccount.registration.accountmodel.AccountDetailModel;
import com.touchbee.android.agent.fragment.openaccount.registration.accountmodel.Data;
import com.touchbee.android.agent.fragment.openaccount.registration.employeemodel.EmployeeModel;
import com.touchbee.android.data.ApiClient;
import com.touchbee.android.data.AppPref;
import com.touchbee.android.databinding.FragmentSavingBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingFragment extends Fragment {
    
    private Context context;
    private Activity activity;
    private FragmentSavingBinding binding;
    private ObservableInt status = new ObservableInt();
    String agentId="", memberId="";
    List<String> actid_datalist = new ArrayList<>();

    public static final String TAG = "SavingFragment";

    List<String> state_ArrayList = new ArrayList<>();
    List<String> employee_ArrayList = new ArrayList<>();
    List<String> agent_ArrayList = new ArrayList<>();

    List<Data> accountdetail_datalist = new ArrayList<>();
    List<String> id_datalist = new ArrayList<>();
    List<String> mobile_datalist = new ArrayList<>();
    List<String> name_datalist = new ArrayList<>();

    public SavingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSavingBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        binding.tvOpendate.setText(dateString);

        state_ArrayList.clear();
        employee_ArrayList.clear();
        agent_ArrayList.clear();

        getAccountDetail("4");

        binding.autocompletetextId.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                int position=actid_datalist.indexOf(""+arg0.getItemAtPosition(arg2));


                Log.v("Tag",""+arg0.getItemAtPosition(arg2));
                Log.v("Tag",""+actid_datalist.indexOf(""+arg0.getItemAtPosition(arg2)));
                Log.v("Tag",""+position);
                Log.v("Tag",""+name_datalist.get(position));


                binding.etName.setText(name_datalist.get(position));
                binding.autocompletetextMobileno.setText(mobile_datalist.get(position));
                memberId = id_datalist.get(position);
            }
        });

        binding.autocompletetextMobileno.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                int position=mobile_datalist.indexOf(""+arg0.getItemAtPosition(arg2));

                binding.etName.setText(name_datalist.get(position));
                binding.autocompletetextId.setText(actid_datalist.get(position));
                memberId = id_datalist.get(position);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (binding.autocompletetextId.getText().toString().equals(""))
                {
                    binding.autocompletetextId.setError("Enter Member Id");
                }else if (binding.etName.getText().toString().isEmpty())
                {
                    binding.etName.setError("Enter Name");
                }else if (binding.autocompletetextMobileno.getText().toString().isEmpty())
                {
                    binding.autocompletetextMobileno.setError("Enter Mobile");
                }else if (binding.etSavingamount.getText().toString().isEmpty())
                {
                    binding.etSavingamount.setError("Enter Amount");
                }else
                {
                    saveDetails(view);
                }
            }
        });

        return binding.getRoot();
    }

    private void saveDetails(View view)
    {
        AppPref appPref = AppPref.getInstance(activity);
        agentId = appPref.getString(AppPref.Key.ID,"");
        Log.e("..MemberId..","...autocompletetextId..."+binding.autocompletetextId.getText().toString()+"......");
        Log.e("..MemberId..","...etName..."+binding.etName.getText().toString()+"......");
        Log.e("..MemberId..","...agentId..."+agentId+"......");
        Log.e("..MemberId..","...etMobile..."+binding.autocompletetextMobileno.getText().toString()+"......");
        Log.e("..MemberId..","...tvOpendate..."+binding.tvOpendate.getText().toString()+"......");
        Log.e("..MemberId..","...etSavingamount..."+binding.etSavingamount.getText().toString()+"......");
        Log.e("..MemberId..","...etInterestrate..."+binding.etInterestrate.getText().toString()+"......");

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        Call<EmployeeModel> call = ApiClient.getApiClient().saveDetails(
                binding.autocompletetextId.getText().toString(),
                memberId,
                binding.etName.getText().toString(),
                agentId,
                binding.tvOpendate.getText().toString(),
                binding.etSavingamount.getText().toString(),
                binding.etInterestrate.getText().toString(),
                binding.autocompletetextMobileno.getText().toString());

        //,
        call.enqueue(new Callback<EmployeeModel>()
        {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response)
            {
                progressDialog.hide();
                Log.e("Saving Account",".......Response.....");
                if (response.isSuccessful())
                {
                    EmployeeModel savingResponse= response.body();
                    if(savingResponse.getMessage().equals("Success!"))
                    {
                        Toast.makeText(context, "Submited Sucessfully...", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.dashboardFragment);
                        Log.e("Saving Account",".......Successs.....");
                    }

                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t)
            {
                Log.e("Saving Account",".......Fail....."+t.toString()+".......");
                progressDialog.hide();
            }
        });
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
                        id_datalist.clear();
                        mobile_datalist.clear();
                        if (accountType.equalsIgnoreCase("4")) {
                            for (int i = 0; i < accountdetail_datalist.size(); i++) {
                                id_datalist.add(accountdetail_datalist.get(i).getMember_id());
                                actid_datalist.add(accountdetail_datalist.get(i).getMember_actid());
                                name_datalist.add(accountdetail_datalist.get(i).getMemberName());
                                mobile_datalist.add(accountdetail_datalist.get(i).getMember_contact());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, actid_datalist);
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

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.activity = activity;
    }
}

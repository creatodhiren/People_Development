package com.creatoweb.peopledevelopment.agent.fragment.openaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.creatoweb.peopledevelopment.databinding.FragmentDdsBinding;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DDSFragment extends Fragment {

    public static final String TAG = "DDSFragment";

    private Context context;
    private Activity activity;
    private FragmentDdsBinding binding;
    private ObservableInt status = new ObservableInt();

    private int mYear, mMonth, mDay;
    private String selectMemberId = "", selectedDay = "";

    List<Data> accountdetail_datalist = new ArrayList<>();
    List<String> actid_datalist = new ArrayList<>();
    List<String> id_datalist = new ArrayList<>();
    List<String> mobile_datalist = new ArrayList<>();
    List<String> name_datalist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDdsBinding.inflate(inflater, container, false);
        binding.setStatus(status);


        getAccountDetail("4");


        binding.spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Days = binding.spinnerDays.getSelectedItem().toString();

                String amount = binding.etAmount.getText().toString();

                int amt = 0;

                if (!amount.equalsIgnoreCase("")) {
                    amt = Integer.parseInt(amount);
                }

                calculateClosingAmount(amt, Days);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.autocompletetextId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int position = actid_datalist.indexOf("" + arg0.getItemAtPosition(arg2));

                binding.etName.setText(name_datalist.get(position));
                binding.autocompletetextMobile.setText(mobile_datalist.get(position));
                selectMemberId = id_datalist.get(position);
            }
        });

        binding.autocompletetextMobile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int position = mobile_datalist.indexOf("" + arg0.getItemAtPosition(arg2));

                binding.etName.setText(name_datalist.get(position));
                binding.autocompletetextId.setText(actid_datalist.get(position));
                selectMemberId = id_datalist.get(position);
            }
        });


        binding.tvOpendate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                binding.tvOpendate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                String oldDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                Log.v("Date before Addition: ", oldDate);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(sdf.parse(oldDate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (!selectedDay.equalsIgnoreCase("")) {

                                    c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(selectedDay));
                                    String newDate = sdf.format(c.getTime());
                                    binding.tvClosingdate.setText(newDate);
                                    Log.v("Date after Addition: ", newDate);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDDSDetail(view);
            }
        });

        return binding.getRoot();
    }

    private void calculateClosingAmount(int amt, String Days) {
        if (Days.equalsIgnoreCase("Choose Days")) {
            selectedDay = "";
            binding.tvRoi.setText("");
            binding.tvRoiperday.setText("");
            binding.tvClosingamount.setText("");
            binding.tvOpendate.setText("");
            binding.tvClosingdate.setText("");
        } else if (Days.equalsIgnoreCase("180 Days")) {
            selectedDay = "180";
            binding.tvRoi.setText("4.0");
            binding.tvRoiperday.setText("" + new DecimalFormat("##.#####").format(4.0 / 365));

            calculateAmount(amt, 180, 0.01096);

        } else if (Days.equalsIgnoreCase("365 Days")) {
            selectedDay = "365";
            binding.tvRoi.setText("4.25");
            binding.tvRoiperday.setText("" + new DecimalFormat("##.#####").format(4.25 / 365));

            calculateAmount(amt, 365, 0.01165);

        } else if (Days.equalsIgnoreCase("730 Days")) {
            selectedDay = "730";
            binding.tvRoi.setText("5.25");
            binding.tvRoiperday.setText("" + new DecimalFormat("##.###").format(5.25 / 365));

            calculateAmount(amt, 730, 0.01439);

        }

    }

    private void calculateAmount(int amt, int days, double oneDayRoi) {
        double total = amt * days;
        double TotalAmount = 0;
        double interest = 0;

        for (int j = 1; j <= days; j++) {
            TotalAmount = TotalAmount + amt;
            interest = interest + ((TotalAmount * oneDayRoi) / 100);
        }

        total = total + interest;
        binding.tvClosingamount.setText("" + total);

        binding.tvOpendate.setText("");
        binding.tvClosingdate.setText("");
    }

    private void sendDDSDetail(View view) {
        binding.progressbar.setVisibility(View.VISIBLE);


        AppPref appPref = AppPref.getInstance(activity);
        String agent_id = appPref.getString(AppPref.Key.ID, "");
        String agent_name = appPref.getString(AppPref.Key.NAME, "");
        String agent_email = appPref.getString(AppPref.Key.EMAIL, "");
        String agent_mobile = appPref.getString(AppPref.Key.MOBILE, "");

//        Log.v("tag", "" + binding.autocompletetextId.getText().toString() + "" +
//                binding.etName.getText().toString() + "" +
//                agent_name + "" + selectMemberId + "" + binding.etAmount.getText().toString() + "" +
//                binding.tvRoi.getText().toString() + "" +
//                selectedDay + "" + binding.tvOpendate.getText().toString() + "" +
//                binding.tvClosingdate.getText().toString() + "" +
//                binding.tvClosingamount.getText().toString() + "" +
//                agent_id + "" + binding.tvRoiperday.getText().toString() + "" +
//                binding.etNominee.getText().toString() + "" + binding.etNomineeage.getText().toString());


        Call<MessageModel> call = ApiClient.getApiClient().sendDDSDetail(binding.autocompletetextId.getText().toString(),
                binding.etName.getText().toString(),
                agent_name, selectMemberId, binding.etAmount.getText().toString(),
                binding.tvRoi.getText().toString(),
                selectedDay, binding.tvOpendate.getText().toString(),
                binding.tvClosingdate.getText().toString(),
                binding.tvClosingamount.getText().toString(),
                agent_id, binding.tvRoiperday.getText().toString(),
                binding.etNominee.getText().toString(), binding.etNomineeage.getText().toString(),binding.autocompletetextMobile.getText().toString());
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                binding.progressbar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    MessageModel messageModel = response.body();
                    if (messageModel.getMessage().equalsIgnoreCase("Success!")) {

                        Toast.makeText(context, "Submit Sucessfully...", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.dashboardFragment);

                    } else if (messageModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Api Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);

                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
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
                            binding.autocompletetextMobile.setThreshold(1);
                            binding.autocompletetextMobile.setAdapter(adapter1);
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

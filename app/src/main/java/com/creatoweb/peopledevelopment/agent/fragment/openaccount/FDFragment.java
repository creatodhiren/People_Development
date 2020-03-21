package com.creatoweb.peopledevelopment.agent.fragment.openaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.agent.fragment.registration.accountmodel.AccountDetailModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.accountmodel.Data;
import com.creatoweb.peopledevelopment.agent.fragment.registration.employeemodel.EmployeeModel;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.databinding.FragmentFdBinding;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FDFragment extends Fragment {

    private Context context;
    private Activity activity;
    private FragmentFdBinding binding;
    private ObservableInt status = new ObservableInt();

    public static final String TAG = "DDSFragment";

    List<String> employee_ArrayList = new ArrayList<>();
    List<Data> accountdetail_datalist = new ArrayList<>();
    List<String> actid_datalist = new ArrayList<>();
    List<String> id_datalist = new ArrayList<>();
    List<String> mobile_datalist = new ArrayList<>();
    List<String> name_datalist = new ArrayList<>();

    private int mYear, mMonth, mDay;

    String EmployeeId = "";

    String selectMemberId = "";

    String selectYear = "";

    private String image="";

    double amount;

    private ProgressDialog progressDialog;


    public FDFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFdBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("loading");

        employee_ArrayList.clear();

        getAccountDetail("4");

        binding.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String year = binding.spinnerYear.getSelectedItem().toString();

                String amount = binding.etAmount.getText().toString();

                int amt = 0;

                if (!amount.equalsIgnoreCase("")) {
                    amt = Integer.parseInt(amount);
                }

                calculateClosingAmount(amt, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    double a = Double.parseDouble(String.valueOf(charSequence));
                    amount = amount - a;
                    String roiString = binding.tvRoi.getText().toString();

                    if (!roiString.equalsIgnoreCase("") && !selectYear.equalsIgnoreCase("")) {
                        double roi = Double.parseDouble(roiString);
                        double year = Double.parseDouble(selectYear);
                        binding.tvClosingamount.setText("" + new DecimalFormat("##.###").format(amount + ((amount * roi * year) / 100)));

                    } else {
                        binding.tvClosingamount.setText("");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    double a = Double.parseDouble(String.valueOf(charSequence));
                    amount = amount + a;
                    String roiString = binding.tvRoi.getText().toString();

                    if (!roiString.equalsIgnoreCase("") && !selectYear.equalsIgnoreCase("")) {
                        double roi = Double.parseDouble(roiString);
                        double year = Double.parseDouble(selectYear);

                        binding.tvClosingamount.setText("" + new DecimalFormat("##.###").format(amount + ((amount * roi * year) / 100)));

                    } else {
                        binding.tvClosingamount.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

                int position = actid_datalist.indexOf("" + arg0.getItemAtPosition(arg2));

                binding.etName.setText(name_datalist.get(position));
                binding.autocompletetextId.setText(id_datalist.get(position));
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

                                if (!selectYear.equalsIgnoreCase("")) {
                                    c.add(Calendar.YEAR, Integer.parseInt(selectYear));
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
                sendFdDeatil(view);
            }
        });

        return binding.getRoot();
    }

    private void calculateClosingAmount(int amt, String Days) {
        if (Days.equalsIgnoreCase("Select Duration")) {
            selectYear = "";
            binding.tvRoipermonth.setText("");
            binding.tvClosingamount.setText("");
            binding.tvClosingamount.setText("");
            binding.tvOpendate.setText("");
            binding.tvClosingdate.setText("");
        } else if (Days.equalsIgnoreCase("1 Year")) {
            selectYear = "1";
            binding.tvRoi.setText("8");
            binding.tvRoipermonth.setText("" + new DecimalFormat("##.###").format(8 / 12));
            binding.tvClosingamount.setText("" + new DecimalFormat("##.###").format(amt + ((amt * 8 * 1) / 100)));

        } else if (Days.equalsIgnoreCase("2 Year")) {
            selectYear = "2";
            binding.tvRoi.setText("8.25");
            binding.tvRoipermonth.setText("" + new DecimalFormat("##.###").format(8.25 / 24));
            binding.tvClosingamount.setText("" + new DecimalFormat("##.###").format(amt + ((amt * 8 * 2) / 100)));

        } else if (Days.equalsIgnoreCase("3 Year")) {
            selectYear = "3";
            binding.tvRoi.setText("8.50");
            binding.tvRoipermonth.setText("" + new DecimalFormat("##.###").format(8.50 / 36));
            binding.tvClosingamount.setText("" + new DecimalFormat("##.###").format(amt + ((amt * 8 * 3) / 100)));

        } else if (Days.equalsIgnoreCase("4 Year")) {
            selectYear = "4";
            binding.tvRoi.setText("8.75");
            binding.tvRoipermonth.setText("" + new DecimalFormat("##.###").format(8.75 / 48));
            binding.tvClosingamount.setText("" + new DecimalFormat("##.###").format(amt + ((amt * 8 * 4) / 100)));

        } else if (Days.equalsIgnoreCase("5 Year")) {
            selectYear = "5";
            binding.tvRoi.setText("9.00");
            binding.tvRoipermonth.setText("" + new DecimalFormat("##.###").format(9.00 / 60));
            binding.tvClosingamount.setText("" + new DecimalFormat("##.###").format(amt + ((amt * 8 * 5) / 100)));

        }
    }

    private void sendFdDeatil(View view) {
        progressDialog.show();

        AppPref appPref = AppPref.getInstance(activity);
        String agent_id = appPref.getString(AppPref.Key.ID, "");
        String agent_name = appPref.getString(AppPref.Key.NAME, "");
        String agent_email = appPref.getString(AppPref.Key.EMAIL, "");
        String agent_mobile = appPref.getString(AppPref.Key.MOBILE, "");

        Log.v("tag", "" + binding.autocompletetextId.getText().toString() + "" +
                binding.etName.getText().toString() + "" +
                agent_name + "" + selectMemberId + "" + binding.etAmount.getText().toString() + "" +
                binding.tvRoi.getText().toString() + "" +
                selectYear + "" + binding.tvOpendate.getText().toString() + "" +
                binding.tvClosingdate.getText().toString() + "" +
                binding.tvClosingamount.getText().toString() + "" +
                EmployeeId + "" + binding.tvRoipermonth.getText().toString() + "" +
                binding.etNominee.getText().toString() + "" + binding.etNomineeage.getText().toString());


        Call<EmployeeModel> call = ApiClient.getApiClient().sendFdDetail(binding.autocompletetextId.getText().toString(),
                binding.etName.getText().toString(),
                agent_id, selectMemberId, binding.etAmount.getText().toString(),
                binding.autocompletetextMobile.getText().toString(),
                binding.tvRoi.getText().toString(),
                selectYear, binding.tvOpendate.getText().toString(),
                binding.tvClosingdate.getText().toString(),
                binding.tvClosingamount.getText().toString(),
                binding.etNominee.getText().toString(), binding.etNomineeage.getText().toString());
        call.enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                progressDialog.hide();
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeModel fdModel = response.body();
                    if (fdModel.getMessage().equalsIgnoreCase("Success!")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", binding.etName.getText().toString());
                        bundle.putString("memberActId", binding.autocompletetextId.getText().toString());
                        bundle.putString("memberAccountId", fdModel.getFdCode());
                        bundle.putString("accounttype", "FD (Fixed Deposit)");
                        bundle.putString("amount", "Rs "+binding.etAmount.getText().toString());
                        bundle.putString("percent", binding.tvRoi.getText().toString()+" % .");
                        bundle.putString("image", image);
                        Navigation.findNavController(view).navigate(R.id.pdfFragment,bundle);
                        Toast.makeText(context, "Submit Sucessfully...", Toast.LENGTH_SHORT).show();
                    } else if (fdModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Api Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                progressDialog.hide();
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

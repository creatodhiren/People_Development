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
import com.creatoweb.peopledevelopment.databinding.FragmentRdBinding;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RDFragment extends Fragment {

    private Context context;
    private Activity activity;
    private FragmentRdBinding binding;
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

    String selectedDay = "";

    int month;
    double roi;
    double amount;

    String memberEmployee = "";

    private String image="";

    private ProgressDialog progressDialog;


    public RDFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRdBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("loading");

        employee_ArrayList.clear();

        getAccountDetail("4");

        binding.spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Days = binding.spinnerDays.getSelectedItem().toString();

                if (Days.equalsIgnoreCase("Select Duration")) {
                    selectedDay = "";
                    binding.tvRoi.setText("");
                    binding.tvRoiperday.setText("");
                    binding.tvOpendate.setText("");
                    binding.tvClosingdate.setText("");
                } else if (Days.equalsIgnoreCase("12 Months")) {
                    selectedDay = "12";
                    binding.tvRoi.setText("8.00");
                    binding.tvRoiperday.setText("" + new DecimalFormat("##.###").format(8.0 / (12*1)));

                    double roi = Double.parseDouble(binding.tvRoi.getText().toString());
                    double months = Double.parseDouble(selectedDay);
                    double amount= Double.parseDouble(binding.etAmount.getText().toString());
                    double Amount=amount*months;
                    double total = amount * ((months * (months + 1)) / (2 * 12)) * (roi / 100);
                    double totalAmount=Amount+total;
                    binding.tvClosingamount.setText("" +totalAmount);

                    binding.tvOpendate.setText("");
                    binding.tvClosingdate.setText("");

                } else if (Days.equalsIgnoreCase("24 Months")) {
                    selectedDay = "24";
                    binding.tvRoi.setText("8.25");
                    binding.tvRoiperday.setText("" + new DecimalFormat("##.###").format(8.25 / (12*2)));

                    double roi = Double.parseDouble(binding.tvRoi.getText().toString());
                    double months = Double.parseDouble(selectedDay);
                    double amount= Double.parseDouble(binding.etAmount.getText().toString());
                    double Amount=amount*months;
                    double total = amount * ((months * (months + 1)) / (2 * 12)) * (roi / 100);
                    double totalAmount=Amount+total;
                    binding.tvClosingamount.setText("" +totalAmount);

                    binding.tvOpendate.setText("");
                    binding.tvClosingdate.setText("");

                } else if (Days.equalsIgnoreCase("36 Months")) {
                    selectedDay = "36";
                    binding.tvRoi.setText("8.50");
                    binding.tvRoiperday.setText("" + new DecimalFormat("##.###").format(8.50 / (12*3)));

                    double roi = Double.parseDouble(binding.tvRoi.getText().toString());
                    double months = Double.parseDouble(selectedDay);
                    double amount= Double.parseDouble(binding.etAmount.getText().toString());
                    double Amount=amount*months;
                    double total = amount * ((months * (months + 1)) / (2 * 12)) * (roi / 100);
                    double totalAmount=Amount+total;
                    binding.tvClosingamount.setText("" +totalAmount);



                    binding.tvOpendate.setText("");
                    binding.tvClosingdate.setText("");
                } else if (Days.equalsIgnoreCase("48 Months")) {
                    selectedDay = "48";
                    binding.tvRoi.setText("8.75");
                    binding.tvRoiperday.setText("" + new DecimalFormat("##.###").format(8.75 / (12*4)));

                    double roi = Double.parseDouble(binding.tvRoi.getText().toString());
                    double months = Double.parseDouble(selectedDay);
                    double amount= Double.parseDouble(binding.etAmount.getText().toString());
                    double Amount=amount*months;
                    double total = amount * ((months * (months + 1)) / (2 * 12)) * (roi / 100);
                    double totalAmount=Amount+total;
                    binding.tvClosingamount.setText("" +totalAmount);

                    binding.tvOpendate.setText("");
                    binding.tvClosingdate.setText("");
                } else if (Days.equalsIgnoreCase("60 Months")) {
                    selectedDay = "60";
                    binding.tvRoi.setText("9.00");
                    binding.tvRoiperday.setText("" + new DecimalFormat("##.###").format(9.00 / (12*5)));

                    double roi = Double.parseDouble(binding.tvRoi.getText().toString());
                    double months = Double.parseDouble(selectedDay);
                    double amount= Double.parseDouble(binding.etAmount.getText().toString());
                    double Amount=amount*months;
                    double total = amount * ((months * (months + 1)) / (2 * 12)) * (roi / 100);
                    double totalAmount=Amount+total;
                    binding.tvClosingamount.setText("" +totalAmount);

                    binding.tvOpendate.setText("");
                    binding.tvClosingdate.setText("");
                }
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

                    if (!binding.tvRoi.getText().toString().equalsIgnoreCase("") && !selectedDay.equalsIgnoreCase("")) {

                        double roi = Double.parseDouble(binding.tvRoi.getText().toString());
                        double months = Double.parseDouble(selectedDay);

                        double Amount=amount*months;

                        double total = amount * ((months * (months + 1)) / (2 * 12)) * (roi / 100);



                        Log.v("tag amount",""+amount);
                        Log.v("tag Amount",""+Amount);
                        Log.v("tag months",""+months);
                        Log.v("tag total",""+total);
                        Log.v("tag roi",""+roi);

                        double totalAmount=Amount+total;


                        binding.tvClosingamount.setText("" +totalAmount);
                    }

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    double a = Double.parseDouble(String.valueOf(charSequence));
                    amount = amount + a;

                    if (!binding.tvRoi.getText().toString().equalsIgnoreCase("") && !selectedDay.equalsIgnoreCase("")) {

                        double roi = Double.parseDouble(binding.tvRoi.getText().toString());
                        double months = Double.parseDouble(selectedDay);

                        double Amount=amount*months;

                        double total = amount * ((months * (months + 1)) / (2 * 12)) * (roi / 100);

                        Log.v("tag amount",""+amount);
                        Log.v("tag Amount",""+Amount);
                        Log.v("tag months",""+months);
                        Log.v("tag total",""+total);
                        Log.v("tag roi",""+roi);

                        double totalAmount=Amount+total;

                        binding.tvClosingamount.setText(""+totalAmount);
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

                                if (!selectedDay.equalsIgnoreCase("")) {
                                    c.add(Calendar.MONTH, Integer.parseInt(selectedDay));
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
                sendRdDetail(view);
            }
        });

        return binding.getRoot();
    }

    private void sendRdDetail(View view) {
        progressDialog.show();

        AppPref appPref = AppPref.getInstance(activity);
        String agent_id = appPref.getString(AppPref.Key.ID,"");
        String agent_name = appPref.getString(AppPref.Key.NAME,"");
        String agent_email = appPref.getString(AppPref.Key.EMAIL,"");
        String agent_mobile = appPref.getString(AppPref.Key.MOBILE,"");

        Log.v("tag",""+binding.autocompletetextId.getText().toString()+""+
                binding.etName.getText().toString()+""+
                agent_name+""+ selectMemberId+""+ binding.etAmount.getText().toString()+""+
                binding.tvRoi.getText().toString()+""+
                selectedDay+""+ binding.tvOpendate.getText().toString()+""+
                binding.tvClosingdate.getText().toString()+""+
                binding.tvClosingamount.getText().toString()+""+
                EmployeeId+""+ binding.tvRoiperday.getText().toString()+""+
                binding.tvNominee.getText().toString()+""+ binding.tvNomineeage.getText().toString());


        Call<EmployeeModel> call = ApiClient.getApiClient().sendRDDetail(binding.autocompletetextId.getText().toString(),
                binding.etName.getText().toString(),
                agent_id, selectMemberId, binding.etAmount.getText().toString(),
                binding.tvRoi.getText().toString(),
                selectedDay, binding.tvOpendate.getText().toString(),
                binding.tvClosingdate.getText().toString(),
                binding.tvClosingamount.getText().toString(),
                binding.tvNominee.getText().toString(), binding.tvNomineeage.getText().toString(),binding.autocompletetextMobile.getText().toString());
        call.enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                progressDialog.hide();
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeModel rdModel = response.body();
                    if (rdModel.getMessage().equalsIgnoreCase("Success!")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", binding.etName.getText().toString());
                        bundle.putString("memberActId", binding.autocompletetextId.getText().toString());
                        bundle.putString("memberAccountId", rdModel.getRdCode());
                        bundle.putString("accounttype", "RD (Recurring Deposit)");
                        bundle.putString("amount", "Rs. "+binding.etAmount.getText().toString());
                        bundle.putString("percent", binding.tvRoi.getText().toString()+" % .");
                        bundle.putString("image", image);
                        Navigation.findNavController(view).navigate(R.id.pdfFragment,bundle);
                        Toast.makeText(context, "Submit Sucessfully...", Toast.LENGTH_SHORT).show();
                    } else if (rdModel.getMessage().equalsIgnoreCase("No Record Found!")) {
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

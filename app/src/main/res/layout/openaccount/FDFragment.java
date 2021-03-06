package com.touchbee.android.agent.fragment.openaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import com.touchbee.android.agent.fragment.openaccount.registration.accountmodel.AccountDetailModel;
import com.touchbee.android.agent.fragment.openaccount.registration.employeemodel.Data;
import com.touchbee.android.agent.fragment.openaccount.registration.employeemodel.EmployeeModel;
import com.touchbee.android.data.ApiClient;
import com.touchbee.android.data.AppPref;
import com.touchbee.android.databinding.FragmentFdBinding;

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

    List<Data> employee_datalist = new ArrayList<>();
    List<String> employee_ArrayList = new ArrayList<>();
    List<com.touchbee.android.agent.fragment.openaccount.registration.accountmodel.Data> accountdetail_datalist = new ArrayList<>();
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


        employee_ArrayList.clear();

        getEmployeeName();
        getAccountDetail("4");

        binding.spinnerEmployeename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EmployeeId = "";
                if (employee_datalist.size() != 0) {
                    EmployeeId = employee_datalist.get(i).getEmployeeId();
                    Log.v("Tag", EmployeeId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        binding.etFdformonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    int a = Integer.parseInt(String.valueOf(charSequence));
                    month = month - a;
                    String roiString = binding.etRoi.getText().toString();
                    String amt = binding.etAmount.getText().toString();

                    if (!roiString.equalsIgnoreCase("") && !amt.equalsIgnoreCase("")) {
                        double roi = Double.parseDouble(roiString);
                        double amount = Double.parseDouble(amt);

                        binding.tvRoipermonth.setText("" +new DecimalFormat("##.###").format(roi / month) );
                        binding.tvClosingamount.setText("" +new DecimalFormat("##.###").format(amount + (((roi / 12) * month) * 100)));

                    } else {
                        binding.tvRoipermonth.setText("");
                        binding.tvClosingamount.setText("");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    int a = Integer.parseInt(String.valueOf(charSequence));
                    month = month + a;
                    String roiString = binding.etRoi.getText().toString();
                    String amt = binding.etAmount.getText().toString();

                    if (!roiString.equalsIgnoreCase("") && !amt.equalsIgnoreCase("")) {
                        double roi = Double.parseDouble(roiString);
                        double amount = Double.parseDouble(amt);

                        binding.tvRoipermonth.setText("" +new DecimalFormat("##.###").format(roi / month) );
                        binding.tvClosingamount.setText("" +new DecimalFormat("##.###").format(amount + (((roi / 12) * month) * 100)));

                    } else {
                        binding.tvRoipermonth.setText("");
                        binding.tvClosingamount.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    double a = Double.parseDouble(String.valueOf(charSequence));
                    amount = amount - a;
                    String roiString = binding.etRoi.getText().toString();
                    String monthStr = binding.etFdformonth.getText().toString();

                    if (!roiString.equalsIgnoreCase("") && !monthStr.equalsIgnoreCase("")) {
                        double roi = Double.parseDouble(roiString);
                        double month = Double.parseDouble(monthStr);
                        binding.tvRoipermonth.setText("" + new DecimalFormat("##.###").format(roi / month) );
                        binding.tvClosingamount.setText("" +new DecimalFormat("##.###").format(amount + (((roi / 12) * month) * 100)));

                    } else {
                        binding.tvRoipermonth.setText("");
                        binding.tvClosingamount.setText("");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    double a = Double.parseDouble(String.valueOf(charSequence));
                    amount = amount + a;
                    String roiString = binding.etRoi.getText().toString();
                    String monthStr = binding.etFdformonth.getText().toString();

                    if (!roiString.equalsIgnoreCase("") && !monthStr.equalsIgnoreCase("")) {
                        double roi = Double.parseDouble(roiString);
                        double month = Double.parseDouble(monthStr);
                        binding.tvRoipermonth.setText("" +new DecimalFormat("##.###").format(roi / month) );
                        binding.tvClosingamount.setText("" +new DecimalFormat("##.###").format(amount + (((roi / 12) * month) * 100)));

                    } else {
                        binding.tvRoipermonth.setText("");
                        binding.tvClosingamount.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    double a = Double.parseDouble(String.valueOf(charSequence));
                    roi = roi - a;
                    String amt = binding.etAmount.getText().toString();
                    String monthStr = binding.etFdformonth.getText().toString();
                    if (!amt.equalsIgnoreCase("") && !monthStr.equalsIgnoreCase("")) {
                        double month = Double.parseDouble(monthStr);
                        double amount = Double.parseDouble(amt);

                        binding.tvRoipermonth.setText("" +new DecimalFormat("##.###").format(roi / month) );
                        binding.tvClosingamount.setText("" +new DecimalFormat("##.###").format(amount + (((roi / 12) * month) * 100)));
                    } else {
                        binding.tvRoipermonth.setText("");
                        binding.tvClosingamount.setText("");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).equalsIgnoreCase("")) {
                    double a = Double.parseDouble(String.valueOf(charSequence));
                    roi = roi + a;
                    String amt = binding.etAmount.getText().toString();
                    String monthStr = binding.etFdformonth.getText().toString();
                    if (!amt.equalsIgnoreCase("") && !monthStr.equalsIgnoreCase("")) {
                        double month = Double.parseDouble(monthStr);
                        double amount = Double.parseDouble(amt);

                        binding.tvRoipermonth.setText("" +new DecimalFormat("##.###").format(roi / month) );
                        binding.tvClosingamount.setText("" +new DecimalFormat("##.###").format(amount + (((roi / 12) * month) * 100)));

                    } else {
                        binding.tvRoipermonth.setText("");
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

                int position=actid_datalist.indexOf(""+arg0.getItemAtPosition(arg2));

                binding.etName.setText(name_datalist.get(position));
                binding.autocompletetextMobile.setText(mobile_datalist.get(position));
                selectMemberId = id_datalist.get(position);
            }
        });

        binding.autocompletetextMobile.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int position=actid_datalist.indexOf(""+arg0.getItemAtPosition(arg2));

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

                                selectedDay=binding.etFdformonth.getText().toString();
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
                sendFdDeatil();
            }
        });

        return binding.getRoot();
    }

    private void getEmployeeName() {
        Call<EmployeeModel> call = ApiClient.getApiClient().getEmployee();
        call.enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    EmployeeModel stateModel = response.body();
                    if (stateModel.getMessage().equalsIgnoreCase("success!")) {
                        employee_datalist = stateModel.getData();

                        employee_ArrayList.clear();

                        for (int i = 0; i < employee_datalist.size(); i++) {
                            employee_ArrayList.add(employee_datalist.get(i).getEmployeeName() + " ( " + employee_datalist.get(i).getEmployeeRoleName() + " ) ");
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, employee_ArrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerEmployeename.setAdapter(adapter);
                        EmployeeId = employee_datalist.get(1).getEmployeeId();
                    } else if (stateModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendFdDeatil() {

        AppPref appPref = AppPref.getInstance(activity);
        String agent_id = appPref.getString(AppPref.Key.ID, "");
        String agent_name = appPref.getString(AppPref.Key.NAME, "");
        String agent_email = appPref.getString(AppPref.Key.EMAIL, "");
        String agent_mobile = appPref.getString(AppPref.Key.MOBILE, "");

        Log.v("tag", "" + binding.autocompletetextId.getText().toString() + "" +
                binding.etName.getText().toString() + "" +
                agent_name + "" + selectMemberId + "" + binding.etAmount.getText().toString() + "" +
                binding.etRoi.getText().toString() + "" +
                selectedDay + "" + binding.tvOpendate.getText().toString() + "" +
                binding.tvClosingdate.getText().toString() + "" +
                binding.tvClosingamount.getText().toString() + "" +
                EmployeeId + "" + binding.tvRoipermonth.getText().toString() + "" +
                binding.etNominee.getText().toString() + "" + binding.etNomineeage.getText().toString());


        Call<MessageModel> call = ApiClient.getApiClient().sendFdDetail(binding.autocompletetextId.getText().toString(),
                binding.etName.getText().toString(),
                agent_name, selectMemberId, binding.etAmount.getText().toString(),
                binding.etRoi.getText().toString(),
                selectedDay, binding.tvOpendate.getText().toString(),
                binding.tvClosingdate.getText().toString(),
                binding.tvClosingamount.getText().toString(),
                EmployeeId, binding.tvRoipermonth.getText().toString(),
                binding.etNominee.getText().toString(), binding.etNomineeage.getText().toString(),binding.etNomineerelation.getText().toString());
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    MessageModel messageModel = response.body();
                    if (messageModel.getMessage().equalsIgnoreCase("Success!")) {

                        Toast.makeText(context, "Submit Sucessfully...", Toast.LENGTH_SHORT).show();

                    } else if (messageModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Api Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
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

package com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.creatoweb.peopledevelopment.agent.fragment.myaccount.model.MyAccountModel;
import com.creatoweb.peopledevelopment.agent.fragment.paymentlist.PaymentListFragment;
import com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist.model.Data;
import com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist.model.PaymentListModel;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.data.Constants;
import com.creatoweb.peopledevelopment.databinding.FragmentPaymenttypelistBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentTypeListFragment extends Fragment {

    private Context context;
    private FragmentPaymenttypelistBinding binding;
    private ObservableInt status = new ObservableInt(0);
    private PaymentTypeListAdapter paymentTypeListAdapter;

    private int mYear, mMonth, mDay;

    private String datefromstr = "", datetostr = "";

    private String type = "";
    private double totaldds = 0.0;
    private double totalsaving = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymenttypelistBinding.inflate(inflater, container, false);
        binding.setStatus(status);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));

        if (getArguments() != null) {
            type = getArguments().getString(Constants.TYPE);
        }

        binding.llSearchfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int milliSecond = 518400000;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                totalsaving = 0.0;
                                totaldds = 0.0;

                                binding.tvSearchfrom.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                String oldDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                datefromstr = oldDate;
                                Log.v("Date before Addition: ", oldDate);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(sdf.parse(oldDate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                try {

                                    String[] sourceSplit= datefromstr.split("-");

                                    int anno= Integer.parseInt(sourceSplit[2]);

                                    if (datetostr.equalsIgnoreCase("")) {
                                        Toast.makeText(context, "Please Select To Date", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String[] sourceSplit1= datetostr.split("-");

                                        int anno1= Integer.parseInt(sourceSplit1[2]);
                                        if (binding.tvSearchto.getText().toString().equalsIgnoreCase("")) {
                                            Toast.makeText(context, "Please Select To Date", Toast.LENGTH_SHORT).show();
                                        } else if (anno>anno1) {
                                            Toast.makeText(context, "Please Select Less Than Date", Toast.LENGTH_SHORT).show();
                                        } else {
                                            getPaymentList();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - milliSecond);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        binding.llSearchtodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int milliSecond = 518400000;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                totalsaving = 0.0;
                                totaldds = 0.0;

//                                binding.ivSearchto.setVisibility(View.GONE);
                                binding.tvSearchto.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                String oldDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                datetostr = oldDate;
                                getPaymentList();

                                Log.v("Date before Addition: ", oldDate);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(sdf.parse(oldDate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                try {

                                    String[] sourceSplit= datetostr.split("-");

                                    int anno= Integer.parseInt(sourceSplit[2]);

                                    if (datefromstr.equalsIgnoreCase("")) {
                                        Toast.makeText(context, "Please Select To Date", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String[] sourceSplit1= datefromstr.split("-");

                                        int anno1= Integer.parseInt(sourceSplit1[2]);
                                        if (binding.tvSearchto.getText().toString().equalsIgnoreCase("")) {
                                            Toast.makeText(context, "Please Select To Date", Toast.LENGTH_SHORT).show();
                                        } else if (anno<anno1) {
                                            Toast.makeText(context, "Please Select GreaterThan Than Date", Toast.LENGTH_SHORT).show();
                                        } else {
                                            getPaymentList();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - milliSecond);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        Date d = new Date();
        CharSequence s  = DateFormat.format("yyyy-MM-dd", d.getTime()-518400000);
        CharSequence s1  = DateFormat.format("yyyy-MM-dd", d.getTime());

        datefromstr=s.toString();
        datetostr=s1.toString();

        getPaymentList();

        return binding.getRoot();
    }

    private void getPaymentList() {
        status.set(1);
        binding.tvTotal.setText("₹ 0.0");
        AppPref appPref = AppPref.getInstance(context);
        String agentId = appPref.getString(AppPref.Key.ID, "");


        Log.v("tag", agentId);
        Log.v("tag", datefromstr);
        Log.v("tag", datetostr);
        Log.v("tag", type);

        Call<PaymentListModel> call = ApiClient.getApiClient().getPaymentList(agentId, type, datefromstr, datetostr);
        call.enqueue(new Callback<PaymentListModel>() {
            @Override
            public void onResponse(Call<PaymentListModel> call, Response<PaymentListModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    status.set(0);
                    PaymentListModel paymentListModel = response.body();
                    if (paymentListModel.getMessage().equalsIgnoreCase("Success!")) {
                        setAdapter(paymentListModel.getData());

                        for (int i = 0; i < paymentListModel.getData().size(); i++) {
                            if (type.equalsIgnoreCase("DDS")) {
                                totaldds = totaldds + Double.parseDouble(paymentListModel.getData().get(i).getInstallment());
                                binding.tvTotal.setText("₹ " + totaldds);
                            } else if (type.equalsIgnoreCase("SAVING")) {
                                totalsaving = totalsaving + Double.parseDouble(paymentListModel.getData().get(i).getPaidAmount());
                                binding.tvTotal.setText("₹ " + totalsaving);
                            }
                        }

                    } else if (paymentListModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        status.set(2);
                        binding.tvTotal.setText("₹ 0.0");
//                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    status.set(2);
                    binding.tvTotal.setText("₹ 0.0");
//                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentListModel> call, Throwable t) {
                status.set(2);
                binding.tvTotal.setText("₹ 0.0");
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(List<Data> list) {
        paymentTypeListAdapter = new PaymentTypeListAdapter(context, list, type);
        binding.recyclerview.setAdapter(paymentTypeListAdapter);

        paymentTypeListAdapter.setClickListener(new PaymentTypeListAdapter.ItemClickListener() {
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

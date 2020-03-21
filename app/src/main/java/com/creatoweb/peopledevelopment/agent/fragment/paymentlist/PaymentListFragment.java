package com.creatoweb.peopledevelopment.agent.fragment.paymentlist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.databinding.FragmentPaymentlistBinding;
import com.creatoweb.peopledevelopment.databinding.TabLayoutBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PaymentListFragment extends Fragment {

    private Context context;
    private FragmentPaymentlistBinding binding;
    private ObservableInt status = new ObservableInt();

    private int tabid = 0;


    public PaymentListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentlistBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        setAdapter();

        setfilltab(inflater, container, 0, "DDS");
        setfilltab(inflater, container, 1, "Saving");

        return binding.getRoot();
    }

    private void setAdapter() {
        PaymentListPagerAdapter paymentListPagerAdapter = new PaymentListPagerAdapter(getChildFragmentManager(), context);
        binding.viewpager.setAdapter(paymentListPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(1);
        binding.tabExpenses.setupWithViewPager(binding.viewpager);
        binding.viewpager.setCurrentItem(tabid);
    }

    private void setfilltab(LayoutInflater inflater, ViewGroup container, int position, String name) {
        TabLayoutBinding binding1 = DataBindingUtil.inflate(inflater, R.layout.tab_layout, container, false);
        binding1.tvTab.setText(name);
        binding.tabExpenses.getTabAt(position).setCustomView(binding1.getRoot());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

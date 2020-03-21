package com.creatoweb.peopledevelopment.agent.fragment.myaccount;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.databinding.FragmentMyaccountBinding;
import com.creatoweb.peopledevelopment.databinding.TabLayoutBinding;

public class MyAccount extends Fragment {

    public static final String TAG = "MyAccount";
    private Context context;
    private ObservableInt status = new ObservableInt(0);
    FragmentMyaccountBinding binding;

    private int tabid = 0;

    public MyAccount() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyaccountBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        setAdapter();

        setfilltab(inflater, container, 0, "All Member");
        setfilltab(inflater, container, 1, "DDS");
        setfilltab(inflater, container, 2, "Saving");
        setfilltab(inflater, container, 3, "Fd");
        setfilltab(inflater, container, 4, "Rd");
        setfilltab(inflater, container, 5, "Loan");


        return binding.getRoot();
    }

    private void setAdapter() {
        MyAccountPagerAdapter myAccountPagerAdapter = new MyAccountPagerAdapter(getChildFragmentManager(), context);
        binding.viewpager.setAdapter(myAccountPagerAdapter);
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

package com.creatoweb.peopledevelopment.agent.fragment.paymentlist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.creatoweb.peopledevelopment.agent.fragment.myaccount.MyAccountMember;
import com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist.PaymentTypeListFragment;
import com.creatoweb.peopledevelopment.data.Constants;

public class PaymentListPagerAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = "MyAccountPagerAdapter";
    private Context context;

    public PaymentListPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        if (position == 0) {
            fragment = new PaymentTypeListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TYPE, "DDS");
            fragment.setArguments(bundle);
            return fragment;
        } else {
            fragment = new PaymentTypeListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TYPE, "SAVING");
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        String s="";
//        if (position==0)
//        {
//            s="New Case";
//        }else if (position==1)
//        {
//            s="Ongoing Case";
//        }
//
//        return s;
//    }
}

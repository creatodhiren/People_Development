package com.creatoweb.peopledevelopment.agent.fragment.help;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.creatoweb.peopledevelopment.databinding.FragmentHelpBinding;


public class Help extends Fragment {

    public static final String TAG = "Dashboard";
    private Context context;
    private ObservableInt status = new ObservableInt(0);

    public Help() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHelpBinding binding = FragmentHelpBinding.inflate(inflater, container, false);
        binding.setStatus(status);
        return binding.getRoot();
    }
}

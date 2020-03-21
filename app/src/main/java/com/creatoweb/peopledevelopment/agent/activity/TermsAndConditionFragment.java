package com.creatoweb.peopledevelopment.agent.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.creatoweb.peopledevelopment.databinding.FragmentTermsAndConditionBinding;

public class TermsAndConditionFragment extends Fragment {

    //TODO: add new layout and add api load

    private Context context;
    private Activity activity;
    private FragmentTermsAndConditionBinding binding;

    public static final String TAG = "SavingFragment";

    public TermsAndConditionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentTermsAndConditionBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.activity = activity;
    }
}

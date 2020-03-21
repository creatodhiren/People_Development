package com.creatoweb.peopledevelopment.agent.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

public class Profile extends Fragment {

    public static final String TAG = "Profile";
    private Context context;
    private ObservableInt status = new ObservableInt(0);
    FragmentProfileBinding binding;

    public Profile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        AppPref appPref = AppPref.getInstance(context);
        String id = appPref.getString(AppPref.Key.ID, "");
        String name = appPref.getString(AppPref.Key.NAME, "");
        String email = appPref.getString(AppPref.Key.EMAIL, "");
        String contact = appPref.getString(AppPref.Key.MOBILE, "");
        String contact1 = appPref.getString(AppPref.Key.MOBILE1, "");
        String image = appPref.getString(AppPref.Key.Image, "");

        binding.tvAgentName.setText(name);
        binding.tvAgentEmail.setText(email);
        binding.tvAgentMobile.setText(contact);

        setImages(image);

        return binding.getRoot();
    }

    private void setImages(String image_Name){
        Picasso.get().load(ApiClient.IMAGE_URL +image_Name).into(binding.ivAgent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

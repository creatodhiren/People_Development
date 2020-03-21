package com.creatoweb.peopledevelopment.agent.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;

import com.creatoweb.peopledevelopment.Navigation;
import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.agent.activity.login.model.LoginModel;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    public static final String TAG = "Login";
    private Context context;
    private ActivityLoginBinding binding;
    private ObservableInt status = new ObservableInt(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        context = Login.this;
        binding.setStatus(status);

        setTnC();
    }

    public void login(View view) {
//        startActivity(new Intent(this, Navigation.class));
    }

    public void login1(View view) {

        if (binding.userId.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Enter User Id", Toast.LENGTH_SHORT).show();
        } else if (binding.password.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        } else {

            status.set(1);
            Log.e("Login Call", "....." + binding.userId.getText().toString() + ".....");
            Log.e("Login Call", "....." + binding.password.getText().toString() + ".....");

            Call<LoginModel> call = ApiClient.getApiClient().login(binding.userId.getText().toString(), binding.password.getText().toString());
            call.enqueue(new Callback<LoginModel>()
            {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response)
                {
                    Log.e("Login Call", "...response.." + response.code() + ".....");
                    Log.e("Login Call", "...response.." + response.message() + ".....");
                    Log.e("Login Call", "...response.." + response.isSuccessful() + ".....");

                    if (response.isSuccessful())
                    {
                        LoginModel loginModel = response.body();

                        if (loginModel.getMessage() !=null)
                        {

                            if (loginModel.getMessage().equalsIgnoreCase("true"))
                            {
                                status.set(2);
                                loginSuccess(loginModel);
                            } else if (loginModel.getMessage().equalsIgnoreCase("false"))
                            {
                                status.set(3);
                                Toast.makeText(context, "Invaild Id or Password", Toast.LENGTH_SHORT).show();
                            }

                        }else
                        {
                            status.set(3);
                            Toast.makeText(context, "Invaild Id or Password", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Invaild Id or Password", Toast.LENGTH_SHORT).show();
                        status.set(4);
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Log.e("Login Fail", ".........." + t.toString() + "............");
                    Toast.makeText(context, "Some error occured,try again", Toast.LENGTH_SHORT).show();
                    status.set(4);
                }
            });

        }
    }

    void loginSuccess(LoginModel loginModel) {
        AppPref appPref = AppPref.getInstance(context);
        appPref.edit();
        appPref.put(AppPref.Key.SESSION, true);
        appPref.put(AppPref.Key.ID, loginModel.getData().getAgentID());
        appPref.put(AppPref.Key.NAME, loginModel.getData().getEmployeeName());
        appPref.put(AppPref.Key.EMAIL, loginModel.getData().getEmployeeEmail());
        appPref.put(AppPref.Key.MOBILE, loginModel.getData().getEmployeeContact1());
        appPref.put(AppPref.Key.MOBILE1, loginModel.getData().getEmployeeContact2());
        appPref.put(AppPref.Key.PASSWORD, loginModel.getData().getEmployeePassword());
        appPref.put(AppPref.Key.Image, loginModel.getData().getEmployeeProfilePath());
        appPref.commit();

        startActivity(new Intent(this, Navigation.class));
        finish();
    }

    void setTnC()
    {
        SpannableString spannable = new SpannableString("By continuing, you agree to Dhanadhip's Terms & Conditions and Privacy Policy");
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),
                39, 57,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),
                61, 76,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tnc.setText(spannable);
    }

//    public void loginMobile(View view) {
//        startActivity(new Intent(context, LoginMobile.class));
//    }
}

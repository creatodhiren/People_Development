package com.creatoweb.peopledevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.creatoweb.peopledevelopment.agent.activity.login.Login;
import com.creatoweb.peopledevelopment.agent.activity.login.model.LoginModel;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            AppPref appPref = AppPref.getInstance(SplashActivity.this);
            boolean isLogin = appPref.getBoolean(AppPref.Key.SESSION, false);
            String id = appPref.getString(AppPref.Key.MOBILE, "");
            String pass = appPref.getString(AppPref.Key.PASSWORD, "");

            if (isLogin) {
                checkIdPass(id, pass);
            } else gotoLogin();

        }, 1000);
    }

    private void gotoStudent() {
        Intent i = new Intent(SplashActivity.this, Navigation.class);
        startActivity(i);
        finish();
    }

    private void gotoLogin() {
        Intent i = new Intent(SplashActivity.this, Login.class);
        startActivity(i);
        finish();
    }

    public void checkIdPass(String id, String pass) {
        if (id.equalsIgnoreCase("")) {
            gotoLogin();
        } else if (pass.equalsIgnoreCase("")) {
            gotoLogin();
        } else {

            Call<LoginModel> call = ApiClient.getApiClient().login(id, pass);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    Log.e("Login Call", "...response.." + response.code() + ".....");
                    Log.e("Login Call", "...response.." + response.message() + ".....");
                    Log.e("Login Call", "...response.." + response.isSuccessful() + ".....");

                    if (response.isSuccessful()) {
                        LoginModel loginModel = response.body();

                        if (loginModel.getMessage() != null) {

                            if (loginModel.getMessage().equalsIgnoreCase("true")) {
                                if (loginModel.getData().getEmployeeContact1().equalsIgnoreCase(id) && loginModel.getData().getEmployeePassword().equalsIgnoreCase(pass)) {
                                    gotoStudent();
                                }else{
                                    gotoLogin();
                                }
                            }else{
                                gotoLogin();
                            }
//                            else if (loginModel.getMessage().equalsIgnoreCase("false"))
//                            {
//                                Toast.makeText(context, "Invaild Id or Password", Toast.LENGTH_SHORT).show();
//                            }

                        } else {
//                            status.set(3);
                            Toast.makeText(getApplicationContext(), "Invaild Id or Password", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invaild Id or Password", Toast.LENGTH_SHORT).show();
//                        status.set(4);
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Log.e("Login Fail", ".........." + t.toString() + "............");
                    Toast.makeText(getApplicationContext(), "Some error occured,try again", Toast.LENGTH_SHORT).show();
//                    status.set(4);
                }
            });

        }
    }
}

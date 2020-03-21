package com.creatoweb.peopledevelopment.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

   private  static final String BASE_URL = "http://192.168.1.20/accountdhandip/peopledevelopment/MyadminAccount/API/";
    public static final String IMAGE_URL = "http://touchbeenidhi.com/Software/images/";

    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getClient() {
        if (retrofit == null)
        {
            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(160, TimeUnit.SECONDS)
                    .readTimeout(160, TimeUnit.SECONDS).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiClient() {
        return ApiClient.getClient().create(ApiService.class);
    }
}
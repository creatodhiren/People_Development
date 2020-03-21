package com.creatoweb.peopledevelopment.utils;

import android.content.Context;

import com.creatoweb.peopledevelopment.data.AppPref;

public class UserSession {

    public static boolean isLogin(Context context) {
        return AppPref.getInstance(context).getBoolean(AppPref.Key.SESSION, false);
    }

    public static String getId(Context context) {
        if (isLogin(context)) return AppPref.getInstance(context).getString(AppPref.Key.ID, "0");
        else return "0";
    }

}

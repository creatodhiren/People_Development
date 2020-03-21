package com.creatoweb.peopledevelopment.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utils {


    /**
     * @param dp
     * @param context
     * @return convert dp to pixel
     */
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    /**
     * @param text
     * @return get the sha256 encoded key for given text
     * this function uses a private key which is appended in given text.
     */
    public static String getsha256(String text) {

        String input = text.concat("");
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (md != null) {
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0".concat(hashtext);
            }
            return hashtext;
        } else
            return "";

    }

    /**
     * @param context
     * @return check if the internet connection is available or not
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * check if the google play services is available or not in device
     *
     * @param context
     * @return
     */
//    public static boolean isGooglePlayServicesAvailable(Context context) {
//        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
//        return resultCode == ConnectionResult.SUCCESS;
//    }

    /**
     * @param date it is input in the 2019-01-18 form
     * @return return the formatted date in the 18 JAN 2019 formate
     */
    public static String getFormatedDate(String date) {
        String s[] = date.split("-");
        return s[2] + " " + getMonth(s[1]) + ", " + s[0];
    }

    /**
     * get the formatted time
     *
     * @param time input time in 12:30:00 formate
     * @return return time in 12:30 PM format
     */
    public static String getFormatedTime(String time) {
        String t[] = time.split(":");

        int h = Integer.valueOf(t[0]);
        if (h < 12) return t[0] + ":" + t[1] + " AM";
        else return t[0] + ":" + t[1] + " PM";
    }

    /**
     * this function convert month number to 3 character month name
     *
     * @param month nonth number
     * @return 3 character long month name
     */
    public static String getMonth(String month) {
        switch (month) {
            case "01":
                return "JAN";
            case "02":
                return "FAB";
            case "03":
                return "MAR";
            case "04":
                return "APR";
            case "05":
                return "MAY";
            case "06":
                return "JUN";
            case "07":
                return "JUL";
            case "08":
                return "AGU";
            case "09":
                return "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
        }
        return "JAN";
    }

    /**
     * convert bitmap to base64 encoded string
     *
     * @param bitmap bitmap
     * @return base64 encoded string
     */
    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();

        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    public static String generateImageName(String prefix) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("ssmmhhddMM", Locale.getDefault());
        return prefix + new Random().nextInt(50) + sdf.format(currentTime) + ".png";
    }

    public static String getShortTime(String s) {
        switch (s) {
            case "10:00 AM to 12:00 PM":
                return "10-12";
            case "12:00 PM to 2:00 PM":
                return "12-2";
            case "2:00 PM to 4:00 PM":
                return "2-4";
            case "4:00 PM to 6:00 PM":
                return "4-6";
            case "6:00 PM to 8:00 PM":
                return "6-8";
        }
        return "10-12";
    }

    public static String getLongTime(String s) {
        switch (s) {
            case "10-12":
                return "10:00 AM to 12:00 PM";
            case "12-2":
                return "12:00 PM to 2:00 PM";
            case "2-4":
                return "2:00 PM to 4:00 PM";
            case "4-6":
                return "4:00 PM to 6:00 PM";
            case "6-8":
                return "6:00 PM to 8:00 PM";
        }
        return "10:00 AM to 12:00 PM";
    }
}

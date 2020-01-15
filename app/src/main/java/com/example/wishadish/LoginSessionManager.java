package com.example.wishadish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.wishadish.ui.LoginPage.LoginActivity;

import java.util.HashMap;

public class LoginSessionManager {

    private Context mCtx;
    private SharedPreferences pref;
    private int PRIVATE_MODE = 0;
    private SharedPreferences.Editor editor;

    private final String TAG = "LoginSessionManager";

    public static final String PREF_NAME   = "LoginPreference";
    private final String IS_LOGIN    = "IsLoggedIn";

    public static final String EMP_ID           = "emp_id";
    public static final String EMP_USERNAME     = "emp_username";
    public static final String EMP_PASSWORD     = "emp_password";
    public static final String EMP_NAME         = "emp_name";
    public static final String EMP_MOBILE       = "emp_mobile";
    public static final String EMP_COMPANY_NAME = "emp_company_name";
    public static final String EMP_GST_NUMBER   = "emp_gst_number";
    public static final String EMP_ADDRESS      = "emp_address";
    public static final String EMP_TIME         = "emp_time";
    public static final String EMP_EMAIL        = "emp_email";
    public static final String EMP_TYPE         = "emp_type";
    public static final String EMP_TOKEN        = "emp_token";

    public LoginSessionManager(Context context){
        mCtx = context;
        pref = mCtx.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String id, String username, String password, String name, String mobile, String company_name, String gst_no, String address, String time, String email, String type, String token) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(EMP_ID, id);
        editor.putString(EMP_USERNAME, username);
        editor.putString(EMP_PASSWORD, password);
        editor.putString(EMP_NAME, name);
        editor.putString(EMP_MOBILE, mobile);
        editor.putString(EMP_COMPANY_NAME, company_name);
        editor.putString(EMP_GST_NUMBER, gst_no);
        editor.putString(EMP_ADDRESS, address);
        editor.putString(EMP_TIME, time);
        editor.putString(EMP_EMAIL, email);
        editor.putString(EMP_TYPE, type);
        editor.putString(EMP_TOKEN, token);

        editor.commit();

        Log.e(TAG, "login session is created");
    }


    public void logout(){

        editor.clear();
        editor.commit();


        Intent i = new Intent(mCtx, LoginActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(mCtx,"Logged Out successfully!",Toast.LENGTH_SHORT).show();
        mCtx.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public HashMap<String,String> getDriverDetailsFromPref(){

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(EMP_ID, pref.getString(EMP_ID, ""));
        user.put(EMP_USERNAME, pref.getString(EMP_USERNAME, ""));
        user.put(EMP_PASSWORD, pref.getString(EMP_PASSWORD, ""));
        user.put(EMP_NAME, pref.getString(EMP_NAME, ""));
        user.put(EMP_MOBILE, pref.getString(EMP_MOBILE,""));
        user.put(EMP_COMPANY_NAME, pref.getString(EMP_COMPANY_NAME,""));
        user.put(EMP_GST_NUMBER, pref.getString(EMP_GST_NUMBER,""));
        user.put(EMP_ADDRESS, pref.getString(EMP_ADDRESS,""));
        user.put(EMP_TIME, pref.getString(EMP_ADDRESS,""));
        user.put(EMP_EMAIL, pref.getString(EMP_EMAIL,""));
        user.put(EMP_TYPE, pref.getString(EMP_TYPE,""));
        user.put(EMP_TOKEN, pref.getString(EMP_TOKEN,""));

        return user;
    }

}
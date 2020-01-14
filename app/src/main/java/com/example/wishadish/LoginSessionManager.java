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

    public static final String DRIVER_ID         = "driverId";
    public static final String DRIVER_DID       = "driverDid";
    public static final String DRIVER_PASSWORD   = "driverPass";
    public static final String DRIVER_PHONE      = "driver_phone";
    public static final String DRIVER_ONLINE_STATUS      = "driver_online_status";
    public static final String DRIVER_PROFILE_PIC       = "driver_profile_pic";
    public static final String DRIVER_AADHAR_FRONT  = "driver_aadhar_front";
    public static final String DRIVER_DL_FRONT  = "driver_dl_front";
    public static final String DRIVER_DL_BACK  = "driver_dl_back";
    public static final String DRIVER_VEHICLE_ID  = "driver_vehicleID";

//    public static final String DRIVER_FCM_TOKEN      = "driver_FCM_Token";

    public LoginSessionManager(Context context){
        mCtx = context;
        pref = mCtx.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String did, String d_name, String password, String phone, String dp, String aadhar_front, String dl_front, String dl_back,  String vid) {

        editor.putBoolean(IS_LOGIN,true);
        editor.putBoolean(DRIVER_ONLINE_STATUS, true);

        editor.putString(DRIVER_ID,d_name);
        editor.putString(DRIVER_DID,did);
        editor.putString(DRIVER_PASSWORD,password);
        editor.putString(DRIVER_PHONE,phone);
        editor.putString(DRIVER_PROFILE_PIC,dp);
        editor.putString(DRIVER_AADHAR_FRONT, aadhar_front);
        editor.putString(DRIVER_DL_FRONT, dl_front);
        editor.putString(DRIVER_DL_BACK, dl_back);
        editor.putString(DRIVER_VEHICLE_ID, vid);

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

    public boolean driverOnlineStatus(){
        return pref.getBoolean(DRIVER_ONLINE_STATUS, false);
    }

    public HashMap<String,String> getDriverDetailsFromPref(){

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(DRIVER_ID, pref.getString(DRIVER_ID, ""));
        user.put(DRIVER_DID, pref.getString(DRIVER_DID, ""));
        user.put(DRIVER_PASSWORD, pref.getString(DRIVER_PASSWORD, ""));
        user.put(DRIVER_PHONE, pref.getString(DRIVER_PHONE, ""));
        user.put(DRIVER_PROFILE_PIC, pref.getString(DRIVER_PROFILE_PIC,""));
        user.put(DRIVER_AADHAR_FRONT, pref.getString(DRIVER_AADHAR_FRONT,""));
        user.put(DRIVER_DL_FRONT, pref.getString(DRIVER_DL_FRONT,""));
        user.put(DRIVER_DL_BACK, pref.getString(DRIVER_DL_BACK,""));

//        user.put(TRANS_CITY, pref.getString(TRANS_CITY, ""));
//        user.put(TRANS_STATE, pref.getString(TRANS_STATE, ""));
//        user.put(TRANS_BANK_ACC, pref.getString(TRANS_BANK_ACC, ""));


        return user;
    }

}
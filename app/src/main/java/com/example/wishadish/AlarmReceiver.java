package com.example.wishadish;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {

    private int id;

    @Override
    public void onReceive(Context context, Intent intent) {


        id = intent.getExtras().getInt("wait_id");

        sendSMS(context);

        Toast.makeText(context, "Notification sent for id "+id, Toast.LENGTH_SHORT).show();
        Log.e("AlarmService", "Notification sent for id = "+id);

    }

    public void sendSMS(Context context) {

        Log.e("AlarmReceiver", "called : sendFlag()");

    }
}
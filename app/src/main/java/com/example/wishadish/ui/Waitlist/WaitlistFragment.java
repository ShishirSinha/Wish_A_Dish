package com.example.wishadish.ui.Waitlist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.AlarmReceiver;
import com.example.wishadish.MainActivity;
import com.example.wishadish.Utility.MySingleton;
import com.example.wishadish.R;
import com.example.wishadish.WaitListAdapter;
import com.example.wishadish.WaitListClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wishadish.LoginSessionManager.EMP_TOKEN;
import static com.example.wishadish.LoginSessionManager.PREF_NAME;
import static com.example.wishadish.ui.Reports.ReportsFragment.BASE_URL;
import static com.example.wishadish.ui.Reports.ReportsFragment.NO_OF_RETRY;
import static com.example.wishadish.ui.Reports.ReportsFragment.RETRY_SECONDS;

public class WaitlistFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<WaitListClass> waitList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Waitlist");

        setHasOptionsMenu(true);

        waitList = new ArrayList<>();

        recyclerView =(RecyclerView) root.findViewById(R.id.rv2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadWaitingList();

        return root;
    }

    private void loadWaitingList() {

        Log.e(TAG, "called : loadWaitingList()");

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final String WAITLIST_URL = BASE_URL + "/waitlist";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WAITLIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                progressDialog.dismiss();

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int code = jsonResponse.getInt("code");

                    if (code != 1) {
                        Toast.makeText(getContext(), "code + "+code, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONArray array1 = jsonResponse.getJSONArray("list");

                    for(int i = 0; i < array1.length(); i++) {

                        JSONObject jo = array1.getJSONObject(i);

                        String id = jo.getString("id");
                        String name = jo.getString("name");
                        String mob = jo.getString("mob");
                        String tables = jo.getString("tables");
                        String mins = jo.getString("mins");
                        String insert_time = jo.getString("insert_time");
                        String expiry_time = jo.getString("expiry_time");
                        String status = jo.getString("status");

                        int tableFor = Integer.parseInt(tables);
                        int time = Integer.parseInt(mins);

                        WaitListClass wl = new WaitListClass(name, mob, tableFor, time);
                        waitList.add(wl);

                        setAlarmForSMS(id);
                    }

                    adapter = new WaitListAdapter(waitList,getActivity().getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "loadWaitlist : Exception caught  " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(getContext(), "Error in loadWaitingList() !", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                String ACCESS_TOKEN = sharedPreferences.getString(EMP_TOKEN, "");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("x-access-token", ACCESS_TOKEN);

                Log.e("x-access-token", "It is = " + ACCESS_TOKEN);

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(RETRY_SECONDS, NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void setAlarmForSMS(String w_id) {

        Log.e("", "alarm set on click");
        int id = Integer.parseInt(w_id);


        AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("wait_id", id);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), id, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 60);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        Log.e("WaitList Frag", "alarm set for id = " + id);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
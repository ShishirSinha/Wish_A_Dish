package com.example.wishadish.ui.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.AddEditTablesActivity;
import com.example.wishadish.MainActivity;
import com.example.wishadish.R;
import com.example.wishadish.Utility.MySingleton;
import com.example.wishadish.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.wishadish.LoginSessionManager.EMP_ID;
import static com.example.wishadish.LoginSessionManager.EMP_TOKEN;
import static com.example.wishadish.LoginSessionManager.PREF_NAME;
import static com.example.wishadish.ui.Reports.ReportsFragment.BASE_URL;
import static com.example.wishadish.ui.Reports.ReportsFragment.NO_OF_RETRY;
import static com.example.wishadish.ui.Reports.ReportsFragment.RETRY_SECONDS;

public class SettingsFragment extends Fragment {

    private Switch tableModeSwitch,serviceChargeSwitch,happyHoursSwitch,kotSwitch,codelessModeSwitch,gstInclusiveSwitch;
    private static TextView printerNameTV;
    private TextView tableModeTv,serviceChargeTv,happyHoursTv;

    private final String TAG = getClass().getSimpleName();

    SharedPreferences pref;
    public static final String SETTINGS_PREF   = "LoginPreference";
    public static final String TABLE_MODE = "tableMode";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Settings");

        setHasOptionsMenu(true);

        tableModeTv = root.findViewById(R.id.tableModeTv);
        tableModeSwitch = root.findViewById(R.id.tableModeSwitch);
        serviceChargeTv = root.findViewById(R.id.serviceChargeTv);
        serviceChargeSwitch = root.findViewById(R.id.serviceChargeSwitch);
        happyHoursTv = root.findViewById(R.id.happyHoursTv);
        happyHoursSwitch = root.findViewById(R.id.happyHoursSwitch);
        kotSwitch = root.findViewById(R.id.kotSwitch);
        codelessModeSwitch = root.findViewById(R.id.codelessModeSwitch);
        gstInclusiveSwitch = root.findViewById(R.id.gstInclusiveSwitch);
        printerNameTV = root.findViewById(R.id.printerNameTV);

        getSettings();

        pref = getActivity().getSharedPreferences(SETTINGS_PREF,Context.MODE_PRIVATE);

        tableModeSwitch.setChecked(pref.getBoolean(TABLE_MODE, false));

        tableModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(TABLE_MODE,true);
                    editor.commit();

                    Fragment fragment = new HomeFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flcontent, fragment ); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                } else {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(TABLE_MODE,false);
                    editor.commit();
                }

            }
        });

        serviceChargeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Fragment newFragment = new EnterServiceChargeFrag();
                    getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, newFragment).commit();
                } else {

                }
            }
        });

        happyHoursSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Fragment newFragment = new HappyHoursFrag();
                    getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, newFragment).commit();
                } else {

                }
            }
        });

        kotSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        codelessModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        gstInclusiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        printerNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new SelectPrinterFrag();
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, newFragment).commit();
            }
        });

        tableModeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(TABLE_MODE,true);
                editor.commit();

                Intent intent = new Intent(getContext(), AddEditTablesActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public static void setPrinterName(String printer) {
        Log.e("c3", "Printer name  = "+printer);
        printerNameTV.setText(printer);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private void getSettings(){

        Log.e(TAG, "called : getSettings");

        //mProgressBar.setVisibility(View.VISIBLE);

        String REPORTS_URL = BASE_URL + "/settings";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REPORTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int code = jsonResponse.getInt("code");

                    if (code != 1) {
                        Toast.makeText(getContext(), "response : "+ response, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONArray jsonSettings = jsonResponse.getJSONArray("settings");

//                    Boolean tableMode  = jsonSettings.getJSONObject(0).getBoolean("tableMode");
//                    Boolean serviceCharge = jsonSettings.getJSONObject(0).getBoolean("serviceCharge");
//                    Boolean happyHours = jsonSettings.getJSONObject(0).getBoolean("happyHours");
//                    Boolean KOT = jsonSettings.getJSONObject(0).getBoolean("KOT");
//                    Boolean codelessmode = jsonSettings.getJSONObject(0).getBoolean("codelessMode");
//                    Boolean getInclusive = jsonSettings.getJSONObject(0).getBoolean("getInclusive");
//                    String printerName = jsonSettings.getJSONObject(0).getString("printerName");
//                    Double ServiceChargeValue = jsonSettings.getJSONObject(0).getDouble("ServiceChargeValue");
//                    Double HappyHours = jsonSettings.getJSONObject(0).getDouble("HappyHours");
//                    String startTime = jsonSettings.getJSONObject(0).getString("ServiceChargeValue");


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Settings Fragment : Exception caught  " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mProgressBar.setVisibility(View.GONE);
                Log.e("error", error.toString());
                Toast.makeText(getContext(),"Error in getSettings() !", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
                String mid = sharedPreferences.getString(EMP_ID,"");
                String ACCESS_TOKEN = sharedPreferences.getString(EMP_TOKEN,"");
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("x-access-token", ACCESS_TOKEN);
                params.put("merchant_id", mid);

                Log.e("x-access-token", "Its is = "+ACCESS_TOKEN);
                Log.e("merchant_id", "it is = "+mid);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(RETRY_SECONDS, NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
}
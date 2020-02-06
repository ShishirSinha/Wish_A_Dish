package com.example.wishadish.ui.OrderOverview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.MainActivity;
import com.example.wishadish.MenuItemClass;
import com.example.wishadish.R;
import com.example.wishadish.TableInfoClass;
import com.example.wishadish.Utility.MySingleton;
import com.example.wishadish.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.example.wishadish.LoginSessionManager.EMP_ID;
import static com.example.wishadish.LoginSessionManager.EMP_TOKEN;
import static com.example.wishadish.LoginSessionManager.PREF_NAME;
import static com.example.wishadish.ui.Reports.ReportsFragment.BASE_URL;
import static com.example.wishadish.ui.Reports.ReportsFragment.NO_OF_RETRY;
import static com.example.wishadish.ui.Reports.ReportsFragment.RETRY_SECONDS;
import static com.example.wishadish.ui.Settings.SettingsFragment.SERVICE_CHARGE;
import static com.example.wishadish.ui.Settings.SettingsFragment.SETTINGS_PREF;

public class ConfirmOrderFrag extends Fragment {

    private String TAG = "ConfirmOrderFrag";
    private ListView listView;
    private TextView totalAmountTv;
    private Button confirmBtn;
    private ArrayList<String> arrayList;
    List<MenuItemClass> list;

    private int totalQty;
    private double totalDis,cgstPer,sgstPer;

    private String mCustNum,mCustName,mTableFor,mWaitTime;
    private int total_qty,table_no,non_chargable,grand_total;
    private double amount,dis_per,discount,cgst,sgst,service_charge,other_charge,r_off,cust_gst;
    private String customerId,waiter_id,time,remark,mobile,name,dob,anniversary,manager_id,kot_status,items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.confirm_order_frag, container, false);

        totalAmountTv = view.findViewById(R.id.totalAmountConfirmTV);
        confirmBtn  = view.findViewById(R.id.confirmOrderBtn);
        listView = view.findViewById(R.id.itemsListview);

        totalQty = 0;
        totalDis = 0;
        cgstPer = 0;
        sgstPer = 0;

        items = "";

        list = ((OrderOverviewActivity)getActivity()).getOrderList();
        arrayList = new ArrayList<>();

        for(int i=0;i<list.size();i++){
            String id1 = list.get(i).getmId();
            String name1 = list.get(i).getmItemName();
            String unit1 = list.get(i).getmUnit();
            int qty1 = list.get(i).getmQuantity();
            double rate1 = list.get(i).getmCost();

            double amount1 = qty1 * rate1;
            double dis_per1 = ((OrderOverviewActivity) getActivity()).getDiscountPercent();
            double dis_rs1 = dis_per1/100.0 * amount1;
            double gst_per1 = list.get(i).getmGstPercent();
            double gst_final_amt1 = gst_per1/100.0 * amount1;

            String disc1 = "none";

            items = items +id1+","+name1+","+unit1+","+qty1+","+rate1+","+amount1+","+dis_per1
                    +","+dis_rs1+","+gst_per1+","+gst_final_amt1+","+disc1 ;

            if(i < list.size()-1)
                items = items + "#";

            name1 = name1.toLowerCase();
            String[] it = name1.split(" ");
            name1 = "";
            for(int j=0;j<it.length;j++){
                if(!it[j].equals("")) {
                    char c = it[j].charAt(0);
                    if(c>='a' && c<='z') {
                        char d = Character.toUpperCase(c);
                        it[j] = it[j].replaceFirst("" + c, "" + d);
                    }
                    name1 = name1 + it[j] + " ";
                }
            }

            arrayList.add(name1 + "  x" +qty1);

            totalQty += qty1;
            totalDis += dis_rs1;

            Log.e("ITEMS","items = "+name1+"  x"+qty1);
        }

        Log.e(TAG, "items json :"+items);

        String[] amountStringArray = ((OrderOverviewActivity)getActivity()).getTotalAmt().split(" ");
        amount = Double.parseDouble(amountStringArray[1]);
        totalAmountTv.setText("Total Amount : ₹"+amount);

        setParams();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        return view;
    }

    private void submitOrder() {

        Log.e(TAG, "called : submitOrder()");

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String GET_TABLES_URL = BASE_URL + "/dashboard/submit";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_TABLES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                progressDialog.dismiss();

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int code = jsonResponse.getInt("code");

                    if (code != 1) {
                        Toast.makeText(getContext(), "code + " + code, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent launchNextActivity;
                    launchNextActivity = new Intent(getContext(), MainActivity.class);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(launchNextActivity);
                    getActivity().finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "submitOrder : Exception caught  " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(getContext(), "Error in submitOrder() !", Toast.LENGTH_SHORT).show();
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

                params.put("customer_id", customerId);
                params.put("waiter_id", waiter_id);
                params.put("total_qty", ""+total_qty);
                params.put("amount", ""+amount);
                params.put("dis_per", ""+dis_per);
                params.put("discount", ""+discount);
                params.put("cgst", ""+cgst);
                params.put("sgst", ""+sgst);
                params.put("service_charge", ""+service_charge);
                params.put("other_charge", ""+other_charge);
                params.put("r_off", ""+r_off);
                params.put("grand_total", ""+grand_total);
                params.put("non_chargable", ""+non_chargable);
                params.put("time", time);
                params.put("remark", remark);
                params.put("mobile", mobile);
                params.put("name", name);
                params.put("cust_gst", ""+cust_gst);
                params.put("dob", dob);
                params.put("anniversary", anniversary);
                params.put("table_no", ""+table_no);
                params.put("manager_id", manager_id);
                params.put("kot_status", kot_status);
                params.put("items", items);


                Log.e("customer_id", ": "+customerId);
                Log.e("waiter_id", waiter_id);
                Log.e("total_qty", ""+total_qty);
                Log.e("amount", ""+amount);
                Log.e("dis_per", ""+dis_per);
                Log.e("discount", ""+discount);
                Log.e("cgst", ""+cgst);
                Log.e("sgst", ""+sgst);
                Log.e("service_charge", ""+service_charge);
                Log.e("other_charge", ""+other_charge);
                Log.e("r_off", ""+r_off);
                Log.e("grand_total", ""+grand_total);
                Log.e("non_chargable", ""+non_chargable);
                Log.e("time", time);
                Log.e("remark", remark);
                Log.e("mobile", mobile);
                Log.e("name", name);
                Log.e("cust_gst", ""+cust_gst);
                Log.e("dob", dob);
                Log.e("anniversary", anniversary);
                Log.e("table_no", ""+table_no);
                Log.e("manager_id", manager_id);
                Log.e("kot_status", kot_status);
                Log.e("items", items);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(RETRY_SECONDS, NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar().show();
    }

    private void setParams(){

        SharedPreferences userPref = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences settingPref = getActivity().getSharedPreferences(SETTINGS_PREF,Context.MODE_PRIVATE);

        // customer_id
        customerId = "1";

        // waiter_id
        waiter_id = userPref.getString(EMP_ID,"");

        // total_qty
        total_qty = totalQty;

        // amount
        amount = amount;

        // dis_per
        dis_per = ((OrderOverviewActivity) getActivity()).getDiscountPercent();

        // discountr_off
        discount = totalDis;

        // cgst
        cgst = cgstPer/100.0 * amount;

        // sgst
        sgst = sgstPer/100.0 * amount;

        // service_charge
        service_charge = amount * settingPref.getFloat(SERVICE_CHARGE,0)/100.0;

        // other_charge (delivery charge)
        other_charge = ((OrderOverviewActivity) getActivity()).getDeliveryCharge();

        // non_chargable
        non_chargable = ((OrderOverviewActivity) getActivity()).getNonChargable() ? 1 : 0;

        double tempTotal = amount - discount + cgst + sgst + service_charge;

        // r_off
        r_off = Math.ceil(tempTotal) - tempTotal;

        // grand_total
        grand_total = (int) Math.ceil(tempTotal);

        // time
        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // remark
        remark = ((OrderOverviewActivity) getActivity()).getRemark();

        // mobile
        mobile = ((OrderOverviewActivity) getActivity()).getMobile();

        // name
        name = ((OrderOverviewActivity) getActivity()).getName();

        // cust_gst
        cust_gst = 0;

        // dob
        dob = ((OrderOverviewActivity) getActivity()).getDob();

        // anniversery
        anniversary = ((OrderOverviewActivity) getActivity()).getAnniversery();

        // table_no
        table_no = 0;

        // managaer_id
        manager_id = "";

        // kot_status
        kot_status = "0";
    }
}

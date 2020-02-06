package com.example.wishadish.ui.Bills;

import android.app.ProgressDialog;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.AllOrderClass;
import com.example.wishadish.MainActivity;
import com.example.wishadish.R;
import com.example.wishadish.Utility.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wishadish.LoginSessionManager.EMP_TOKEN;
import static com.example.wishadish.LoginSessionManager.PREF_NAME;
import static com.example.wishadish.ui.Reports.ReportsFragment.BASE_URL;
import static com.example.wishadish.ui.Reports.ReportsFragment.NO_OF_RETRY;
import static com.example.wishadish.ui.Reports.ReportsFragment.RETRY_SECONDS;

public class TotalBillsFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AllOrderClass> allOrderList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Total Bills");

        setHasOptionsMenu(true);

//        final TextView textView = root.findViewById(R.id.text_tools);
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        allOrderList = new ArrayList<>();

        recyclerView =(RecyclerView) root.findViewById(R.id.rv6);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTotalBills();

        return root;
    }

    private void loadTotalBills() {

        Log.e(TAG, "called : loadTotalBills()");

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final String CURRENT_ORDERS_URL = BASE_URL + "/bills/all";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CURRENT_ORDERS_URL, new Response.Listener<String>() {
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

                    JSONArray array1 = jsonResponse.getJSONArray("orders");

                    for(int i = 0; i < array1.length(); i++) {

                        JSONObject jo = array1.getJSONObject(i);

                        int id = Integer.parseInt(jo.getString("id"));
                        String bill_id = jo.getString("bill_id");
                        String customer_id = jo.getString("customer_id");
                        String waiter_id = jo.getString("waiter_id");
                        int total_qty = Integer.parseInt(jo.getString("total_qty"));
                        double amount = Double.parseDouble(jo.getString("amount"));
                        String time = jo.getString("time");
                        String table_no = jo.getString("table_no");

                        String[] x = time.split(" ");

                        String orderDate = x[0];
                        String orderTime = x[1];

                        AllOrderClass ao = new AllOrderClass(bill_id, amount, orderDate, orderTime, table_no);
                        allOrderList.add(ao);
                    }

                    adapter = new AllOrderAdapter(allOrderList,getContext(),getActivity());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "loadCurrentOrders : Exception caught  " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(getContext(), "Error in loadCurrentOrders() !", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
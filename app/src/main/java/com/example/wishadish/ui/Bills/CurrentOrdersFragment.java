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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.CurrentOrderAdapter;
import com.example.wishadish.CurrentOrderClass;
import com.example.wishadish.MySingleton;
import com.example.wishadish.R;
import com.example.wishadish.WaitListAdapter;
import com.example.wishadish.WaitListClass;

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

public class CurrentOrdersFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CurrentOrderClass> currentOrderClassList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        setHasOptionsMenu(true);

//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        currentOrderClassList = new ArrayList<>();

        recyclerView =(RecyclerView) root.findViewById(R.id.rv5);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CurrentOrderClass co1 = new CurrentOrderClass("1",720,"08-01-2020",false);
        CurrentOrderClass co2 = new CurrentOrderClass("2",635,"08-01-2020",true);
        currentOrderClassList.add(co1);
        currentOrderClassList.add(co2);

        adapter = new CurrentOrderAdapter(currentOrderClassList,getActivity().getApplicationContext(), getActivity());
        recyclerView.setAdapter(adapter);

        loadCurrentOrders();

        return root;
    }

    private void loadCurrentOrders() {

        Log.e(TAG, "called : loadCurrentOrders()");

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final String CURRENT_ORDERS_URL = BASE_URL + "/bills/current";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, CURRENT_ORDERS_URL, new Response.Listener<String>() {
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
                        double amount = Double.parseDouble(jo.getString("amount"));
                        String time = jo.getString("time");
                        String waiter_id = jo.getString("waiter_id");
                        String table_no = jo.getString("table_no");


                        CurrentOrderClass co = new CurrentOrderClass(""+id,amount,"08-01-2020",false);
                        currentOrderClassList.add(co);
                    }

                    adapter = new CurrentOrderAdapter(currentOrderClassList,getContext(),getActivity());
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
                Map<String, String> params = new HashMap<String, String>();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                String ACCESS_TOKEN = sharedPreferences.getString(EMP_TOKEN,"");
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("x-access-token", ACCESS_TOKEN);

                Log.e("x-access-token", "It is = "+ACCESS_TOKEN);
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
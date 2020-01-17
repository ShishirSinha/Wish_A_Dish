package com.example.wishadish.ui.Reports;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.MySingleton;
import com.example.wishadish.R;
import com.example.wishadish.WaitListClass;
import com.example.wishadish.ui.Waitlist.GalleryViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wishadish.LoginSessionManager.EMP_TOKEN;
import static com.example.wishadish.LoginSessionManager.PREF_NAME;

public class ReportsFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<WaitListClass> waitList;

    private TextView maxBillId0,maxBillId1,maxBillId2,maxBillId3,maxBillId4;
    private TextView maxBillAmt0,maxBillAmt1,maxBillAmt2,maxBillAmt3,maxBillAmt4;

    private TextView maxItemName0,maxItemName1,maxItemName2,maxItemName3,maxItemName4;
    private TextView maxItemAmt0,maxItemAmt1,maxItemAmt2,maxItemAmt3,maxItemAmt4;

    private TextView disId0,disId1,disId2,disId3,disId4;
    private TextView disAmt0,disAmt1,disAmt2,disAmt3,disAmt4;

    private TextView payMet0,payMet1,payMet2,payMet3,payMet4;
    private TextView payMethodAmt0,payMethodAmt1,payMethodAmt2,payMethodAmt3,payMethodAmt4;

    public static final String BASE_URL = "http://194.59.165.34:5008";
    public static final int RETRY_SECONDS = 5 * 1000;
    public static final int NO_OF_RETRY = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reports, container, false);

        setHasOptionsMenu(true);

        maxBillId0 = root.findViewById(R.id.maxBillId0);
        maxBillId1 = root.findViewById(R.id.maxBillId1);
        maxBillId2 = root.findViewById(R.id.maxBillId2);
        maxBillId3 = root.findViewById(R.id.maxBillId3);
        maxBillId4 = root.findViewById(R.id.maxBillId4);

        maxBillAmt0 = root.findViewById(R.id.maxBillAmt0);
        maxBillAmt1 = root.findViewById(R.id.maxBillAmt1);
        maxBillAmt2 = root.findViewById(R.id.maxBillAmt2);
        maxBillAmt3 = root.findViewById(R.id.maxBillAmt3);
        maxBillAmt4 = root.findViewById(R.id.maxBillAmt4);

        maxItemName0 = root.findViewById(R.id.maxItemName0);
        maxItemName1 = root.findViewById(R.id.maxItemName1);
        maxItemName2 = root.findViewById(R.id.maxItemName2);
        maxItemName3 = root.findViewById(R.id.maxItemName3);
        maxItemName4 = root.findViewById(R.id.maxItemName4);

        maxItemAmt0 = root.findViewById(R.id.maxItemAmt0);
        maxItemAmt1 = root.findViewById(R.id.maxItemAmt1);
        maxItemAmt2 = root.findViewById(R.id.maxItemAmt2);
        maxItemAmt3 = root.findViewById(R.id.maxItemAmt3);
        maxItemAmt4 = root.findViewById(R.id.maxItemAmt4);

        disId0 = root.findViewById(R.id.disId0);
        disId1 = root.findViewById(R.id.disId1);
        disId2 = root.findViewById(R.id.disId2);
        disId3 = root.findViewById(R.id.disId3);
        disId4 = root.findViewById(R.id.disId4);

        disAmt0 = root.findViewById(R.id.disAmt0);
        disAmt1 = root.findViewById(R.id.disAmt1);
        disAmt2 = root.findViewById(R.id.disAmt2);
        disAmt3 = root.findViewById(R.id.disAmt3);
        disAmt4 = root.findViewById(R.id.disAmt4);

        payMet0 = root.findViewById(R.id.payMet0);
        payMet1 = root.findViewById(R.id.payMet1);
        payMet2 = root.findViewById(R.id.payMet2);
        payMet3 = root.findViewById(R.id.payMet3);
        payMet4 = root.findViewById(R.id.payMet4);

        payMethodAmt0 = root.findViewById(R.id.payMethodAmt0);
        payMethodAmt1 = root.findViewById(R.id.payMethodAmt1);
        payMethodAmt2 = root.findViewById(R.id.payMethodAmt2);
        payMethodAmt3 = root.findViewById(R.id.payMethodAmt3);
        payMethodAmt4 = root.findViewById(R.id.payMethodAmt4);

        getReport();

        return root;
    }

    private void getReport(){

        Log.e(TAG, "called : getReport");

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String REPORTS_URL = BASE_URL + "/reports";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REPORTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                progressDialog.dismiss();

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int code = jsonResponse.getInt("code");

                    if (code != 1) {
                        Toast.makeText(getContext(), "response : "+ response, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONArray jsonMaxAmount = jsonResponse.getJSONArray("max_amounts");

                    String aid0 = jsonMaxAmount.getJSONObject(0).getString("id");
                    String amt0 = jsonMaxAmount.getJSONObject(0).getString("amount");
                    String aid1 = jsonMaxAmount.getJSONObject(1).getString("id");
                    String amt1 = jsonMaxAmount.getJSONObject(1).getString("amount");
                    String aid2 = jsonMaxAmount.getJSONObject(2).getString("id");
                    String amt2 = jsonMaxAmount.getJSONObject(2).getString("amount");
                    String aid3 = jsonMaxAmount.getJSONObject(3).getString("id");
                    String amt3 = jsonMaxAmount.getJSONObject(3).getString("amount");
                    String aid4 = jsonMaxAmount.getJSONObject(4).getString("id");
                    String amt4 = jsonMaxAmount.getJSONObject(4).getString("amount");

                    JSONArray jsonMaxDiscount = jsonResponse.getJSONArray("max_discount");

                    String did0 = jsonMaxDiscount.getJSONObject(0).getString("id");
                    String dis0 = jsonMaxDiscount.getJSONObject(0).getString("discount");
                    String did1 = jsonMaxDiscount.getJSONObject(1).getString("id");
                    String dis1 = jsonMaxDiscount.getJSONObject(1).getString("discount");
                    String did2 = jsonMaxDiscount.getJSONObject(2).getString("id");
                    String dis2 = jsonMaxDiscount.getJSONObject(2).getString("discount");
                    String did3 = jsonMaxDiscount.getJSONObject(3).getString("id");
                    String dis3 = jsonMaxDiscount.getJSONObject(3).getString("discount");
                    String did4 = jsonMaxDiscount.getJSONObject(4).getString("id");
                    String dis4 = jsonMaxDiscount.getJSONObject(4).getString("discount");

                    JSONArray jsonMaxQty = jsonResponse.getJSONArray("max_qty");

                    String name0 = jsonMaxQty.getJSONObject(0).getString("item_name");
                    String qty0 = jsonMaxQty.getJSONObject(0).getString("max_qty");
                    String name1 = jsonMaxQty.getJSONObject(1).getString("item_name");
                    String qty1 = jsonMaxQty.getJSONObject(1).getString("max_qty");
                    String name2 = jsonMaxQty.getJSONObject(2).getString("item_name");
                    String qty2 = jsonMaxQty.getJSONObject(2).getString("max_qty");
                    String name3 = jsonMaxQty.getJSONObject(3).getString("item_name");
                    String qty3 = jsonMaxQty.getJSONObject(3).getString("max_qty");
                    String name4 = jsonMaxQty.getJSONObject(4).getString("item_name");
                    String qty4 = jsonMaxQty.getJSONObject(4).getString("max_qty");

                    JSONArray jsonPayMethod = jsonResponse.getJSONArray("payment_method");

                    String method0 = jsonPayMethod.getJSONObject(0).getString("method");
                    String pamt0 = jsonPayMethod.getJSONObject(0).getString("amount");
                    String method1 = jsonPayMethod.getJSONObject(1).getString("method");
                    String pamt1 = jsonPayMethod.getJSONObject(1).getString("amount");
                    String method2 = jsonPayMethod.getJSONObject(2).getString("method");
                    String pamt2 = jsonPayMethod.getJSONObject(2).getString("amount");
                    String method3 = jsonPayMethod.getJSONObject(3).getString("method");
                    String pamt3 = jsonPayMethod.getJSONObject(3).getString("amount");
                    String method4 = jsonPayMethod.getJSONObject(4).getString("method");
                    String pamt4 = jsonPayMethod.getJSONObject(4).getString("amount");

                    maxBillId0.setText("#"+aid0);
                    maxBillId1.setText("#"+aid1);
                    maxBillId2.setText("#"+aid2);
                    maxBillId3.setText("#"+aid3);
                    maxBillId4.setText("#"+aid4);

                    maxBillAmt0.setText("₹"+amt0);
                    maxBillAmt1.setText("₹"+amt1);
                    maxBillAmt2.setText("₹"+amt2);
                    maxBillAmt3.setText("₹"+amt3);
                    maxBillAmt4.setText("₹"+amt4);

                    maxItemName0.setText(name0);
                    maxItemName1.setText(name1);
                    maxItemName2.setText(name2);
                    maxItemName3.setText(name3);
                    maxItemName4.setText(name4);

                    maxItemAmt0.setText(qty0);
                    maxItemAmt1.setText(qty1);
                    maxItemAmt2.setText(qty2);
                    maxItemAmt3.setText(qty3);
                    maxItemAmt4.setText(qty4);

                    disId0.setText("#"+did0);
                    disId1.setText("#"+did1);
                    disId2.setText("#"+did2);
                    disId3.setText("#"+did3);
                    disId4.setText("#"+did4);

                    disAmt0.setText("₹"+dis0);
                    disAmt1.setText("₹"+dis1);
                    disAmt2.setText("₹"+dis2);
                    disAmt3.setText("₹"+dis3);
                    disAmt4.setText("₹"+dis4);

                    payMet0.setText(method0);
                    payMet1.setText(method1);
                    payMet2.setText(method2);
                    payMet3.setText(method3);
                    payMet4.setText(method4);

                    payMethodAmt0.setText("₹"+pamt0);
                    payMethodAmt1.setText("₹"+pamt1);
                    payMethodAmt2.setText("₹"+pamt2);
                    payMethodAmt3.setText("₹"+pamt3);
                    payMethodAmt4.setText("₹"+pamt4);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Reports Fragment : Exception caught  " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mProgressBar.setVisibility(View.GONE);
                Log.e("error", error.toString());
                Toast.makeText(getContext(),"Error in getReports() !", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
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
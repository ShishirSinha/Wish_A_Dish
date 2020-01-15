package com.example.wishadish.ui.Reports;

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

    public static final String BASE_URL = "http://194.59.165.34:5008";
    public static final int RETRY_SECONDS = 5 * 1000;
    public static final int NO_OF_RETRY = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reports, container, false);

        setHasOptionsMenu(true);

        getReport();

        return root;
    }

    private void getReport(){

        Log.e(TAG, "called : getReport");

        //mProgressBar.setVisibility(View.VISIBLE);

        String REPORTS_URL = BASE_URL + "/reports";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REPORTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                //mProgressBar.setVisibility(View.GONE);

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

                Log.e("x-access-token", "Its is = "+ACCESS_TOKEN);
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
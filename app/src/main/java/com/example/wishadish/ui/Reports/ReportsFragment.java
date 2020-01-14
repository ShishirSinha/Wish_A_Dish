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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    JSONObject jsonResult = jsonResponse.getJSONArray("result").getJSONObject(0);

                    String d_name = jsonResult.getString("d_name");
                    String phone = jsonResult.getString("phn");

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
        }) ;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(RETRY_SECONDS, NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
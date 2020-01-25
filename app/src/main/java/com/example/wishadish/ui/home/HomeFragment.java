package com.example.wishadish.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.wishadish.DataBases.AppExecutors;
import com.example.wishadish.DataBases.CompleteMenuTable;
import com.example.wishadish.DataBases.DbRepository;
import com.example.wishadish.DataBases.MenuDb;
import com.example.wishadish.MainActivity;
import com.example.wishadish.MenuItemClass;
import com.example.wishadish.MenuItemAdapter;
import com.example.wishadish.R;
import com.example.wishadish.TableAdapter;
import com.example.wishadish.TableInfoClass;
import com.example.wishadish.Utility.MySingleton;
import com.example.wishadish.ui.OrderOverview.OrderOverviewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wishadish.LoginSessionManager.EMP_ID;
import static com.example.wishadish.LoginSessionManager.EMP_TOKEN;
import static com.example.wishadish.LoginSessionManager.PREF_NAME;
import static com.example.wishadish.ui.Reports.ReportsFragment.BASE_URL;
import static com.example.wishadish.ui.Reports.ReportsFragment.NO_OF_RETRY;
import static com.example.wishadish.ui.Reports.ReportsFragment.RETRY_SECONDS;
import static com.example.wishadish.ui.Settings.SettingsFragment.SETTINGS_PREF;
import static com.example.wishadish.ui.Settings.SettingsFragment.TABLE_MODE;

public class HomeFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter1;
    private List<MenuItemClass> menuItems;
    private LinearLayout menuModeLL;
    private RelativeLayout totalAmountRL;
    private SearchView searchView;
    private String previousQuertText;
    private ListView searchLV;
    private RecyclerView.Adapter searchAdapter;

    private List<TableInfoClass> tableList;
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    private LinearLayout tableModeLL;

    private List<CompleteMenuTable> completeMenuList;
    //This arraylist will have data as pulled from server. This will keep cumulating.
    private List<MenuItemClass> productResults;
    //Based on the search string, only filtered products will be moved here from productResults
    private List<MenuItemClass> filteredProductResults;

    private MenuDb mDb;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Home");

        setHasOptionsMenu(true);

        mDb = MenuDb.getInstance(getContext().getApplicationContext());

        getMenuList();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean(TABLE_MODE, false);

        Log.e(TAG, "table mode = " + b);

        if (b) {

            tableModeLL = root.findViewById(R.id.homeTableTypeLL);
            menuModeLL = root.findViewById(R.id.homeMenuTypeLL);

            tableModeLL.setVisibility(View.VISIBLE);
            menuModeLL.setVisibility(View.GONE);

            tableList = new ArrayList<>();

            recyclerView2 = (RecyclerView) root.findViewById(R.id.rv4);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

            TableInfoClass mi1 = new TableInfoClass(7, 4, true);
            TableInfoClass mi2 = new TableInfoClass(10, 6, true);
            tableList.add(mi1);
            tableList.add(mi2);

            adapter2 = new TableAdapter(tableList, getActivity().getApplicationContext());
            recyclerView2.setAdapter(adapter2);

        } else {

            tableModeLL = root.findViewById(R.id.homeTableTypeLL);
            menuModeLL = root.findViewById(R.id.homeMenuTypeLL);

            menuModeLL.setVisibility(View.VISIBLE);
            tableModeLL.setVisibility(View.GONE);

            searchView = root.findViewById(R.id.searchView);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    searchView.clearFocus();
                }
            }, 100);

            menuItems = new ArrayList<>();
            productResults = new ArrayList<>();
            filteredProductResults = new ArrayList<>();

            searchView = root.findViewById(R.id.searchView);
            totalAmountRL = root.findViewById(R.id.totalAmountBtnRL);
            searchLV = root.findViewById(R.id.listview);
            recyclerView1 = root.findViewById(R.id.rv1);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

            MenuItemClass mi1 = new MenuItemClass("Paneer Butter Masala", 0, "Veg", 320);
            MenuItemClass mi2 = new MenuItemClass("Butter Naan", 1, "Chapati", 400);
            menuItems.add(mi1);
            menuItems.add(mi2);

            adapter1 = new MenuItemAdapter(menuItems, getActivity().getApplicationContext());
            recyclerView1.setAdapter(adapter1);

            searchView.setIconified(false);

            totalAmountRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), OrderOverviewActivity.class);
                    startActivity(intent);
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    newText = newText.trim();

                    if(newText.length()>2 && !(newText.substring(0,3).equalsIgnoreCase(previousQuertText))) {
                        searchLV.setVisibility(View.VISIBLE);
                        productResults.clear();
                        displaySearchResults(newText);
                        previousQuertText = newText.substring(0,3);
                        Log.e(TAG, "ptext = "+previousQuertText);
                        Log.e(TAG,"1  newText="+newText+"  "+"previousText="+previousQuertText+"  productResults.size()= "+ productResults.size());
                    }
                    else if(newText.length()>2 && newText.substring(0,3).equalsIgnoreCase(previousQuertText)){
                        searchLV.setVisibility(View.VISIBLE);
                        displaySearchResultFromPreviousList(newText);
                        Log.e(TAG,"2  newText="+newText+"  "+"previousText="+previousQuertText+"  productResults.size()= "+ productResults.size());
                    }
                    else if(newText.length()<=2){
                        filteredProductResults.clear();
                        searchLV.setVisibility(View.GONE);
                        Log.e(TAG,"3  newText="+newText+"  "+"previousText="+previousQuertText+"  productResults.size()= "+ productResults.size());
                    }

                    return false;
                }
            });
        }

        return root;
    }

    private void displaySearchResultFromPreviousList(String itemname){
        filterProductArray(itemname);
        searchLV.setAdapter(new SearchResultsAdapter(getActivity(),filteredProductResults));
    }

    private void displaySearchResults(final String itemname){

        final MenuDb appDb = MenuDb.getInstance(getContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                completeMenuList = appDb.completeMenuTableDao().getAllItems();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "complete menu size = "+completeMenuList.size());

                        for(int i=0;i<completeMenuList.size();i++) {
                            String name = completeMenuList.get(i).getName();
                            Double rate = Double.parseDouble(completeMenuList.get(i).getRate());
                            String type = completeMenuList.get(i).getVeg();
                            int id = Integer.parseInt(completeMenuList.get(i).getId());

                            MenuItemClass tempItem = new MenuItemClass(name, 0, type, rate);

                            String matchfound = "N";

                            for (int j = 0; i < productResults.size(); j++) {
                                if (productResults.get(j).getmItemName().equals(tempItem.getmItemName())) {
                                    matchfound = "Y";
                                    break;
                                }
                            }

                            if (matchfound.equals("N")) {
                                productResults.add(tempItem);
                            }
                        }

                        //calling this method to filter the search results from productResults and move them to
                        //filteredProductResults
                        filterProductArray(itemname);
                        searchLV.setAdapter(new SearchResultsAdapter(getActivity(),filteredProductResults));
                    }
                });
            }
        });

    }

    private void getMenuList() {

        Log.e(TAG, "called : getMenuList()");

        final String GET_MENU_URL = BASE_URL + "/dashboard/search";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_MENU_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int code = jsonResponse.getInt("code");

                    if (code != 1) {
                        Toast.makeText(getContext(), "code + "+code, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONArray itemlist = jsonResponse.getJSONArray("menu");

                    completeMenuList = new ArrayList<>();
                    final MenuDb appDb = MenuDb.getInstance(getContext());

                    for(int i = 0; i < itemlist.length(); i++) {

                        JSONObject jo = itemlist.getJSONObject(i);

                        String id = jo.getString("id");
                        String name = jo.getString("name");
                        String rate = jo.getString("rate");
                        String veg = jo.getString("veg");
                        String unit = jo.getString("unit");
                        String gst_per = jo.getString("gst_per");
                        String ttype_tag = jo.getString("ttype_tag");
                        String geo_tag = jo.getString("geo_tag");
                        String time_created = jo.getString("time_created");
                        String status = jo.getString("status");

                        if(veg=="1")
                            veg = "veg";
                        else if(veg=="0")
                            veg = "non-veg";

                        final CompleteMenuTable x = new CompleteMenuTable(name,id,unit,rate,gst_per,ttype_tag,geo_tag,veg,time_created,status);

                        final int finalI = i;
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                appDb.completeMenuTableDao().insert(x);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e(TAG, "inserted "+ finalI);
                                    }
                                });
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "getMenuList : Exception caught  " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(getContext(), "Error in getMenuList() !", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                String ACCESS_TOKEN = sharedPreferences.getString(EMP_TOKEN,"");
                String mid = sharedPreferences.getString(EMP_ID,"");
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("x-access-token", ACCESS_TOKEN);
                params.put("name", "");
                params.put("merchant_id", mid);

                Log.e("x-access-token", "It is = "+ACCESS_TOKEN);
                Log.e("merchant_id", "it is = "+mid);;

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(RETRY_SECONDS, NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings :
            {
                Fragment myFragment = new waitlistFrag();
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, myFragment).commit();
                Log.e("clicked","yesss");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void filterProductArray(String newText) {
        String pName;
        filteredProductResults.clear();
        for (int i = 0; i < productResults.size(); i++) {
            pName = productResults.get(i).getmItemName().toLowerCase();
            if (pName.contains(newText.toLowerCase())) {
                filteredProductResults.add(productResults.get(i));
            }
        }
    }
}
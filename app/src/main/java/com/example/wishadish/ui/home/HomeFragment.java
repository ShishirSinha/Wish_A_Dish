package com.example.wishadish.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishadish.MenuItemClass;
import com.example.wishadish.MenuItemAdapter;
import com.example.wishadish.R;
import com.example.wishadish.TableAdapter;
import com.example.wishadish.TableInfoClass;
import com.example.wishadish.ui.OrderOverview.OrderOverviewActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.wishadish.ui.Settings.SettingsFragment.SETTINGS_PREF;
import static com.example.wishadish.ui.Settings.SettingsFragment.TABLE_MODE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter1;
    private List<MenuItemClass> menuItems;
    private LinearLayout menuModeLL;
    private RelativeLayout totalAmountRL;
    private SearchView searchView;

    private List<TableInfoClass> tableList;
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    private LinearLayout tableModeLL;
    private Button addTableBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean(TABLE_MODE, false);

        Log.e(TAG, "table mode = " + b);

        if (b) {

            tableModeLL = root.findViewById(R.id.homeTableTypeLL);
            menuModeLL = root.findViewById(R.id.homeMenuTypeLL);
            addTableBtn = root.findViewById(R.id.addTableButton);

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

            addTableBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment newFragment = new AddNewTableFrag();
                    getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, newFragment).commit();
                }
            });
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

            searchView = root.findViewById(R.id.searchView);
            totalAmountRL = root.findViewById(R.id.totalAmountBtnRL);
            recyclerView1 = root.findViewById(R.id.rv1);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

            MenuItemClass mi1 = new MenuItemClass("Paneer Butter Masala", 0, "Veg", 320);
            MenuItemClass mi2 = new MenuItemClass("Butter Naan", 1, "Chapati", 400);
            menuItems.add(mi1);
            menuItems.add(mi2);

            adapter1 = new MenuItemAdapter(menuItems, getActivity().getApplicationContext());
            recyclerView1.setAdapter(adapter1);

            totalAmountRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), OrderOverviewActivity.class);
                    startActivity(intent);
                }
            });

            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setIconified(false);
                }
            });
        }
        return root;
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
}
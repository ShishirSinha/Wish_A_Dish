package com.example.wishadish.ui.Settings;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.wishadish.R;

public class SettingsFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private Switch tableModeSwitch,serviceChargeSwitch,happyHoursSwitch,kotSwitch,codelessModeSwitch,gstInclusiveSwitch;
    private static TextView printerNameTV;

    SharedPreferences pref;
    private final String TAG = "LoginSessionManager";
    public static final String PREF_NAME   = "LoginPreference";
    public static final String TABLE_MODE = "tableMode";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        setHasOptionsMenu(true);

        tableModeSwitch = root.findViewById(R.id.tableModeSwitch);
        serviceChargeSwitch = root.findViewById(R.id.serviceChargeSwitch);
        happyHoursSwitch = root.findViewById(R.id.happyHoursSwitch);
        kotSwitch = root.findViewById(R.id.kotSwitch);
        codelessModeSwitch = root.findViewById(R.id.codelessModeSwitch);
        gstInclusiveSwitch = root.findViewById(R.id.gstInclusiveSwitch);
        printerNameTV = root.findViewById(R.id.printerNameTV);

        pref = getActivity().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

        tableModeSwitch.setChecked(pref.getBoolean(TABLE_MODE, false));

        tableModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(TABLE_MODE,true);
                    editor.commit();
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
}
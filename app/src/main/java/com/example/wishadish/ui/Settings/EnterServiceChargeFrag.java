package com.example.wishadish.ui.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wishadish.R;

import static com.example.wishadish.ui.Settings.SettingsFragment.SERVICE_CHARGE;
import static com.example.wishadish.ui.Settings.SettingsFragment.SETTINGS_PREF;

public class EnterServiceChargeFrag extends Fragment {

    private EditText ServiceChargeET;
    private Button ConfirmBtn;
    private String serviceCharge;

    private String mCustNum,mCustName,mTableFor,mWaitTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.enter_service_charge_frag, container, false);

        ServiceChargeET = view.findViewById(R.id.serviceChargeET);
        ConfirmBtn = view.findViewById(R.id.confirmServiceChargeBtn);

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCharge = ServiceChargeET.getText().toString().trim();

                SharedPreferences pref = getActivity().getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putFloat(SERVICE_CHARGE, 0);
                editor.apply();

                Toast.makeText(getContext(), "Service Charge successfully entered!",Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().remove(EnterServiceChargeFrag.this).commit();
            }
        });

        return view;
    }
}

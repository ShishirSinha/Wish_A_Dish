package com.example.wishadish.ui.Settings;

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

        serviceCharge = ServiceChargeET.getText().toString().trim();

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Service Charge successfully entered!",Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().remove(EnterServiceChargeFrag.this).commit();
            }
        });

        return view;
    }
}

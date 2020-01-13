package com.example.wishadish.ui.home;

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

public class waitlistFrag extends Fragment {

    private EditText CustNumET, CustNameET, TableForET, WaitTimeET;
    private Button ConfirmBtn;

    private String mCustNum,mCustName,mTableFor,mWaitTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.waitlist_frag, container, false);

        CustNameET = view.findViewById(R.id.custNameET);
        CustNumET  = view.findViewById(R.id.custNumberET);
        TableForET = view.findViewById(R.id.tableForET);
        WaitTimeET = view.findViewById(R.id.waitingTimeET);
        ConfirmBtn = view.findViewById(R.id.confirmBtn);

        mCustName = CustNameET.getText().toString().trim();
        mCustNum  = CustNumET.getText().toString().trim();
        mTableFor = TableForET.getText().toString().trim();
        mWaitTime = WaitTimeET.getText().toString().trim();

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Your details have been entered!",Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().remove(waitlistFrag.this).commit();
            }
        });

        return view;
    }
}

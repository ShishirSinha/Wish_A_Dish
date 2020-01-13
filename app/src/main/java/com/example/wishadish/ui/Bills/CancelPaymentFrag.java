package com.example.wishadish.ui.Bills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wishadish.R;

import java.util.Objects;

public class CancelPaymentFrag extends Fragment {

    private EditText reasonCancelET,refernceCancelET;
    private Button confirmBtn;
    private LinearLayout reasonCancelLL,refernceCancelLL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cancel_payment_frag, container, false);

        reasonCancelET = view.findViewById(R.id.cancelReasonET);
        reasonCancelLL = view.findViewById(R.id.reasonCancelLL);
        refernceCancelET = view.findViewById(R.id.cancelReferenceET);
        refernceCancelLL = view.findViewById(R.id.referenceCancelLL);
        confirmBtn  = view.findViewById(R.id.CancelConfirmBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(CancelPaymentFrag.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar().show();
    }
}

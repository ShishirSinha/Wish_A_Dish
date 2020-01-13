package com.example.wishadish.ui.Bills;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wishadish.R;

import java.util.Objects;

public class UpdatePaymentFrag extends Fragment {

    private EditText amount1,amount2,amount3,amount4,amount5;
    private TextView totalAmountTv;
    private ImageView addMethodIv;
    private Button confirmBtn;
    private Spinner paymentMethodSpinner1, paymentMethodSpinner2, paymentMethodSpinner3, paymentMethodSpinner4, paymentMethodSpinner5;
    private LinearLayout payMethll1, getPayMethll2, getPayMethll3, getPayMethll4, getPayMethll5;

    private int methodNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.update_payment_frag, container, false);

        totalAmountTv = view.findViewById(R.id.paymentAmountTv);
        paymentMethodSpinner1 = view.findViewById(R.id.paymentMethodSpinner1);
        paymentMethodSpinner2 = view.findViewById(R.id.paymentMethodSpinner2);
        paymentMethodSpinner3 = view.findViewById(R.id.paymentMethodSpinner3);
        paymentMethodSpinner4 = view.findViewById(R.id.paymentMethodSpinner4);
        paymentMethodSpinner5 = view.findViewById(R.id.paymentMethodSpinner5);
        amount1 = view.findViewById(R.id.amountEt1);
        amount2 = view.findViewById(R.id.amountEt2);
        amount3 = view.findViewById(R.id.amountEt3);
        amount4 = view.findViewById(R.id.amountEt4);
        amount5 = view.findViewById(R.id.amountEt5);
        getPayMethll2 = view.findViewById(R.id.payMetLL2);
        getPayMethll3 = view.findViewById(R.id.payMetLL3);
        getPayMethll4 = view.findViewById(R.id.payMetLL4);
        getPayMethll5 = view.findViewById(R.id.payMetLL5);
        addMethodIv = view.findViewById(R.id.addMethodIv);
        confirmBtn  = view.findViewById(R.id.confirmPaymentBtn);

        methodNumber = 1;

        addMethodIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Mnumber", methodNumber+"");
                if(methodNumber == 1){
                    getPayMethll2.setVisibility(View.VISIBLE);
                    methodNumber++;
                }
                else if(methodNumber == 2){
                    getPayMethll3.setVisibility(View.VISIBLE);
                    methodNumber++;
                }
                else if(methodNumber == 3){
                    getPayMethll4.setVisibility(View.VISIBLE);
                    methodNumber++;
                }
                else if(methodNumber == 4){
                    getPayMethll5.setVisibility(View.VISIBLE);
                    methodNumber++;
                    addMethodIv.setVisibility(View.GONE);
                }
            }
        });


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(UpdatePaymentFrag.this).commit();
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

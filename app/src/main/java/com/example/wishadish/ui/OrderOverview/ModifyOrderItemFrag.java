package com.example.wishadish.ui.OrderOverview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wishadish.R;

import java.util.Objects;

public class ModifyOrderItemFrag extends Fragment {

    private TextView itemNameTV, itemRateTV, itemQuantityTV;
    private Button ConfirmBtn;
    private ImageView itemRemoveBtn, itemAddBtn;

    private String mCustNum,mCustName,mTableFor,mWaitTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.modify_order_item_frag, container, false);

        itemNameTV = view.findViewById(R.id.modifyItemNameTV);
        itemRateTV  = view.findViewById(R.id.modifyItemRateTV);
        itemQuantityTV = view.findViewById(R.id.modifyItemQuantityTV);
        itemRemoveBtn = view.findViewById(R.id.modifyItemRemoveIV);
        itemAddBtn = view.findViewById(R.id.modifyItemAddTV);
        ConfirmBtn = view.findViewById(R.id.modifyItemConfirmBtn);

        itemRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(""+itemQuantityTV.getText());
                if(q > 1)
                    q = q - 1;
                itemQuantityTV.setText(""+q);
            }
        });

        itemAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(""+itemQuantityTV.getText());
                q = q + 1;
                itemQuantityTV.setText(""+q);
            }
        });

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(ModifyOrderItemFrag.this).commit();
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

package com.example.wishadish.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wishadish.MainActivity;
import com.example.wishadish.R;

import java.util.ArrayList;
import java.util.Objects;

public class ConfirmOrderFrag extends Fragment {

    private ListView listView;
    private TextView totalAmountTv;
    private Button confirmBtn;

    ArrayList<String> arrayList;
    private String mCustNum,mCustName,mTableFor,mWaitTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.confirm_order_frag, container, false);

        totalAmountTv = view.findViewById(R.id.totalAmountConfirmTV);
        confirmBtn  = view.findViewById(R.id.confirmOrderBtn);
        listView = view.findViewById(R.id.itemsListview);

        arrayList = new ArrayList<>();

        arrayList.add("Paneer Butter Masala   x 1");
        arrayList.add("Butter Naan   x 1");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent launchNextActivity;
                launchNextActivity = new Intent(getContext(), MainActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
                getActivity().finish();
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

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wishadish.R;

import java.util.Objects;

public class AddNewTableFrag extends Fragment {

    private EditText tableNumberET, tableSizeET;
    private Button ConfirmBtn;
    private String tableNumber, tableSize;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_table_frag, container, false);

        tableNumberET = view.findViewById(R.id.tableNumberET);
        tableSizeET = view.findViewById(R.id.tableSizeET);
        ConfirmBtn = view.findViewById(R.id.confirmAddTableBtn);

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableNumber = tableNumberET.getText().toString().trim();
                tableSize = tableSizeET.getText().toString().trim();

                if(!tableNumber.equals("") && !tableSize.equals("")) {
                    Toast.makeText(getContext(), "Table added successfully!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().remove(AddNewTableFrag.this).commit();
                }
                else{
                    Toast.makeText(getContext(), "Please enter valid values!", Toast.LENGTH_SHORT).show();
                }
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

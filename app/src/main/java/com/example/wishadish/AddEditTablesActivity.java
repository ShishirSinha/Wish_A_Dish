package com.example.wishadish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.wishadish.ui.home.AddNewTableFrag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddEditTablesActivity extends AppCompatActivity {

    private List<TableInfoClass> tableList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Button addTableBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_tables);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        addTableBtn = findViewById(R.id.addTableButton);

        tableList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.rv8);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddEditTablesActivity.this));

        TableInfoClass mi1 = new TableInfoClass(7, 4, true);
        TableInfoClass mi2 = new TableInfoClass(10, 6, true);
        tableList.add(mi1);
        tableList.add(mi2);

        adapter = new TableAdapter(tableList,this);
        recyclerView.setAdapter(adapter);

        addTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new AddNewTableFrag();
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, newFragment).commit();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

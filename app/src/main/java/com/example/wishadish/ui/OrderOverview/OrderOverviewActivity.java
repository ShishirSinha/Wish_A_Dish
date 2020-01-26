package com.example.wishadish.ui.OrderOverview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.wishadish.MainActivity;
import com.example.wishadish.MenuItemClass;
import com.example.wishadish.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderOverviewActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MenuItemClass> menuItems;
    private RelativeLayout menuModeRL;
    private Button addNewItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_overview);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        menuItems = new ArrayList<>();

        addNewItemBtn = findViewById(R.id.addNewItemBtn);
        menuModeRL = findViewById(R.id.rl2);
        recyclerView = findViewById(R.id.rv7);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuItems = (List<MenuItemClass>) getIntent().getSerializableExtra("list");

        adapter = new OrderOverviewAdapter(menuItems,this);
        recyclerView.setAdapter(adapter);

        addNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderOverviewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menuModeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myFragment = new ConfirmOrderFrag();
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, myFragment).commit();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

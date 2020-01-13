package com.example.wishadish.ui.Bills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishadish.AllOrderAdapter;
import com.example.wishadish.AllOrderClass;
import com.example.wishadish.R;

import java.util.ArrayList;
import java.util.List;

public class TotalBillsFragment extends Fragment {

    private BillsViewModel toolsViewModel;
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AllOrderClass> allOrderList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(BillsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        setHasOptionsMenu(true);

//        final TextView textView = root.findViewById(R.id.text_tools);
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        allOrderList = new ArrayList<>();

        recyclerView =(RecyclerView) root.findViewById(R.id.rv6);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AllOrderClass ao1 = new AllOrderClass("1",720,"08-01-2020","02:03:20","2");
        allOrderList.add(ao1);

        adapter = new AllOrderAdapter(allOrderList,getActivity().getApplicationContext(), getActivity());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
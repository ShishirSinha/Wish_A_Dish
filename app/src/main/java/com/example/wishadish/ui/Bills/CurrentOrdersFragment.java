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

import com.example.wishadish.CurrentOrderAdapter;
import com.example.wishadish.CurrentOrderClass;
import com.example.wishadish.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentOrdersFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CurrentOrderClass> currentOrderClassList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        setHasOptionsMenu(true);

//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        currentOrderClassList = new ArrayList<>();

        recyclerView =(RecyclerView) root.findViewById(R.id.rv5);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CurrentOrderClass co1 = new CurrentOrderClass("1",720,"08-01-2020",false);
        CurrentOrderClass co2 = new CurrentOrderClass("2",635,"08-01-2020",true);
        currentOrderClassList.add(co1);
        currentOrderClassList.add(co2);

        adapter = new CurrentOrderAdapter(currentOrderClassList,getActivity().getApplicationContext(), getActivity());
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
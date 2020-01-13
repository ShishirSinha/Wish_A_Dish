package com.example.wishadish.ui.Waitlist;

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

import com.example.wishadish.R;
import com.example.wishadish.WaitListAdapter;
import com.example.wishadish.WaitListClass;

import java.util.ArrayList;
import java.util.List;

public class WaitlistFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<WaitListClass> waitList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        setHasOptionsMenu(true);

//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        waitList = new ArrayList<>();

        recyclerView =(RecyclerView) root.findViewById(R.id.rv2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        WaitListClass wl0 = new WaitListClass("Rishabh Raj","9876543210",6,10);
        WaitListClass wl1 = new WaitListClass("Shishir Sinha","7050656055",9,20);
        WaitListClass wl2 = new WaitListClass("Shubham Chowdhuri","9876543210",4,90);
        waitList.add(wl0);
        waitList.add(wl1);
        waitList.add(wl2);

        adapter = new WaitListAdapter(waitList,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
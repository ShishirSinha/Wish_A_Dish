package com.example.wishadish.ui.HumanResources;

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

import com.example.wishadish.EmployeeAdapter;
import com.example.wishadish.EmployeeClass;
import com.example.wishadish.R;

import java.util.ArrayList;
import java.util.List;

public class ViewEmployeesFragment extends Fragment {

    private SendViewModel sendViewModel;
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<EmployeeClass> empList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        setHasOptionsMenu(true);

//        final TextView textView = root.findViewById(R.id.text_send);
//        sendViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        empList = new ArrayList<>();

        recyclerView =(RecyclerView) root.findViewById(R.id.rv3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EmployeeClass wl1 = new EmployeeClass("Rishabh Raj","STAFF","rishabhraj@gmail.com", "9876543210","30000","Chiramugath House, University P.O., CUSAT, Kochi, Kerala-682022");
        EmployeeClass wl2 = new EmployeeClass("Shishir Sinha","CHEF","shishir.mangal@gmail.com","7050656055","50000","Chiramugath House, University P.O., CUSAT, Kochi, Kerala-682022");
        EmployeeClass wl3 = new EmployeeClass("Shubham Chowdhuri","STAFF","shubham2506@gmail.com","9876543210","40000","Chiramugath House, University P.O., CUSAT, Kochi, Kerala-682022");
        empList.add(wl1);
        empList.add(wl2);
        empList.add(wl3);

        adapter = new EmployeeAdapter(empList,getActivity().getApplicationContext(), getActivity());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
package com.example.wishadish;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WaitListAdapter extends RecyclerView.Adapter<WaitListAdapter.ViewHolder> {

    private List<WaitListClass> mWaitingPersons;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cusNameTv;
        public TextView cusNumTv;
        public TextView tableForTv;
        public TextView waitTimeTv;
        public ImageButton tickBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cusNameTv = itemView.findViewById(R.id.custNameTV);
            cusNumTv = itemView.findViewById(R.id.custNumberTV);
            tableForTv = itemView.findViewById(R.id.tableForTV);
            waitTimeTv = itemView.findViewById(R.id.waitingTimeTV);
            tickBtn = itemView.findViewById(R.id.tickBtn);
        }
    }

    public WaitListAdapter(List<WaitListClass> mWaitingPersons, Context context) {
        this.mWaitingPersons = mWaitingPersons;
        this.context = context;
        Log.e("constructor","WaitList haha");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.waiting_person_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cusNameTv.setText(mWaitingPersons.get(position).getmCustName());
        holder.cusNumTv.setText(mWaitingPersons.get(position).getmCustNumber());
        holder.tableForTv.setText("Table For :  "+ mWaitingPersons.get(position).getmTableFor());
        holder.waitTimeTv.setText(""+ mWaitingPersons.get(position).getmWaitTimeMinutes()+" minutes");
    }

    @Override
    public int getItemCount() {
        return mWaitingPersons.size();
    }
}

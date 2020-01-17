package com.example.wishadish;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private List<TableInfoClass> mTableList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tableNumberTv;
        public TextView tableSizeTv;
        public Switch tableOnlineSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tableNumberTv = itemView.findViewById(R.id.tableNumberTv);
            tableSizeTv = itemView.findViewById(R.id.tableSizeTv);
            tableOnlineSwitch = itemView.findViewById(R.id.tableOnlineSwitch);
        }
    }

    public TableAdapter(List<TableInfoClass> mTableList, Context context) {
        this.mTableList = mTableList;
        this.context = context;
        Log.e("constructor table adap","haha");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_info, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tableNumberTv.setText("Table No. #"+mTableList.get(position).getmTableNo());
        holder.tableSizeTv.setText(""+ mTableList.get(position).getmTableSize()+" people");
        holder.tableOnlineSwitch.setChecked(mTableList.get(position).ismTableOnline());
    }

    @Override
    public int getItemCount() {
        return mTableList.size();
    }
}

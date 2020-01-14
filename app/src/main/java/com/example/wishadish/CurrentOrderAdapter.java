package com.example.wishadish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishadish.ui.OrderOverview.OrderOverviewActivity;

import java.util.List;

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.ViewHolder> {

    private List<CurrentOrderClass> mCurrentOrderList;
    private Context context;
    private Activity act;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView currentOrderNumTv;
        public TextView currentOrderAmountTv;
        public TextView currentOrderDateTv;
        public Button currentOrderEditBtn;
        public Button currentOrderCancelBtn;
        public Button currentOrderCancelledTvBtn;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            currentOrderNumTv = itemView.findViewById(R.id.currentOrderNumTv);
            currentOrderAmountTv = itemView.findViewById(R.id.currentOrderAmountTv);
            currentOrderDateTv = itemView.findViewById(R.id.currentOrderDateTv);
            currentOrderEditBtn = itemView.findViewById(R.id.currentOrderEditBtn);
            currentOrderCancelBtn = itemView.findViewById(R.id.currentOrderCancelBtn);
            currentOrderCancelledTvBtn = itemView.findViewById(R.id.currentOrderCancelledTv);

            currentOrderEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), OrderOverviewActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public CurrentOrderAdapter(List<CurrentOrderClass> mCurrentOrderList, Context context, Activity act) {
        this.mCurrentOrderList = mCurrentOrderList;
        this.context = context;
        this.act = act;
        Log.e("constructor","CurrentOrder haha");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_order_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.currentOrderNumTv.setText("Order #"+mCurrentOrderList.get(position).getmOrdernumber());
        holder.currentOrderAmountTv.setText("₹"+mCurrentOrderList.get(position).getmOrderAmount());
        holder.currentOrderDateTv.setText(mCurrentOrderList.get(position).getmDate());

        if(mCurrentOrderList.get(position).ismOrderCancelled()){
            holder.currentOrderCancelledTvBtn.setVisibility(View.VISIBLE);
            holder.currentOrderEditBtn.setVisibility(View.GONE);
            holder.currentOrderCancelBtn.setVisibility(View.GONE);
        } else {
            holder.currentOrderCancelledTvBtn.setVisibility(View.GONE);
            holder.currentOrderEditBtn.setVisibility(View.VISIBLE);
            holder.currentOrderCancelBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mCurrentOrderList.size();
    }
}

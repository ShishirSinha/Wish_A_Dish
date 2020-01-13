package com.example.wishadish.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishadish.MenuItemClass;
import com.example.wishadish.R;

import java.util.List;

public class OrderOverviewAdapter extends RecyclerView.Adapter<OrderOverviewAdapter.ViewHolder> {

    private List<MenuItemClass> mMenuItems;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemnameTv;
        public TextView quantityTv;
        public TextView rateTv;
        public TextView typeTv;
        public RelativeLayout removeBtn;
        public LinearLayout openModifyItemFragBtn;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemnameTv = itemView.findViewById(R.id.orderOverviewItemNameTv);
            quantityTv = itemView.findViewById(R.id.orderOverviewItemQuantityTv);
            rateTv = itemView.findViewById(R.id.orderOverviewItemRateTv);
            typeTv = itemView.findViewById(R.id.orderOverviewItemTypeTv);
            removeBtn = itemView.findViewById(R.id.rl1);
            openModifyItemFragBtn = itemView.findViewById(R.id.orderModifyLL);

            openModifyItemFragBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context=v.getContext();
//                    Fragment newFragment = new ModifyOrderItemFrag();
//                    context.getFra().beginTransaction().add(android.R.id.content, newFragment).commit();

                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    Fragment myFragment = new ModifyOrderItemFrag();
                    activity.getSupportFragmentManager().beginTransaction().add(android.R.id.content, myFragment).commit();
                }
            });

        }
    }

    public OrderOverviewAdapter(List<MenuItemClass> mMenuItems, Context context) {
        this.mMenuItems = mMenuItems;
        this.context = context;
        Log.e("constructor","haha");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_overview_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemnameTv.setText(mMenuItems.get(position).getmItemName());
        holder.rateTv.setText("Rate :   ₹"+mMenuItems.get(position).getmCost());
        holder.typeTv.setText(mMenuItems.get(position).getType());
        holder.quantityTv.setText("Quantity :   "+mMenuItems.get(position).getmQuantity());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }
}

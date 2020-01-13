package com.example.wishadish;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    private List<MenuItemClass> mMenuItems;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemname;
        public TextView quantity;
        public ImageButton addBtn;
        public ImageButton decBtn;
        public ImageButton settingBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            addBtn = itemView.findViewById(R.id.addbtn);
            decBtn = itemView.findViewById(R.id.removebtn);
            settingBtn = itemView.findViewById(R.id.settingsbtn);
            quantity = itemView.findViewById(R.id.quantitytv);
        }
    }

    public MenuItemAdapter(List<MenuItemClass> mMenuItems, Context context) {
        this.mMenuItems = mMenuItems;
        this.context = context;
        Log.e("constructor","haha");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemname.setText(mMenuItems.get(position).getmItemName());
        holder.quantity.setText(""+mMenuItems.get(position).getmQuantity());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }
}

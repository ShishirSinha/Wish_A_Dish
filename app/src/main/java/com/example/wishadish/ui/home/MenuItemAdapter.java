package com.example.wishadish.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishadish.MenuItemClass;
import com.example.wishadish.R;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    private List<MenuItemClass> mMenuItems;
    private Context context;
    private double totalAmount;
    private LinearLayout totalAmountLL;
    private TextView totalAmountTv;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemname;
        public TextView quantity;
        public ImageButton addBtn;
        public ImageButton decBtn;
        public ImageButton settingBtn;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            addBtn = itemView.findViewById(R.id.addbtn);
            decBtn = itemView.findViewById(R.id.removebtn);
            settingBtn = itemView.findViewById(R.id.settingsbtn);
            quantity = itemView.findViewById(R.id.quantitytv);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMenuItems.get(getAdapterPosition()).setmQuantity(mMenuItems.get(getAdapterPosition()).getmQuantity()+1);
                    quantity.setText(""+mMenuItems.get(getAdapterPosition()).getmQuantity());
                    totalAmountLL.setVisibility(View.VISIBLE);
                    totalAmount = totalAmount + mMenuItems.get(getAdapterPosition()).getmCost();
                    totalAmountTv.setText("₹ "+totalAmount);
                }
            });

            decBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuItems.get(getAdapterPosition()).getmQuantity() > 1) {
                        mMenuItems.get(getAdapterPosition()).setmQuantity(mMenuItems.get(getAdapterPosition()).getmQuantity() - 1);
                        quantity.setText("" + mMenuItems.get(getAdapterPosition()).getmQuantity());
                        totalAmount = totalAmount - mMenuItems.get(getAdapterPosition()).getmCost();
                        totalAmountLL.setVisibility(View.VISIBLE);
                        totalAmountTv.setText("₹ "+totalAmount);
                    }
                    else {
                        totalAmount = totalAmount - mMenuItems.get(getAdapterPosition()).getmCost();
                        totalAmountTv.setText("₹ "+totalAmount);
                        removeAt(getAdapterPosition());
                        Toast.makeText(context, "Item removed from the list!", Toast.LENGTH_SHORT).show();
                    }
                    if(getItemCount()==0){
                        totalAmount = 0;
                        totalAmountLL.setVisibility(View.GONE);
                    }

                }
            });
        }
    }

    public MenuItemAdapter(List<MenuItemClass> mMenuItems, Context context, LinearLayout totalAmountLL, TextView totalAmountTv) {
        this.mMenuItems = mMenuItems;
        this.context = context;
        this.totalAmountLL = totalAmountLL;
        this.totalAmountTv = totalAmountTv;
        Log.e("constructor","haha");
    }

    public void addItem(MenuItemClass menuItemClassObject) {

        int f=0;
        for(int i=0;i<mMenuItems.size();i++){
            if(mMenuItems.get(i).getmItemName().equalsIgnoreCase(menuItemClassObject.getmItemName())){
                f = 1;
                break;
            }
        }
        if (f==0) {
            mMenuItems.add(menuItemClassObject);
            notifyDataSetChanged();
            totalAmountLL.setVisibility(View.VISIBLE);
            totalAmount = totalAmount + menuItemClassObject.getmCost();
            totalAmountTv.setText("₹ "+totalAmount);
        }
        else
            Toast.makeText(context, "Item already added to the list!",Toast.LENGTH_SHORT).show();
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

        String itemName = mMenuItems.get(position).getmItemName();
        itemName = itemName.toLowerCase();
        String[] it = itemName.split(" ");
        itemName = "";
        for(int i=0;i<it.length;i++){
            if(!it[i].equals("")) {
                char c = it[i].charAt(0);
                if(c>='a' && c<='z') {
                    char d = Character.toUpperCase(c);
                    it[i] = it[i].replaceFirst("" + c, "" + d);
                }
                itemName = itemName + it[i] + " ";
            }
        }

        holder.itemname.setText(itemName.trim());
        holder.quantity.setText(""+mMenuItems.get(position).getmQuantity());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    public List getListItems() {
        return mMenuItems;
    }

    public void removeAt(int position) {
        mMenuItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mMenuItems.size());
    }
}

package com.example.wishadish.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wishadish.MenuItemClass;
import com.example.wishadish.R;

import java.util.ArrayList;
import java.util.List;

public  class SearchResultsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    private List<MenuItemClass> menuItemDetails;
    int count;
    Typeface type;
    Context context;

    public SearchResultsAdapter(Context context, List<MenuItemClass> menuItemDetails) {

        layoutInflater = LayoutInflater.from(context);
        this.menuItemDetails = new ArrayList<>();

        this.menuItemDetails = menuItemDetails;
        this.count= menuItemDetails.size();
        this.context = context;

    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return menuItemDetails.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final MenuItemClass tempProduct = menuItemDetails.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.searchview_menu_item, null);
            holder = new ViewHolder();
            holder.menuItemNameTv = (TextView) convertView.findViewById(R.id.searchResultItemNameTv);
            holder.menuItemRateTv = (TextView) convertView.findViewById(R.id.searchResultItemRateTv);
            holder.menuItemTypeIv = (ImageView) convertView.findViewById(R.id.searchResultItemTypeIv);
            holder.addMenuItemTv  = (TextView) convertView.findViewById(R.id.addItemFromSearchTv);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        String type = tempProduct.getType();
        Log.e("Search Adapter",type+"  "+tempProduct.getmItemName());
        if(type.equalsIgnoreCase("0"))
            holder.menuItemTypeIv.setImageResource(R.drawable.veg_icon);
        else
            holder.menuItemTypeIv.setImageResource(R.drawable.non_veg_icon);
        holder.menuItemNameTv.setText(tempProduct.getmItemName());
        holder.menuItemRateTv.setText("₹ "+((int) tempProduct.getmCost()));
        final int x = position;

        holder.addMenuItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+x+" "+tempProduct.getmItemName()+" added!",Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

    class ViewHolder {

        ImageView menuItemTypeIv;
        TextView menuItemNameTv;
        TextView menuItemRateTv;
        TextView addMenuItemTv;
    }

}

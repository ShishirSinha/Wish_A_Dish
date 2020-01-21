package com.example.wishadish.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.wishadish.MenuItemClass;
import com.example.wishadish.R;

import java.util.ArrayList;
import java.util.List;

public  class SearchResultsAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;

    private List<MenuItemClass> menuItemDetails= new ArrayList<>();
    int count;
    Typeface type;
    Context context;

    public SearchResultsAdapter(Context context, List<MenuItemClass> menuItemDetails) {

        layoutInflater = LayoutInflater.from(context);

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
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        MenuItemClass tempProduct = menuItemDetails.get(position);

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.searchview_menu_item, null);
            holder = new ViewHolder();
            holder.product_name = (TextView) convertView.findViewById(R.id.searchviewTv);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.product_name.setText(tempProduct.getmItemName());

        return convertView;
    }

    static class ViewHolder
    {
        TextView product_name;
    }

}

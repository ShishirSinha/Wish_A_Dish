package com.example.wishadish.ui.Bills;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishadish.AllOrderClass;
import com.example.wishadish.R;

import java.util.List;

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.ViewHolder> {

    private List<AllOrderClass> mAllOrderList;
    private Context context;
    private Activity act;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView allOrderTableNumTv;
        public TextView allOrderTimeTv;
        public TextView allOrderNumTv;
        public TextView allOrderAmountTv;
        public TextView allOrderDateTv;
        public Button allOrderUpdateBtn;
        public Button allOrderCancelBtn;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            allOrderTableNumTv = itemView.findViewById(R.id.allOrderTableNumberTv);
            allOrderDateTv = itemView.findViewById(R.id.allOrderDateTv);
            allOrderNumTv = itemView.findViewById(R.id.allOrderNumTv);
            allOrderAmountTv = itemView.findViewById(R.id.allOrderAmountTv);
            allOrderTimeTv = itemView.findViewById(R.id.allOrderTimeTv);
            allOrderUpdateBtn = itemView.findViewById(R.id.allOrderUpdateBtn);
            allOrderCancelBtn = itemView.findViewById(R.id.allOrderCancelBtn);

            allOrderUpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    Fragment myFragment = new UpdatePaymentFrag();
//                    activity.getSupportFragmentManager().beginTransaction().add(android.R.id.content, myFragment).commit();

                    FragmentManager mFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.add(R.id.flcontent, myFragment);
                    ft.commit();
                }
            });

            allOrderCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    Fragment myFragment = new CancelPaymentFrag();
//                    activity.getSupportFragmentManager().beginTransaction().add(android.R.id.content, myFragment).commit();

                    FragmentManager mFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.add(R.id.flcontent, myFragment);
                    ft.commit();
                }
            });
        }
    }

    public AllOrderAdapter(List<AllOrderClass> mAllOrderList, Context context, Activity act) {
        this.mAllOrderList = mAllOrderList;
        this.context = context;
        this.act = act;
        Log.e("constructor","allOrder haha");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_order_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.allOrderTableNumTv.setText("Table "+mAllOrderList.get(position).getmTableNumber());
        holder.allOrderDateTv.setText(mAllOrderList.get(position).getmDate());
        holder.allOrderNumTv.setText("Order #"+mAllOrderList.get(position).getmOrdernumber());
        holder.allOrderAmountTv.setText("₹"+mAllOrderList.get(position).getmOrderAmount());
        holder.allOrderTimeTv.setText(mAllOrderList.get(position).getmTime());
    }

    @Override
    public int getItemCount() {
        return mAllOrderList.size();
    }
}

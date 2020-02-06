package com.example.wishadish.ui.HumanResources;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wishadish.EmployeeClass;
import com.example.wishadish.R;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<EmployeeClass> mEmployeeList;
    private Context context;
    private Activity act;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView empNameTv;
        public TextView empTypeTv;
        public TextView empEmailTv;
        public TextView empMobTv;
        public TextView empSalTv;
        public TextView empAddTv;
        public ImageView empImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            empNameTv = itemView.findViewById(R.id.empNameTv);
            empTypeTv = itemView.findViewById(R.id.empTypeTv);
            empEmailTv = itemView.findViewById(R.id.empEmailTv);
            empMobTv = itemView.findViewById(R.id.empMobTv);
            empSalTv = itemView.findViewById(R.id.empSalTv);
            empAddTv = itemView.findViewById(R.id.empAddTv);
            empImage = itemView.findViewById(R.id.empIv);
        }
    }

    public EmployeeAdapter(List<EmployeeClass> mEmployeeList, Context context, Activity act) {
        this.mEmployeeList = mEmployeeList;
        this.context = context;
        this.act = act;
        Log.e("constructor","EmployeeList  haha");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.empNameTv.setText(mEmployeeList.get(position).getmEmployeeName());
        holder.empTypeTv.setText(mEmployeeList.get(position).getmEmployeeType());
        holder.empEmailTv.setText(mEmployeeList.get(position).getmEmployeeEmailId());
        holder.empMobTv.setText(mEmployeeList.get(position).getmEmployeeMob());
        holder.empAddTv.setText(mEmployeeList.get(position).getmEmployeeAddress());
        holder.empSalTv.setText("₹ "+mEmployeeList.get(position).getmEmployeeSalary());

        Glide.with(act)
                .load("https://cms.qz.com/wp-content/uploads/2019/10/pensive-simian-qz-01.png?w=690&h=828&crop=1&strip=all&quality=75")
                .placeholder(R.drawable.dp_placeholder)
                .transform(new RoundedCornersTransformation(10, 0))
                .centerCrop()
                .into(holder.empImage);
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }
}

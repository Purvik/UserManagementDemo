package com.student.usermanagementdemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.student.usermanagementdemo.R;
import com.student.usermanagementdemo.entities.GruhUdhyog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GruhUdhyogAdapter extends RecyclerView.Adapter<GruhUdhyogAdapter.MyViewHolder> {

    private List<GruhUdhyog> gruhUdhyogList;

    public GruhUdhyogAdapter(List<GruhUdhyog> list) {
        this.gruhUdhyogList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_gruh_udhyog, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GruhUdhyog gruhUdhyog = gruhUdhyogList.get(position);
        holder.bName.setText(gruhUdhyog.getName());
        holder.bContact.setText(gruhUdhyog.getContact());
        holder.bEmail.setText(gruhUdhyog.getEmail());
        holder.bAddress.setText(gruhUdhyog.getAddress());
    }

    @Override
    public int getItemCount() {
        return gruhUdhyogList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView businessIcon;
        public TextView bName, bContact, bEmail, bAddress;

        public MyViewHolder(View view) {
            super(view);
            businessIcon = view.findViewById(R.id.businessIcon);
            bName = view.findViewById(R.id.tvBusinessName);
            bContact = view.findViewById(R.id.tvBusinessContact);
            bEmail = view.findViewById(R.id.tvBusinessEmail);
            bAddress = view.findViewById(R.id.tvBusinessAddress);
        }
    }
}

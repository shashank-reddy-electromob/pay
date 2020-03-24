package com.nitish.paymatrixandroidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RentPaymentAdapter extends RecyclerView.Adapter<RentPaymentAdapter.ProgrammingViewHolder> {


    private ArrayList<HashMap> map;

    public RentPaymentAdapter(ArrayList<HashMap> map){
        this.map = map;
    }

    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rent_payment_list_item,parent,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder holder, int position) {

        HashMap<String,String> data;
        data=map.get(position);

        holder.t1.setText(data.get("property name"));
        holder.t2.setText(data.get("pincode"));
        holder.t3.setText(data.get("property address"));

    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3;
        public ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.property_name);
            t2 = itemView.findViewById(R.id.property_pincode);
            t3 = itemView.findViewById(R.id.property_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
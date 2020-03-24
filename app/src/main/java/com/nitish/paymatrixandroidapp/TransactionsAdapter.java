package com.nitish.paymatrixandroidapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ProgrammingViewHolder> {


    private ArrayList<HashMap> map;

    public TransactionsAdapter(ArrayList<HashMap> map){
        this.map = map;
    }

    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.transactions_list_item,parent,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder holder, int position) {

        HashMap<String,String> data;
        data=map.get(position);

        holder.t1.setText(data.get("Receipt ID"));
        holder.t2.setText(data.get("Date Paid"));
        holder.t3.setText(data.get("Transaction Type"));
        holder.t4.setText(data.get("Amount"));
        holder.t5.setText(data.get("Status"));
//        holder.t6.setText(data.get("UTR"));

    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3,t4,t5,t6;
        public ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.receipt_id);
            t2 = itemView.findViewById(R.id.date);
            t3 = itemView.findViewById(R.id.type);
            t4 = itemView.findViewById(R.id.amount);
            t5 = itemView.findViewById(R.id.status);
            //t6 = itemView.findViewById(R.id.utr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
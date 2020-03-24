package com.nitish.paymatrixandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashMap;


public class TransactionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView navigation;
    private TextView notify_user;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Slidr.attach(this);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final RecyclerView transactionsList = findViewById(R.id.transactionsList);

        transactionsList.setLayoutManager(new LinearLayoutManager(this));

        navigation = findViewById(R.id.navigation2);

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
            }
        });

        notify_user=findViewById(R.id.notify);
        progressBar = findViewById(R.id.progressBar);

        db.collection("" +
                "Transactions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ArrayList<HashMap> map=new ArrayList<>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                HashMap<String,String> ans= new HashMap<>();
                                ans.put("Transaction Type",document.get("Transaction Type").toString());
                                ans.put("Amount",document.get("Amount").toString());
                                ans.put("Status",document.get("Status").toString());
                                //ans.put("UTR",document.get("UTR").toString());
                                ans.put("Receipt ID",document.get("Receipt ID").toString());
                                String date[]=document.getTimestamp("Date Paid").toDate().toString().split(" ");
                                ans.put("Date Paid",date[2]+" "+date[1]+" "+date[5]);
                                //Log.d("Working", ans.toString());
                                map.add((HashMap) ans.clone());
                            }
                            transactionsList.setAdapter(new TransactionsAdapter(map));
                            progressBar.setVisibility(View.GONE);
                            if(map.size()==0)
                                notify_user.setVisibility(View.VISIBLE);
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void open(View v){
        TextView t=v.findViewById(R.id.receipt_id);
        String str= (String) t.getText();

        Intent i=new Intent(this,TranscationDetailsActivity.class);
        i.putExtra("transaction_id", str);
        startActivity(i);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goBack(View v){
        finish();
    }
}
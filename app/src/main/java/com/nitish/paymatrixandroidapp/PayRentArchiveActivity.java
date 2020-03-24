package com.nitish.paymatrixandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashMap;

public class PayRentArchiveActivity extends AppCompatActivity {

    private TextView notify_user;
    private ProgressBar progressBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_rent_archive);

        Slidr.attach(this);

        notify_user=findViewById(R.id.notify);
        progressBar = findViewById(R.id.progressBar);

        final RecyclerView archiveList = findViewById(R.id.archiveList);

        archiveList.setLayoutManager(new LinearLayoutManager(this));

        db.collection("" +
                "Archive List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ArrayList<HashMap> map=new ArrayList<>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                HashMap<String,String> property= new HashMap<>();

                                property.put("pincode",document.get("pincode").toString());
                                property.put("property address",document.get("property address").toString());
                                property.put("property name",document.get("property name").toString());

                                map.add((HashMap) property.clone());
                            }

                            archiveList.setAdapter(new ArchiveListAdapter(map));
                            progressBar.setVisibility(View.GONE);
                            if(map.size()==0)
                                notify_user.setVisibility(View.VISIBLE);
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void goBack(View v){
        finish();
    }
}

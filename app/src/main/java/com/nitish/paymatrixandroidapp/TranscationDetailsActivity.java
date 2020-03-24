package com.nitish.paymatrixandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.r0adkll.slidr.Slidr;

public class TranscationDetailsActivity extends AppCompatActivity {



    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView t1,t2,t3,t4,t5,t6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcation_details);


        Slidr.attach(this);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String id = extras.getString("transaction_id");


        t1 = findViewById(R.id.textView1);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        t4 = findViewById(R.id.textView5);
        t5 = findViewById(R.id.textView6);
        t6 = findViewById(R.id.textView7);

        db.collection("" +
                "Transactions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                if ((document.get("Receipt ID").toString()).equals(id)) {

                                    t2.setText(document.get("Transaction Type").toString());
                                    t3.setText(document.get("Amount").toString());
                                    t4.setText(document.get("Status").toString());
                                    t5.setText(document.get("UTR").toString());
                                    t6.setText(document.get("Receipt ID").toString());
                                    t1.setText(document.getTimestamp("Date Paid").toDate().toString());
                                    //Toast.makeText(getApplicationContext(),document.get("Receipt ID").toString(),Toast.LENGTH_SHORT).show();

                                    break;


                                }
                            }
                        } else {
                            //.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void goBack (View v){
        finish();
    }
}

package com.nitish.paymatrixandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.r0adkll.slidr.Slidr;

public class UserProfileActivity extends AppCompatActivity {

    private TextView t1,t2,t3;
    private String user_uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Slidr.attach(this);

        t1=findViewById(R.id.user_name);
        t2=findViewById(R.id.phone_number);
        t3=findViewById(R.id.email_id);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user_uid = FirebaseAuth.getInstance().getUid();
        }

        DocumentReference docRef = db.collection("Users").document(user_uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        t1.setText(document.get("Name").toString());
                        t2.setText(document.get("Phone Number").toString());
                        t3.setText(document.get("Email Address").toString());
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void KYC(View v){
        Toast.makeText(getApplicationContext(),"kyc pending",Toast.LENGTH_LONG).show();
    }
    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();
    }
    public void goBack(View v){
        finish();
    }
}

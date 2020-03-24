package com.nitish.paymatrixandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class NavigationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private LinearLayout payrent,rentagreement,transactions,profile,settings,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        payrent = findViewById(R.id.llpayrent);
        rentagreement = findViewById(R.id.llrentagreement);
        transactions = findViewById(R.id.lltransactions);
        profile = findViewById(R.id.llprofile);
        settings = findViewById(R.id.llsettings);
        logout = findViewById(R.id.lllogout);

        firebaseAuth = FirebaseAuth.getInstance();

        payrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PayRentActivity.class));
            }
        });

        rentagreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RentAgreementActivity.class));
            }
        });

        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TransactionsActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));


            }
        });

    }

    public void goBack(View v){
        finish();
    }
}

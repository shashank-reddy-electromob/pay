package com.nitish.paymatrixandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserDashboardActivity extends AppCompatActivity {

    private ViewPager userdashboardPager;
    private UserDashboardPagerAdapter userdashboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        userdashboardPager=findViewById(R.id.dashboard_pager);
        userdashboardAdapter=new UserDashboardPagerAdapter(getSupportFragmentManager(),0);
        userdashboardPager.setAdapter(userdashboardAdapter);
    }

    public void openProfile(View v){
        Intent i=new Intent(this, UserProfileActivity.class);
        startActivity(i);
    }

    public void payRent(View v){
        Intent i=new Intent(this,PayRentActivity.class);
        startActivity(i);

    }
    public void rentAgreements(View v){
        Intent i=new Intent(this, RentAgreementActivity.class);
        startActivity(i);
    }
    public void transactions(View v){
        Intent i=new Intent(this, TransactionsActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}

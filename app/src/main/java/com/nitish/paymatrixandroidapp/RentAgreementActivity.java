package com.nitish.paymatrixandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.r0adkll.slidr.Slidr;

public class RentAgreementActivity extends AppCompatActivity {

    Button upload,fill;
    String[] state = { "---No State Selected---","Telangana", "Andhra Pradesh", "Madhya pradesh", "Gujarat", "Kerala","Maharashtra","Jammu and Kashmir","Tamil nadu","Bihar","Karnataka","Rajasthan","Assam","Goa","West Bengal",
            "odisha","Haryana","Jharkhand","Manipur","Nagaland","Chhattisgarh","Uttarakhand","Himachal Pradesh","Sikkim","Tripura","Arunachal Pradesh","Mizoram","Megalaya"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_agreement);

        Slidr.attach(this);

        upload = findViewById(R.id.uploadfile);


        Spinner spin = findViewById(R.id.spinner123);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UploadFileActivity.class));
            }
        });
    }



    public void navigationDrawer1(View v){
        Intent i=new Intent(this,NavigationActivity.class);
        startActivity(i);
    }

    public void goBack(View v){
        finish();
    }
}

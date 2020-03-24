package com.nitish.paymatrixandroidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

public class AddPropertyActivity extends AppCompatActivity {

    private TextView back;
    private Button save_property;
    private ImageView image;
    private FirebaseStorage firebaseStorage;
    private static final int PICK_IMAGE = 123;
    private ProgressDialog progressDialog;
    private Uri imagepath;
    private StorageReference mstorageref;
    private StorageTask muploadtask;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {


            //Toast.makeText(aadhar.this,"test2",Toast.LENGTH_SHORT).show();

            imagepath = data.getData();

            Picasso.get().load(imagepath).into(image);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        Slidr.attach(this);

        back = findViewById(R.id.back);
        save_property = findViewById(R.id.property_add);

        firebaseStorage = FirebaseStorage.getInstance();
        image = findViewById(R.id.property_image);

        mstorageref = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        final StorageReference storageReference = firebaseStorage.getReference("users");
        StorageReference reff = storageReference.child(firebaseAuth.getUid()).child("Property image");


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(AddPropertyActivity.this, "Select from Camera roll: ", Toast.LENGTH_SHORT).show();
                if (muploadtask != null && muploadtask.isInProgress()) {
                    Toast.makeText(AddPropertyActivity.this, "Upload in progress ...", Toast.LENGTH_SHORT).show();

                } else {

                    openfile();

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPropertyActivity.this,PayRentActivity.class));
            }
        });
    }

    private void openfile() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE);
        save_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (imagepath != null ){
                    progressDialog.setMessage("Relax a bit we are uploading your pic ...");
                    progressDialog.show();
                    StorageReference fileReference = mstorageref.child(firebaseAuth.getUid()).child("Property image");

                    muploadtask = fileReference.putFile(imagepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddPropertyActivity.this,"Process success",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddPropertyActivity.this,"Process failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(AddPropertyActivity.this,"IMAGE NOT SELECTED: ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void goBack(View v){
        finish();
    }
}
package com.nitish.paymatrixandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PayRentActivity extends AppCompatActivity {

    private ImageView image;
    private TextView text;
    private Button addproperty;
    private ImageView navigation;

    private TextView notify_user;
    private ProgressBar progressBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_rent);

        Slidr.attach(this);

        image = findViewById(R.id.imageView);
        addproperty = findViewById(R.id.button);
        navigation = findViewById(R.id.navigation);

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayRentActivity.this,PayRentArchiveActivity.class));
            }
        });

        addproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayRentActivity.this, AddPropertyActivity.class));
            }
        });

        notify_user=findViewById(R.id.notify);
        progressBar = findViewById(R.id.progressBar);

        final RecyclerView rentPaidList = findViewById(R.id.rentPaymentList);
        rentPaidList.setLayoutManager(new LinearLayoutManager(this));

        db.collection("" + "ADD PROPERTY").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            ArrayList<HashMap> map=new ArrayList<>();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                HashMap<String,String> property= new HashMap<>();

                                property.put("pincode",document.get("pincode").toString());
                                property.put("property address",document.get("property address").toString());
                                property.put("property name",document.get("property name").toString());

                                map.add((HashMap) property.clone());
                            }
                            rentPaidList.setAdapter(new RentPaymentAdapter(map));
                            progressBar.setVisibility(View.GONE);
                            if(map.size()==0)
                                notify_user.setVisibility(View.VISIBLE);
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void openArchiveList(View v){
        Intent i=new Intent(this,PayRentArchiveActivity.class);
        startActivity(i);
    }

//    public void archiveProperty(View v){
//        final View w= (View) v.getParent();
//        TextView t = w.findViewById(R.id.property_name);
//        String str = (String) t.getText();
//
//        db.collection("ADD PROPERTY")
//                .whereEqualTo("property name", str)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    Map<String, Object> property = new HashMap<>();
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                //Log.d(TAG, document.getId() + " => " + document.getData());
//                                property.put("pincode",document.get("pincode").toString());
//                                property.put("property address",document.get("property address").toString());
//                                property.put("property name",document.get("property name").toString());
//                                deleteProperty(document.getId(), w);
//                                break;
//                            }
//                        } else {
//                            //Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                        writeArchivedPropertyData(property);
//                    }
//                });
//        db.collection("ADD PROPERTY").document("DC")
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        //Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Log.w(TAG, "Error deleting document", e);
//                    }
//                });
//    }
//    public void writeArchivedPropertyData(Map property){
//
//        db.collection("Archive List")
//                .add(property)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        //Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Log.w(TAG, "Error adding document", e);
//                    }
//                });
//    }
//
//    public void deleteProperty(String id, View view){
//        db.collection("ADD PROPERTY").document(id)
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        //Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                        //Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Log.w(TAG, "Error deleting document", e);
//                    }
//                });
//        view.setVisibility(View.GONE);
//    }

    public void openMenu(View v)
    {
        PopupMenu popup = new PopupMenu(getApplicationContext(),findViewById(R.id.property_menu));
        popup.getMenuInflater().inflate(R.menu.property_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(),"Hide", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
    }

    public void goBack(View v){
        finish();
    }
}

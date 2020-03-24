package com.nitish.paymatrixandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ViewPager loginPager;
    private LoginPagerAdapter loginAdapter;
    private TabLayout tabLayout;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    private EditText name_edittext,mobile_edittext,email_edittext,password_edittext, email_mobile_edittext, confirm_password_edittext;
    private Button button;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=_])" +
                    "(?=\\S+$)" +
                    ".{4,}" +
                    "$"
    );

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginPager=findViewById(R.id.login_pager);
        loginAdapter=new LoginPagerAdapter(getSupportFragmentManager(),0);
        loginPager.setAdapter(loginAdapter);

        tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(loginPager);


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent i=new Intent(getApplicationContext(),UserDashboardActivity.class);
            startActivity(i);
        }
    }

    public void login(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent i=new Intent(getApplicationContext(),
                                    UserDashboardActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Failed Logging In",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signup(final String email,final String password,final String name,final String mobile){

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Message", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Failed to send email",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            FirebaseUser user_details = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();

                            user_details.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),"Failed Profile Update",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            String uid=FirebaseAuth.getInstance().getUid();

                            Map<String, Object> user_profile = new HashMap<>();
                            user_profile.put("Name", name);
                            user_profile.put("Email Address", email);
                            user_profile.put("Phone Number", mobile);

                            db.collection("Users").document(uid).set(user_profile);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Failed Signing Up",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void submit(View v){

        v=(View)v.getParent();

        progressBar = v.findViewById(R.id.progressBar);

        button = v.findViewById(R.id.submit_button);
        String str = button.getText().toString();

        name_edittext = v.findViewById(R.id.name_input);
        mobile_edittext = v.findViewById(R.id.mobile_input);
        email_mobile_edittext = v.findViewById(R.id.email_mobile_input);
        email_edittext = v.findViewById(R.id.email_input);
        password_edittext = v.findViewById(R.id.password_input);
        confirm_password_edittext = v.findViewById(R.id.confirm_password_input);

        if(str.equals("LOGIN"))
        {
            String email = email_mobile_edittext.getText().toString();
            String password = password_edittext.getText().toString();

            if( validateEmail(email) )
            {
                if(password.isEmpty()){
                    password_edittext.setError("Field can't be empty");
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    login(email,password);
                }
            }
            else
                Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show();

        }
        else
        {
            String name = name_edittext.getText().toString();
            String mobile = mobile_edittext.getText().toString();
            String email = email_edittext.getText().toString();
            String password = password_edittext.getText().toString();
            String confirm_password = confirm_password_edittext.getText().toString();
            if(password.equals(confirm_password) && !name.isEmpty() &&
                    !mobile.isEmpty() && validateEmail(email) && validatePassword(password))
            {
                progressBar.setVisibility(View.VISIBLE);
                signup(email, password, name, mobile);
            }
            else
                Toast.makeText(this, "Enter all Details", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEmail(String email){

        if(email.isEmpty()){
            email_mobile_edittext.setError("Field Can't be empty");
            return false;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_mobile_edittext.setError("please enter a valid email address ");
            return false;
        }
        else{
            //email_mobile_edittext.setError("null");
            return true;
        }
    }

    private boolean validatePassword(String password){

        if(password.isEmpty()){
            password_edittext.setError("Field can't be empty");
            return false;
        }

        else if(!PASSWORD_PATTERN.matcher(password).matches()){

            password_edittext.setError("password is too weak");
            return false;
        }

        else {
            //password_edittext.setError("null");
            return true;
        }
    }

}

package com.nitish.paymatrixandroidapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginSignupFragment extends Fragment {

    private Button button;
    private TextView t1,t2,t3,t4,t5,t6;
    private EditText et1,et2,et3,et4,et5,et6;
    public LoginSignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_signup, container, false);
        String str=getArguments().getString("text");

        t1=view.findViewById(R.id.name_text_view);
        t2=view.findViewById(R.id.mobile_text_view);
        t3=view.findViewById(R.id.email_mobile_text_view);
        t4=view.findViewById(R.id.email_text_view);
        t5=view.findViewById(R.id.password_text_view);
        t6=view.findViewById(R.id.confirm_password_text_view);

        et1=view.findViewById(R.id.name_input);
        et2=view.findViewById(R.id.email_input);
        et3=view.findViewById(R.id.email_mobile_input);
        et4=view.findViewById(R.id.mobile_input);
        et5=view.findViewById(R.id.password_input);
        et6=view.findViewById(R.id.confirm_password_input);

        if(str.equals("LOGIN"))
        {
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            t6.setVisibility(View.GONE);
            et1.setVisibility(View.GONE);
            et2.setVisibility(View.GONE);
            et4.setVisibility(View.GONE);
            et6.setVisibility(View.GONE);
        }
        else
        {
            t3.setVisibility(View.GONE);
            et3.setVisibility(View.GONE);
        }
        button=view.findViewById(R.id.submit_button);
        button.setText(str);

        return view;
    }

    public void submit(View v){

    }

}

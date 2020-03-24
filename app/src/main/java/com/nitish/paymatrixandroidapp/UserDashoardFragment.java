package com.nitish.paymatrixandroidapp;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDashoardFragment extends Fragment {

private ImageView image;
    public UserDashoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_dashoard, container, false);
        String image_src=getArguments().getString("image");

        int id = getResources().getIdentifier(image_src, "drawable", "com.nitish.paymatrixandroidapp");

        image=view.findViewById(R.id.pager_image);
        image.setImageResource(id);
        return view;
    }

}

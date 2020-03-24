package com.nitish.paymatrixandroidapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginPagerAdapter extends FragmentPagerAdapter{


    public LoginPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        LoginSignupFragment fragment=new LoginSignupFragment();
        position = position+1;
        Bundle bundle=new Bundle();
        if(position==1)
            bundle.putString("text","LOGIN");
        else
            bundle.putString("text","SIGNUP");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position==1)?"SIGNUP":"LOGIN";
    }
}

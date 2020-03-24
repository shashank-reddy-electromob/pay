package com.nitish.paymatrixandroidapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class UserDashboardPagerAdapter extends FragmentPagerAdapter {
    public UserDashboardPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        UserDashoardFragment fragment=new UserDashoardFragment();
        position = position+1;
        Bundle bundle=new Bundle();
        bundle.putString("image","dashboard_pager_image"+position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

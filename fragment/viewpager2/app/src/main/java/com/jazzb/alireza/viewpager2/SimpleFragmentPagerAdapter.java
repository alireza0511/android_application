package com.jazzb.alireza.viewpager2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    Bundle bundle;
    DayFragment dayFragment;

    public SimpleFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        bundle = new Bundle();
        dayFragment = new DayFragment();

        if (position == 0) {
            bundle.putString("day","Monday");
        } else if (position == 1) {
            bundle.putString("day","Tuesday");

        } else if (position == 2) {
            bundle.putString("day","Wednsday");

        } else if (position == 3) {
            bundle.putString("day","Thursday");

        } else {
            bundle.putString("day","Friday");

        }
        dayFragment.setArguments(bundle);
        return dayFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}

package com.jazzb.alireza.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

   public SimpleFragmentPagerAdapter(FragmentManager fragmentManager){
       super(fragmentManager);
   }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new MondayFragment();
        } else if (position == 1) {

            return new TuesdayFragment();
        } else if (position == 2) {

            return new WednsdayFragment();
        } else if (position == 3) {

            return new ThursdayFragment();
        } else {

            return new FridayFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}

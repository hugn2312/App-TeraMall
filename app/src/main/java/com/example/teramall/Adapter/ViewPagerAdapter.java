package com.example.teramall.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.teramall.Fragment.AccountFragment;
import com.example.teramall.Fragment.ShopFragment;
import com.example.teramall.Fragment.TeraFragment;
import com.example.teramall.Fragment.RentFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TeraFragment();
            case 1:
                return new RentFragment();
            case 2:
                return new ShopFragment();
            case 3:
                return new AccountFragment();
            default:
                return new TeraFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

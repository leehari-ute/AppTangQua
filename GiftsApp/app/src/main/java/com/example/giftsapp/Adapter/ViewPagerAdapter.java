package com.example.giftsapp.Adapter;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.giftsapp.Controller.Fragment_Accounts.Delivered;
import com.example.giftsapp.Controller.Fragment_Accounts.Delivering;
import com.example.giftsapp.Controller.Fragment_Accounts.WaitForConfirm;
import com.example.giftsapp.Controller.Fragment_Accounts.WaitForTheGift;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[] = {"Chờ xác nhận", "Chờ lấy hàng", "Đang giao hàng", "Đã giao hàng"};
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new WaitForTheGift();
            case 2:
                return new Delivering();
            case 3:
                return new Delivered();
            default:
                return new WaitForConfirm();
        }
    }

    @Override
    public int getCount() {
        return Titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}

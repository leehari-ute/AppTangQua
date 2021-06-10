package com.example.giftsapp.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.giftsapp.Controller.Fragment_Accounts.BillStatus;
import com.example.giftsapp.Controller.Fragment_Accounts.BillStatusAdmin;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[] = {"Chờ xác nhận", "Chờ lấy hàng", "Đang giao hàng", "Đã giao hàng"};
    private final boolean isAdmin;
    public ViewPagerAdapter(@NonNull FragmentManager fm, boolean isAdmin) {
        super(fm);
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment fragObj;
        if (isAdmin) {
            fragObj = new BillStatusAdmin();
        } else {
            fragObj = new BillStatus();
        }
        String status = "Error";
        switch (position){
            case 0:
                status = "WaitForConfirm";
                break;
            case 1:
                status = "WaitForTheGift";
                break;
            case 2:
                status = "Delivering";
                break;
            case 3:
                status = "Delivered";
                break;
            default:
                break;
        }
        bundle.putString("status", status);
        fragObj.setArguments(bundle);
        return fragObj;
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

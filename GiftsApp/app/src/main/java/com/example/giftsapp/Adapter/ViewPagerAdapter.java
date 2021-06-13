package com.example.giftsapp.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.giftsapp.Controller.Fragment_Accounts.BillStatus;
import com.example.giftsapp.Controller.Fragment_Accounts.BillStatusAdmin;
import com.example.giftsapp.Model.Bill;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence[] Titles = {"Chờ xác nhận", "Chờ lấy hàng", "Đang giao hàng", "Đã giao hàng"};
    private final boolean isAdmin;
    private final ArrayList<com.example.giftsapp.Model.Bill> billArrayList;
    public ViewPagerAdapter(@NonNull FragmentManager fm, boolean isAdmin, ArrayList<Bill> billArrayList) {
        super(fm);
        this.isAdmin = isAdmin;
        this.billArrayList = billArrayList;
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
                status = "Chờ xác nhận";
                break;
            case 1:
                status = "Chờ lấy hàng";
                break;
            case 2:
                status = "Đang giao hàng";
                break;
            case 3:
                status = "Đã giao hàng";
                break;
            default:
                break;
        }
        bundle.putString("STATUS", status);
        bundle.putParcelableArrayList("LIST_BILL", billArrayList);
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

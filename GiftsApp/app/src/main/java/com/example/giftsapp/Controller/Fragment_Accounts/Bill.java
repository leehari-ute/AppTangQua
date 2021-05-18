package com.example.giftsapp.Controller.Fragment_Accounts;

//import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.giftsapp.Adapter.BillAdapter;
import com.example.giftsapp.Adapter.ViewPagerAdapter;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Model.BillModel;
import com.example.giftsapp.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Bill extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SettingAccountForm settingAccountForm;
    FragmentManager fragmentManager;

    public Bill() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        Init(view);

        return view;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof SettingAccountForm) {
            this.settingAccountForm = (SettingAccountForm) context;
        }
    }

    private void Init(View view){
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        fragmentManager = getChildFragmentManager();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
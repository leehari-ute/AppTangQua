package com.example.giftsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.giftsapp.Controller.ProductDescriptionFragment;
import com.example.giftsapp.Controller.ProductOrderDetailsFragment;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    int totalTabs;
    public ProductDetailsAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                return productDescriptionFragment1;
            case 1:
                ProductOrderDetailsFragment productOrderDetailsFragment = new ProductOrderDetailsFragment();
                return productOrderDetailsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

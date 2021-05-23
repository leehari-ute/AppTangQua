package com.example.giftsapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giftsapp.R;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter {

    private List<String> productImagetList;

    public ProductImageAdapter(List<String> productImagetList) {
        this.productImagetList = productImagetList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       ImageView productImage = new ImageView(container.getContext());
      // productImage.setImageResource(productImagetList.get(position));
        Glide.with(container.getContext()).load(productImagetList.get(position)).apply(new RequestOptions().placeholder(R.drawable.ic__homec)).into(productImage);
       container.addView(productImage,0);
       return productImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return productImagetList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    // Adapter này load bên product_images_layout

}

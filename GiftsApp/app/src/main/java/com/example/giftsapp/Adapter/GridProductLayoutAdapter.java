package com.example.giftsapp.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giftsapp.Controller.ProductDetailsActivity;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.R;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {
    //Dùng lại cái HorizontalProductScrollModel
    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public GridProductLayoutAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return horizontalProductScrollModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        String id = horizontalProductScrollModelList.get(position).getId();
        if(convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(parent.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("IdProduct1",horizontalProductScrollModelList.get(position).getId());
                    productDetailsIntent.putExtra("productDescription1",horizontalProductScrollModelList.get(position).getProductDetailsDescription());
                    parent.getContext().startActivity(productDetailsIntent);
                }
            });

            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productName = view.findViewById(R.id.h_s_product_name);
            TextView productDescription = view.findViewById(R.id.h_s_product_description);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);


            Glide.with(parent.getContext()).load( horizontalProductScrollModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.ic__homec)).into(productImage);
            productName.setText(horizontalProductScrollModelList.get(position).getProductName());
            productDescription.setText(horizontalProductScrollModelList.get(position).getProductDescription());
            productPrice.setText(horizontalProductScrollModelList.get(position).getProductPrice()+".VND");
        }
        else{
            view = convertView;
        }
        return view;
    }
}

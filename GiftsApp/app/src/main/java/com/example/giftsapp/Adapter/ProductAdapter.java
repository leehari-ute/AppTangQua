package com.example.giftsapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private List<Products> productsList;

    public ProductAdapter(Context context, int layout, List<Products> productsList) {
        this.context = context;
        this.layout = layout;
        this.productsList = productsList;
    }

    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        //map
        TextView txtName = convertView.findViewById(R.id.txtNameProduct);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        ImageView img = convertView.findViewById(R.id.imgProduct);

        //assign
        Products products = productsList.get(position);
        txtName.setText(products.getName());
        txtPrice.setText(products.getPrice() + " VND");
        txtQuantity.setText("SL: " + products.getQuantity());
        Glide.with(context.getApplicationContext())
                .load(products.getImageUrl())
                .into(img);

        return convertView;
    }
}

package com.example.giftsapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giftsapp.Controller.OrderDetailsActivity;
import com.example.giftsapp.Model.Bill;
import com.example.giftsapp.Model.BillModel;
import com.example.giftsapp.R;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Bill> billList;

    public BillAdapter(Context context, int layout, List<Bill> billList) {
        this.context = context;
        this.layout = layout;
        this.billList = billList;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        //Init view
        TextView txtFirstPro = convertView.findViewById(R.id.txtFirstPro);
        TextView txtFirstPrice = convertView.findViewById(R.id.txtFirstPrice);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        TextView txtStatus = convertView.findViewById(R.id.txtStatus);
        TextView txtTotal = convertView.findViewById(R.id.txtTotal);
        TextView txtViewDetail = convertView.findViewById(R.id.txtViewDetail);
        ImageView imgSp = convertView.findViewById(R.id.imgSp);

        //Gán gtri
        Bill bill = billList.get(position);
        txtFirstPro.setText(bill.getProductsArrayList().get(0).getName());
        txtFirstPrice.setText(bill.getProductsArrayList().get(0).getPrice());
        txtStatus.setText(bill.getStatus().get(0).getName());
        txtQuantity.setText(bill.getQuantityProduct() + " sản phẩm");
        txtTotal.setText(bill.getTotalPrice());
        Glide.with(context.getApplicationContext())
                .load(bill.getProductsArrayList().get(0).getImageUrl())
                .into(imgSp);

        txtViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("PARCEL_BILL", bill);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        return convertView;
    }
}

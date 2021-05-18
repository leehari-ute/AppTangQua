package com.example.giftsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giftsapp.Model.BillModel;
import com.example.giftsapp.R;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<BillModel> listBill;

    public BillAdapter(Context context, int layout, List<BillModel> listBill) {
        this.context = context;
        this.layout = layout;
        this.listBill = listBill;
    }

    @Override
    public int getCount() {
        return listBill.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
        ImageView imgSp = convertView.findViewById(R.id.imgSp);

        //Gán gtri
        BillModel billModel = listBill.get(position);
        txtFirstPro.setText(billModel.getFirstProduct());
        txtFirstPrice.setText(billModel.getFirstPrice());
        txtStatus.setText(billModel.getStatus());
        txtQuantity.setText(billModel.getQuantity());
        txtTotal.setText(billModel.getTotal());
        Glide.with(context.getApplicationContext())
                .load(billModel.getImgUrl())
                .into(imgSp);

        return convertView;
    }
}

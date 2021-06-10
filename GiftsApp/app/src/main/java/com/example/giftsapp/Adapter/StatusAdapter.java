package com.example.giftsapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giftsapp.Model.Products;
import com.example.giftsapp.Model.StatusBill;
import com.example.giftsapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class StatusAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<StatusBill> statusBills;

    public StatusAdapter(Context context, int layout, List<StatusBill> statusBills) {
        this.context = context;
        this.layout = layout;
        this.statusBills = statusBills;
    }

    @Override
    public int getCount() {
        return statusBills.size();
    }

    @Override
    public Object getItem(int position) {
        return statusBills.get(position);
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

        ImageView imgStatus = convertView.findViewById(R.id.imgStatus);
        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtDate = convertView.findViewById(R.id.txtDate);

        StatusBill statusModel = statusBills.get(position);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

        if (statusModel.getDone()) {
            imgStatus.setImageResource(R.drawable.selected_dot);
        }
        String strDate = formatter.format(statusModel.getDate());
        String status =  "Đã giao hàng";
        switch (statusModel.getName()) {
            case "Đang giao hàng":
                status = "Đã lấy hàng";
                break;
            case "Chờ lấy hàng":
                status = "Đã xác nhận";
                break;
            case "Chờ xác nhận":
                status = "Đã tạo đơn";
            default:
                break;
        }

        txtTitle.setText(status);
        txtDate.setText(strDate);
        return convertView;
    }
}

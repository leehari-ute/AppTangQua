package com.example.giftsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;

import java.util.List;

public class products_adapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private List<Products> productsList;

    public products_adapter(Context context, int layout, List<Products> productsList) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        //ánh xạ view
        TextView txtTen = convertView.findViewById(R.id.txtviewTen);
        TextView txtGia = convertView.findViewById(R.id.txtviewGia);
        ImageView imgHinh = convertView.findViewById(R.id.imageviewHinh);

        //gán
        Products products = productsList.get(position);
        txtTen.setText(products.getTenSp());
        txtGia.setText(products.getGia());
        imgHinh.setImageResource(products.getHinh());

        return convertView;
    }
}

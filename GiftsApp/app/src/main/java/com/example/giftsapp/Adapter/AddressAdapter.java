package com.example.giftsapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.giftsapp.Model.Address;
import com.example.giftsapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Address> addressList;

    public AddressAdapter(Context context, int layout, List<Address> addressList) {
        this.context = context;
        this.layout = layout;
        this.addressList = addressList;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtDefault = convertView.findViewById(R.id.txtDefault);
        TextView txtPhone = convertView.findViewById(R.id.txtPhone);
        TextView txtDetailAddress = convertView.findViewById(R.id.txtDetailAddress);
        TextView txtVillage = convertView.findViewById(R.id.txtVillage);
        TextView txtDistrict = convertView.findViewById(R.id.txtDistrict);
        TextView txtProvince = convertView.findViewById(R.id.txtProvince);
        TextView txtIconLocation = convertView.findViewById(R.id.txtIconLocation);

        Address address = addressList.get(position);
        txtName.setText(address.getName());
        txtPhone.setText(address.getPhone());
        txtDetailAddress.setText(address.getDetailAddress());
        txtVillage.setText(address.getVillage());
        txtDistrict.setText(address.getDistrict());
        txtProvince.setText(address.getProvince());

        if (!address.isDefault()) {
            txtDefault.setVisibility(View.INVISIBLE);
            @SuppressLint("UseCompatLoadingForDrawables") Drawable iconLocation = context.getResources().getDrawable(R.drawable.location_gray);
            txtIconLocation.setCompoundDrawables(null, null, iconLocation, null);
        }
        return convertView;
    }
}

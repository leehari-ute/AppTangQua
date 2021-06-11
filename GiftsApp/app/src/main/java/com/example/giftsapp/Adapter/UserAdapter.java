package com.example.giftsapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.giftsapp.Model.Bill;
import com.example.giftsapp.Model.User;
import com.example.giftsapp.R;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private List<User> userList;

    public UserAdapter(Context context, int layout, List<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        TextView txtStt = convertView.findViewById(R.id.txtStt);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtEmail = convertView.findViewById(R.id.txtEmail);

        User user = userList.get(position);
        txtStt.setText(position + 1 +"");
        txtName.setText(user.getFullName());
        txtEmail.setText(user.getEmail());

        return convertView;
    }
}

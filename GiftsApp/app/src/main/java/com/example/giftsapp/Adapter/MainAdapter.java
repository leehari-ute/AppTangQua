package com.example.giftsapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftsapp.Controller.BillAdmin;
import com.example.giftsapp.Controller.ProductsForm;
import com.example.giftsapp.Controller.SettingAccountAdmin;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Model.MainModel;
import com.example.giftsapp.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements AdapterView.OnItemClickListener {
    ArrayList<MainModel> mainModels;
    Context context;
    private  ItemClickListener listener ;

    // tạo constructor
    public MainAdapter(Context context, ArrayList<MainModel> mainModels)
    {
        this.context = context;
        this.mainModels=mainModels;
    }



    // tạo setter cho listener
    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // tạo view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        // set logo for imageView
        holder.imageView.setImageResource(mainModels.get(position).getIconImage());
        // set name for Textview
        holder.textView.setText(mainModels.get(position).getIconName());
        //set OnClickListener
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // tạo biến trong row_item layout
        ImageView imageView;
        TextView textView;
        int position;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            // Ánh xạ
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            linearLayout=itemView.findViewById(R.id.linearlayout);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProductsForm.class);
                    switch (position) {
                        case 1:
                            intent = new Intent(v.getContext(), BillAdmin.class);
                            break;
                        case 2:
                            intent = new Intent(v.getContext(), ProductsForm.class);
                            break;
                        case 3:
                            intent = new Intent(v.getContext(), SettingAccountAdmin.class);
                        default:
                            break;
                    }
                    v.getContext().startActivity(intent);
                    ((Activity)context).finish();
                }
            });
        }
    }


}

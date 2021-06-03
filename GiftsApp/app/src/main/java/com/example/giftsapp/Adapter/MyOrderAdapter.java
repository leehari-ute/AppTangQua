package com.example.giftsapp.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftsapp.Controller.OrderDetailsActivity;
import com.example.giftsapp.Model.MyOrderItemModel;
import com.example.giftsapp.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Viewholder> {

    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return  new Viewholder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.Viewholder holder, int position) {
        int resource = myOrderItemModelList.get(position).getProductImage();
        int rating = myOrderItemModelList.get(position).getRating();
        String title = myOrderItemModelList.get(position).getProductTitle();
        String deliveryStatus = myOrderItemModelList.get(position).getDeliveryStatus();
        holder.setData(resource,title,deliveryStatus,rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }
    class Viewholder extends RecyclerView.ViewHolder{

        ImageView productImage;
        ImageView deliveryIndicator; // chỉ thị màu của trạng thái đơn hàng
        TextView productTitle;
        TextView deliveryStatus; // trạng thái giao hàng
        private LinearLayout rateNowContainer;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            deliveryIndicator = itemView.findViewById(R.id.order_indicator);
            productTitle = itemView.findViewById(R.id.product_title);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date);
            rateNowContainer =itemView.findViewById(R.id.rate_now_container);

            // click chuyển sang view chi tiết order
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setData(int resource, String title, String deliveryDate ,int rating){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(deliveryDate.equals("Cancelled")) { // trạng thái chưa gửi đc
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorRed)));
            }
            else{
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
            }
            deliveryStatus.setText(deliveryDate);

            //////rating layout
            setRating(rating);
            for(int i=0;i<rateNowContainer.getChildCount();i++)
            {
                final int starPosition = i;
                rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }
            /////// rating layout
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setRating(int starPosition) {
            for(int i=0;i<rateNowContainer.getChildCount();i++)
            {
                ImageView starBtn = (ImageView)rateNowContainer.getChildAt(i);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(i<=starPosition){
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }
    }
}

package com.example.giftsapp.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giftsapp.Controller.ProductDetailsActivity;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.R;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {
    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    // tạo controller cho list HorizontalProductScrollModel
    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }

    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getProductImage();
        String name = horizontalProductScrollModelList.get(position).getProductName();
        String description = horizontalProductScrollModelList.get(position).getProductDescription();
        String price = horizontalProductScrollModelList.get(position).getProductPrice();
        String id = horizontalProductScrollModelList.get(position).getId();
        String productDetailDescription = horizontalProductScrollModelList.get(position).getProductDetailsDescription();

        holder.setProductImage(resource);
        holder.setProductName(name);
        holder.setProductDescription(description);
        holder.setProductPrice(price);
        holder.setId(id);
        holder.setProductDetailsDescription(productDetailDescription);
    }

    public String getIdDocument(int position) {
        return horizontalProductScrollModelList.get(position).getId();
    }
    @Override
    public int getItemCount() {
        if(horizontalProductScrollModelList.size()>8){
            return 8;
        }
        else{
        return horizontalProductScrollModelList.size();}
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productDescription;
        private TextView productPrice;
        private String Id;
        private String productDetailsDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_product_image);
            productName = itemView.findViewById(R.id.h_s_product_name);
            productDescription = itemView.findViewById(R.id.h_s_product_description);
            productPrice = itemView.findViewById(R.id.h_s_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("IdProduct",Id);
                    productDetailsIntent.putExtra("productDescription",productDetailsDescription);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
        private void setProductImage(String resource){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic__homec)).into(productImage);
        }
        private void setProductName(String Name)
        {
            productName.setText(Name);
        }
        private void setProductDescription(String description)
        {
            productDescription.setText(description);
        }
        private void setProductPrice (String price){
            productPrice.setText(price+".VND");
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getProductDetailsDescription() {
            return productDetailsDescription;
        }

        public void setProductDetailsDescription(String productDetailsDescription) {
            this.productDetailsDescription = productDetailsDescription;
        }
    }
}

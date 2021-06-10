package com.example.giftsapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giftsapp.Controller.ProductDetailsActivity;
import com.example.giftsapp.Model.ProductSearchModel;
import com.example.giftsapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> implements Filterable {

    List<ProductSearchModel> productSearchModelList ;
    List<ProductSearchModel> productSearchModelListOld;

    public SearchProductAdapter(List<ProductSearchModel> productSearchModelList) {
        this.productSearchModelList = productSearchModelList;
        this.productSearchModelListOld = productSearchModelList; // list product ban đầu
    }

    @NonNull
    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductAdapter.ViewHolder holder, int position) {
        String Resource = productSearchModelList.get(position).getProductImage();
        String ProName = productSearchModelList.get(position).getProductName();
        String ProDescription = productSearchModelList.get(position).getProductDescription();
        String ProPrice = productSearchModelList.get(position).getProductPrice();
        String ProID = productSearchModelList.get(position).getProductId();
        String ProDescriptionDetail = productSearchModelList.get(position).getProductDetailDescription();

        holder.setProductImage(Resource);
        holder.setProductName(ProName);
        holder.setProductDescription(ProDescription);
        holder.setProductPrice(ProPrice);
        holder.setId(ProID);
        holder.setProDetailDescription(ProDescriptionDetail);

    }

    @Override
    public int getItemCount() {
        if(productSearchModelList.size()!=0) {
            return productSearchModelList.size();
        }
        return 0;
    }

    // Filter product chỗ này
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String StrSearch = constraint.toString(); // key word mà người ta nhập
                if(StrSearch.isEmpty())
                {
                    productSearchModelList = productSearchModelListOld;
                }
                else {
                    List<ProductSearchModel> list = new ArrayList<>();
                    for(ProductSearchModel pro : productSearchModelListOld)
                    {
                        if(pro.getProductName().toLowerCase().contains(StrSearch.toLowerCase()))
                        {
                            list.add(pro);
                        }
                    }
                    productSearchModelList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productSearchModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productSearchModelList = (List<ProductSearchModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productDescription;
        private TextView productPrice;
        private String Id;
        private String ProDetailDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_title);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("IdProductSearch",Id);
                    productDetailsIntent.putExtra("ProDescriptionSearch",ProDetailDescription);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }

        public void setProductImage(String resource) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic__homec)).into(productImage);
        }

        public void setProductName(String Name) {
            productName.setText(Name);
        }

        public void setProductDescription(String description) {
            productDescription.setText(description);
        }

        public void setProductPrice(String price) {
            productPrice.setText(price+".VND");
        }

        public void setProDetailDescription(String proDetailDescription) {
            ProDetailDescription = proDetailDescription;
        }

        public void setId(String id) {
            Id = id;
        }
        public String getId() {
            return Id;
        }
    }

}

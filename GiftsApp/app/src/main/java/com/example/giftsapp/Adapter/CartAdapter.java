package com.example.giftsapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftsapp.Model.CartItemModel;
import com.example.giftsapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType())
        {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType){
                case CartItemModel.CART_ITEM:
                    View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                    return new CartItemViewholder(cartItemView);  // View load các sản phẩm trong giỏ
                case CartItemModel.TOTAL_AMOUNT:
                    View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                    return new CartTotalAmountViewholder(cartTotalView); // view tính tiền các sản phẩm
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
            {
                int resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                int freeVAT = cartItemModelList.get(position).getFreeVAT();
                String price = cartItemModelList.get(position).getProductPrice();
                String cutPrice = cartItemModelList.get(position).getCuttedPrice();
                int codesales = cartItemModelList.get(position).getCodeSale();

                ((CartItemViewholder)holder).setItemDetails(resource,title,freeVAT,price,cutPrice,codesales);
                break;}
                case CartItemModel.TOTAL_AMOUNT:
                {
                    int totalItems = cartItemModelList.get(position).getTotalItem();
                    String totalItemsPrice = cartItemModelList.get(position).getTotalItemPrice();
                    String deliveryPrice = cartItemModelList.get(position).getDeliveryPrice();
                    String totalAmount = cartItemModelList.get(position).getTotalAmount();
                    String saveAmount = cartItemModelList.get(position).getSaveAmount();
                    ((CartTotalAmountViewholder)holder).setTotalAmounts(totalItems,totalItemsPrice,deliveryPrice,totalAmount,saveAmount);
                }
            default: return;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewholder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView freeVATIcon;
        private TextView productTitle;
        private TextView freeVAT;
        private TextView cuttedPrice;
        private TextView productPrice;
        private TextView productQuantity;
        private TextView codeSale;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            freeVATIcon = itemView.findViewById(R.id.free_VAT_icon);
            productTitle = itemView.findViewById(R.id.product_title);
            freeVAT = itemView.findViewById(R.id.tv_free_VAT);
            cuttedPrice = itemView.findViewById(R.id.tv_cut_price);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity=itemView.findViewById(R.id.product_quantity);
            codeSale = itemView.findViewById(R.id.tv_codeSale);
        }
        private void setItemDetails(int resource, String title, int freeVATNo, String productPriceText, String productCuttedPrice, int CodeSale)
        {
            productImage.setImageResource(resource);
            productTitle.setText(title);

            if(freeVATNo>0)
            {
                freeVATIcon.setVisibility(View.VISIBLE);
                freeVAT.setVisibility(View.VISIBLE);
                freeVAT.setText(freeVATNo+""+"% VAT");
            }
            else{
                freeVAT.setText("0% VAT");
            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(productCuttedPrice);
            codeSale.setText("Mã giảm giá: "+CodeSale+"");

        }

    }

    class CartTotalAmountViewholder extends RecyclerView.ViewHolder{

        private TextView totalItems; // SL sản phẩm
        private TextView totalItemPrice;// tổng tiền sản phẩm
        private TextView deliveryPrice;// tiền vận chuyển
        private TextView saveAmount; // tiết kiệm
        private TextView totalAmount;// Tổng tiền cần phải trả
        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice=itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            saveAmount=itemView.findViewById(R.id.save_amount);
        }
        private void setTotalAmounts(int totalItemsText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String saveAmountText){
            totalItems.setText(totalItemsText+"");
            totalItemPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            saveAmount.setText(saveAmountText);
        }
    }
}

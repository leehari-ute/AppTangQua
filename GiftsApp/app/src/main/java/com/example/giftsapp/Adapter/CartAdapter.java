package com.example.giftsapp.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giftsapp.Controller.DeliveryActivity;
import com.example.giftsapp.Controller.MainActivity;
import com.example.giftsapp.Controller.MyCartFragment;
import com.example.giftsapp.Controller.ProductDetailsActivity;
import com.example.giftsapp.Model.CartItemModel;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.giftsapp.Controller.LoginForm.currentUser;

public class CartAdapter extends RecyclerView.Adapter {
    private MainActivity mainActivity;


    private List<CartItemModel> cartItemModelList;
    FirebaseFirestore firebaseFirestore;

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
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                int freeVAT = cartItemModelList.get(position).getFreeVAT();
                String price = cartItemModelList.get(position).getProductPrice();
                String cutPrice = cartItemModelList.get(position).getCuttedPrice();
                int codesales = cartItemModelList.get(position).getCodeSale();
                int productQuantitys = cartItemModelList.get(position).getProductQuantity();
                String productId = cartItemModelList.get(position).getProID();
                String proStatus = cartItemModelList.get(position).getProStatus();

                ((CartItemViewholder)holder).setItemDetails(resource,title,freeVAT,price,cutPrice,codesales,productQuantitys,productId,proStatus);
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
        private String productID;
        private TextView removeItemCart;
        private TextView ProductStatus; // check còn sản phẩm trong kho không

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
            removeItemCart = itemView.findViewById(R.id.tv_remove_item);
            ProductStatus = itemView.findViewById(R.id.tv_status);
        }
        private void setItemDetails(String resource, String title, int freeVATNo, String productPriceText, String productCuttedPrice
                , int CodeSale, int productQuantitytext, String productIDtext, String productStatusText)
        {
           // productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic__homec)).into(productImage);
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
            productQuantity.setText("SL: "+productQuantitytext);

            if(productStatusText.equals("Còn hàng")) {
                ProductStatus.setText(productStatusText);
                ProductStatus.setTextColor(Color.parseColor("#2BCC6F"));
            }else
            {
                ProductStatus.setText(productStatusText);
                ProductStatus.setTextColor(Color.parseColor("#DF0404"));
            }

            firebaseFirestore = FirebaseFirestore.getInstance(); // kết nối DB



            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    EditText quantityNo = quantityDialog.findViewById(R.id.quantity_no);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn);
                    Button okBtn = quantityDialog.findViewById(R.id.ok_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                firebaseFirestore.collection("Carts").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            if(documentSnapshot.exists()) {
                                                final double[] totalPrice = {0};
                                                final ArrayList<Map<String, Object>>[] productArray = new ArrayList[]{(ArrayList<Map<String, Object>>) documentSnapshot.getData().get("ListProducts")};
                                                for (int i = 0; i < productArray[0].size(); i++) {
                                                    String ProID = productArray[0].get(i).get("ProductID").toString();
                                                    int quantity = Integer.parseInt(productArray[0].get(i).get("Quantity").toString());
                                                    if(ProID.equals(productIDtext))
                                                    {
                                                        productQuantity.setText("SL: "+quantityNo.getText().toString());

                                                        // productQuantitytext = Integer.parseInt(quantityNo.getText().toString());

                                                        // xóa items với số lượng cũ
                                                        Map<String,Object> itemRemove = new HashMap<>();
                                                        itemRemove.put("ProductID",productIDtext);
                                                        itemRemove.put("Quantity", quantity);
                                                        firebaseFirestore.collection("Carts").document(currentUser).update("ListProducts", FieldValue.arrayRemove(itemRemove));

                                                        // Thêm items lại với số lượng được cập nhập
                                                        Map<String, Object> ItemCart = new HashMap<>();
                                                        ItemCart.put("ProductID",productIDtext);
                                                        ItemCart.put("Quantity",Integer.parseInt( quantityNo.getText().toString()));
                                                        firebaseFirestore.collection("Carts").document(currentUser).update("ListProducts", FieldValue.arrayUnion(ItemCart));

                                                        break;
                                                    }
                                                }
                                            }
                                            else{
                                                Toast.makeText(mainActivity, "Không có giỏ hàng", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(mainActivity, "Không có giỏ hàng", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }catch (Exception e)
                            {
                                Toast.makeText(mainActivity, "Chưa có giỏ hàng", Toast.LENGTH_SHORT).show();
                            }

                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });



            removeItemCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        firebaseFirestore.collection("Carts").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.exists()) {
                                        final double[] totalPrice = {0};
                                        final ArrayList<Map<String, Object>>[] productArray = new ArrayList[]{(ArrayList<Map<String, Object>>) documentSnapshot.getData().get("ListProducts")};
                                        for (int i = 0; i < productArray[0].size(); i++) {
                                            String ProID = productArray[0].get(i).get("ProductID").toString();
                                            int quantity = Integer.parseInt(productArray[0].get(i).get("Quantity").toString());
                                            if(ProID.equals(productIDtext))
                                            {
                                                Map<String,Object> itemRemove = new HashMap<>();
                                                itemRemove.put("ProductID",productIDtext);
                                                itemRemove.put("Quantity",quantity);
                                                firebaseFirestore.collection("Carts").document(currentUser).update("ListProducts", FieldValue.arrayRemove(itemRemove));

                                                break;
                                            }
                                        }

                                    }
                                    else{
                                        Toast.makeText(mainActivity, "Không có giỏ hàng", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(mainActivity, "Không có giỏ hàng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }catch (Exception e)
                    {
                        Toast.makeText(mainActivity, "Chưa có giỏ hàng", Toast.LENGTH_SHORT).show();
                    }

                   /* Map<String,Object> itemRemove = new HashMap<>();
                    itemRemove.put("ProductID",productIDtext);
                    //itemRemove.put("Quantity",finalQuantityCurrent);
                    firebaseFirestore.collection("Carts").document(currentUser).update("ListProducts", FieldValue.arrayRemove(itemRemove));*/

                }
            });
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
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

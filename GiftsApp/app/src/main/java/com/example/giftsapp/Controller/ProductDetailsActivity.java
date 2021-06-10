package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giftsapp.Adapter.ProductDetailsAdapter;
import com.example.giftsapp.Adapter.ProductImageAdapter;
import com.example.giftsapp.Model.CartCurrentModel;
import com.example.giftsapp.Model.CartItemModel;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.example.giftsapp.Controller.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.giftsapp.Controller.LoginForm.currentUser;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView AddToCart;

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;


    private  static  boolean ALREADY_ADDED_TO_WISHLIST = false;
    private FloatingActionButton addToWishListBtn;

    // product description layout
    private ConstraintLayout productDetailsOnly;
    private ConstraintLayout productDetailsTabs;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    public static String productOrderDetails;
    public static String productDescription="Đang cập nhập";
    public static int tabPosition =-1;

    private String Id_product; // horizontalScollViewAdapter
    private String Id_product1; // GridProductLayoutAdapter
    private String Id_product2; // HomePageFragment Adapter
    private String Id_product3; // SearchProductAdapter
    private String ProductDetailDescription;
    private String ProductDetailDescription1;
    private String ProductDetailDescription2;
    private String ProductDetailDescription3; // ProDescriptionSearch
    // product description layout

    // rate now layout
    private LinearLayout rateNowContainer;
    // rate now layout
    private Button buyNowBtn;
    private FirebaseFirestore firebaseFirestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishListBtn = (FloatingActionButton) findViewById(R.id.add_to_wishList_btn);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tabLayout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_ratings_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cut_price);
        productDetailsTabs=findViewById(R.id.product_details_tabs_container);
        AddToCart = findViewById(R.id.tv_AddToCart); // nút add to cart chỗ này

        Id_product = getIntent().getStringExtra("IdProduct");
        Id_product1 = getIntent().getStringExtra("IdProduct1");
        Id_product2 = getIntent().getStringExtra("IdProduct2");
        Id_product3 = getIntent().getStringExtra("IdProductSearch");


        ProductDetailDescription = getIntent().getStringExtra("productDescription");
        ProductDetailDescription1 = getIntent().getStringExtra("productDescription1");
        ProductDetailDescription2 = getIntent().getStringExtra("productDescription2");
        ProductDetailDescription3 = getIntent().getStringExtra("ProDescriptionSearch");
        final String[] description_sms = new String[1];
        List<String> productImages = new ArrayList<>();
        /*productImages.add(R.drawable.son);
        productImages.add(R.drawable.teddy);
        productImages.add(R.drawable.gmail);
        productImages.add(R.drawable.facebook);*/

        String Id_product_current = "";
        if(Id_product==null && Id_product1==null && Id_product3==null)
        {
            Id_product_current = Id_product2;
        }
        else if(Id_product==null && Id_product2==null && Id_product3==null)
        {
            Id_product_current = Id_product1;
        }
        else if(Id_product1 == null && Id_product2==null && Id_product3==null)
        {
            Id_product_current = Id_product;
        }
        else if(Id_product == null && Id_product2==null && Id_product1==null)
        {
            Id_product_current = Id_product3;
        }

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Products").document(Id_product_current)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (int i = 0; i < 3; i++) {
                        productImages.add(documentSnapshot.get("imageUrl").toString());
                    }
                    ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
                    productImagesViewPager.setAdapter(productImageAdapter);

                    productTitle.setText(documentSnapshot.get("name").toString());
                    averageRatingMiniView.setText("4.5");
                    totalRatingMiniView.setText("( 100 ratings)");
                    productPrice.setText(documentSnapshot.get("price").toString() + ".VND");
                    long price = Long.parseLong(documentSnapshot.get("price").toString()) + 20000;
                    cuttedPrice.setText(price + "" + ".VND");

                   description_sms[0] = documentSnapshot.get("description").toString();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            }
        });

        if(ProductDetailDescription==null && ProductDetailDescription1==null && ProductDetailDescription3==null)
        {
            productDescription = ProductDetailDescription2;
        }else if(ProductDetailDescription==null && ProductDetailDescription2==null && ProductDetailDescription3==null)
        {
            productDescription = ProductDetailDescription1;
        }else if(ProductDetailDescription1==null && ProductDetailDescription2==null && ProductDetailDescription3==null)
        {
            productDescription = ProductDetailDescription;
        }else if(ProductDetailDescription1==null && ProductDetailDescription2==null && ProductDetailDescription==null)
        {
            productDescription = ProductDetailDescription3;
        }

        List<CartCurrentModel> cartCurrentModelList = new ArrayList<>();

        try {
            String finalId_product_current = Id_product_current;
            firebaseFirestore.collection("Carts").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.exists()) {
                            // load dữ liệu của cart cũ
                            ArrayList<Map<String, Object>> productArray = (ArrayList<Map<String, Object>>) documentSnapshot.getData().get("ListProducts");
                            for (int i = 0; i < productArray.size(); i++) {
                                String ProID = productArray.get(i).get("ProductID").toString();
                                int Quantity = Integer.parseInt(productArray.get(i).get("Quantity").toString());
                                cartCurrentModelList.add(new CartCurrentModel(ProID,Quantity));
                            }
                            AddToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cartCurrentModelList.add(new CartCurrentModel(finalId_product_current,1));
                                    loadCart(cartCurrentModelList);
                                    Log.i("TH1","giỏ hàng tồn tại");
                                    Log.i("sizelist",cartCurrentModelList.size()+"");
                                }
                            });
                        }
                        else{
                            AddToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AddCart(finalId_product_current,1);
                                    Log.i("TH2","chưa có giỏ hàng trước đó");
                                }

                            });

                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(ProductDetailsActivity.this, "Không có giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(ProductDetailsActivity.this, "Lỗi phát sinh", Toast.LENGTH_SHORT).show();
        }


        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);
        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ALREADY_ADDED_TO_WISHLIST){
                    ALREADY_ADDED_TO_WISHLIST =false;
                    addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                }else
                {
                    ALREADY_ADDED_TO_WISHLIST =true;
                    addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorOranges));
                }
            }
        });
        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));

        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                productDetailsViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // rate now layout
        rateNowContainer = findViewById(R.id.rate_now_container);
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
        // rate now layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });

    }

    // set màu cho image star
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


    // thêm Item khi đã có giỏ hàng
    public void loadCart (List<CartCurrentModel> listProductCart)
    {
        Map<String, Object> ItemCart = new HashMap<>();
        Map<String, Object>AddCart = new HashMap<>();
        for(int x=0;x<listProductCart.size();x++)
        {
            ItemCart.put("ProductID",listProductCart.get(x).getProductID());
            ItemCart.put("Quantity",listProductCart.get(x).getProductQuantity());
            firebaseFirestore.collection("Carts").document(currentUser).update("ListProducts", FieldValue.arrayUnion(ItemCart));
            Toast.makeText(ProductDetailsActivity.this, "Thêm vào giỏ thành công", Toast.LENGTH_SHORT).show();
        }
    }

    // Thêm item khi chưa có giỏ hàng
    public void AddCart(String ProductID, int Quantity)
    {
        Map<String, Object> ItemCart;
        Map<String, Object>AddCart = new HashMap<>();

        ItemCart = new HashMap<>();
        ItemCart.put("ProductID",ProductID);
        ItemCart.put("Quantity",Quantity);
        AddCart.put("ListProducts",Arrays.asList(ItemCart));
        try{
            firebaseFirestore.collection("Carts").document(currentUser)
                    .set(AddCart).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ProductDetailsActivity.this, "Thêm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProductDetailsActivity.this, "Thêm vào giỏ thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(ProductDetailsActivity.this, "Thêm vào giỏ thất bại 111", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home)
        {
            finish();
            return true;
        }
        else if(id == R.id.main_search_icon)
        {
            // search chỗ này

            return true;
        }
        else if(id == R.id.main_cart_icon)
        {
            Intent cartIntent = new Intent(ProductDetailsActivity.this,MainActivity.class);
            com.example.giftsapp.Controller.MainActivity.showCart =true;
            startActivity(cartIntent);
            // xem giỏ hàng chỗ này
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.example.giftsapp.Controller;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;
import com.example.giftsapp.Adapter.ProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProductsForm extends AppCompatActivity{
    EditText            edtSearch;
    GridView            gvProducts;
    ProductAdapter      productAdapter;
    ImageButton         btnOccasion, btnObject, btnHoliday, btnNew, btnMoney;
    FirebaseFirestore   fStore;
    FirebaseStorage     fStorage;
    StorageReference    storageRef;
    List<Products>      productsList;
    List<Products>      searchProductsList;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cửa hàng của tôi");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.supply1);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        Init();

        btnObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu(v);
            }
        });

        btnOccasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu(v);
            }
        });

        btnHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu(v);
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu(v);
            }
        });

        btnMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu(v);
            }
        });

        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        searchProductsList.clear();
                        String keySearch = edtSearch.getText().toString().trim().toLowerCase();
                        for (Products product: productsList) {
                            if (product.getName().toLowerCase().contains(keySearch)) {
                                searchProductsList.add(product);
                            }
                        }
                        productAdapter  = new ProductAdapter(getApplicationContext(), R.layout.products, searchProductsList);
                        gvProducts.setAdapter(productAdapter);

                        return true;
                    }
                }
                return false;
            }
        });

        gvProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditProductForm.class);
                Products product = (Products) parent.getAdapter().getItem(position);
                intent.putExtra("EXTRA_DOCUMENT_PRODUCT", product);
                startActivity(intent);
                finish();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AdminHome.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSettings:
                break;
            case  R.id.menuReload:
                productsList.clear();
                GetProducts();
                break;
            case R.id.menuAdd:
                Intent intent = new Intent(getApplicationContext(), AddProductsForm.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menuExit:
                finish();
                System.exit(0);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        edtSearch       = findViewById(R.id.edtSearch);
        btnObject       = findViewById(R.id.btnObject);
        btnOccasion     = findViewById(R.id.btnOccasion);
        btnHoliday      = findViewById(R.id.btnHoliday);
        btnNew          = findViewById(R.id.btnNew);
        btnMoney        = findViewById(R.id.btnMoney);
        gvProducts      = findViewById(R.id.gridProducts);
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        productsList    = new ArrayList<>();
        searchProductsList = new ArrayList<>();
        productAdapter  = new ProductAdapter(this, R.layout.products, productsList);
        gvProducts.setAdapter(productAdapter);
        GetProducts();
    }

    private void ShowMenu(View view){
        switch (view.getId()) {
            case R.id.btnObject:
                PopupMenu popupMenu_doituong = new PopupMenu(this,btnObject);
                popupMenu_doituong.getMenuInflater().inflate(R.menu.menu_doituong, popupMenu_doituong.getMenu());
                popupMenu_doituong.show();
                popupMenu_doituong.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                break;
            case  R.id.btnOccasion:
                PopupMenu popupMenu_dip = new PopupMenu(this, btnOccasion);
                popupMenu_dip.getMenuInflater().inflate(R.menu.menu_dip, popupMenu_dip.getMenu());
                popupMenu_dip.show();
                popupMenu_dip.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                break;
            case R.id.btnHoliday:
                PopupMenu popupMenu_holiday = new PopupMenu(this, btnHoliday);
                popupMenu_holiday.getMenuInflater().inflate(R.menu.menu_holiday, popupMenu_holiday.getMenu());
                popupMenu_holiday.show();
                popupMenu_holiday.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                break;
            case R.id.btnMoney:
                PopupMenu popupMenu_money = new PopupMenu(this, btnMoney);
                popupMenu_money.getMenuInflater().inflate(R.menu.menu_money, popupMenu_money.getMenu());
                popupMenu_money.show();
                popupMenu_money.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                break;
            case R.id.btnNew:
                break;
        }
    }

    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAsc:
                Toast.makeText(this, "Bookmark", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDesc:
                Toast.makeText(this, "Upload", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.menuItem_facebook:
//                Toast.makeText(this, "Share Facebook", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.menuItem_instagram:
//                Toast.makeText(this, "Share Instagram", Toast.LENGTH_SHORT).show();
//                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void GetProducts() {
        productsList.clear();
        CollectionReference productRefs = fStore.collection("Products");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String name         = (String) document.get("name");
                        String price        = (String) document.get("price");
                        String description  = (String) document.get("description");
                        String createAt     = (String) document.get("createAt");
                        Integer quantity    =  Integer.parseInt(document.get("quantity").toString());
                        String holiday      = (String) document.get("holiday");
                        String object       = (String) document.get("object");
                        String occasion     = (String) document.get("occasion");
                        String imgUrl       =  document.getString("imageUrl");
                        Products product = new Products(id, name, price, imgUrl, description, createAt, quantity, holiday, object, occasion);
                        productsList.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Error");
            }
        });
    }
}

package com.example.giftsapp.Controller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;
import com.example.giftsapp.Adapter.products_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProductsForm extends AppCompatActivity{
    GridView gvProducts;
    products_adapter productAdapter;
    ImageButton btnOccasion, btnObject, btnHoliday, btnNew, btnMoney;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    List<Products> productsList;

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
        /*actionBar.setTitle(Html.fromHtml("<font color='#ff0000'>ActionBartitle </font>"));*/

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSettings:
                break;
            case  R.id.menuReload:
                productsList.clear();
                getProducts();
                break;
            case R.id.menuAdd:
                Intent intent = new Intent(getApplicationContext(), AddProductsForm.class);
                startActivity(intent);
                break;
            case R.id.menuExit:
                System.exit(0);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void DialogAdd(){
        Dialog dialog_add = new Dialog(this);
        dialog_add.setContentView(R.layout.dialog_addpro);
        dialog_add.show();
    }

    private void Init() {
        btnObject = findViewById(R.id.btnObject);
        btnOccasion = findViewById(R.id.btnOccasion);
        btnHoliday = findViewById(R.id.btnHoliday);
        btnNew = findViewById(R.id.btnNew);
        btnMoney = findViewById(R.id.btnMoney);
        gvProducts = findViewById(R.id.gridProducts);
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageRef = fStorage.getReference();
        productsList = new ArrayList<>();
        productAdapter = new products_adapter(this, R.layout.products, productsList);
        gvProducts.setAdapter(productAdapter);
        getProducts();
    }

    private void ShowMenu(View view){
        switch (view.getId()) {
            case R.id.btnObject:
                PopupMenu popupMenu_doituong = new PopupMenu(this,btnObject);
                popupMenu_doituong.getMenuInflater().inflate(R.menu.menu_doituong, popupMenu_doituong.getMenu());
                popupMenu_doituong.show();
                break;
            case  R.id.btnOccasion:
                PopupMenu popupMenu_dip = new PopupMenu(this, btnOccasion);
                popupMenu_dip.getMenuInflater().inflate(R.menu.menu_dip, popupMenu_dip.getMenu());
                popupMenu_dip.show();
                break;
            case R.id.btnHoliday:
                PopupMenu popupMenu_holiday = new PopupMenu(this, btnHoliday);
                popupMenu_holiday.getMenuInflater().inflate(R.menu.menu_holiday, popupMenu_holiday.getMenu());
                popupMenu_holiday.show();
                break;
            case R.id.btnNew:
                break;
            case R.id.btnMoney:
                PopupMenu popupMenu_money = new PopupMenu(this, btnMoney);
                popupMenu_money.getMenuInflater().inflate(R.menu.menu_money, popupMenu_money.getMenu());
                popupMenu_money.show();
                break;
        }
    }

    public void getProducts() {
        CollectionReference productRefs = fStore.collection("Products");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String name         = (String) document.get("name");
                        String price        = (String) document.get("price");
                        String description  = (String) document.get("description");
                        Integer quantity    =  Integer.parseInt(document.get("quantity").toString());
                        String holiday      = (String) document.get("holiday");
                        String object       = (String) document.get("object");
                        String occasion     = (String) document.get("occasion");
                        String imgUrl       =  document.getString("imageUrl");
                        Products product = new Products(name, price, imgUrl, description, quantity, holiday, object, occasion);
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

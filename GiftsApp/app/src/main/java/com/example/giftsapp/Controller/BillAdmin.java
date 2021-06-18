package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.giftsapp.Adapter.ViewPagerAdapter;
import com.example.giftsapp.Model.Bill;
import com.example.giftsapp.Model.Products;
import com.example.giftsapp.Model.StatusBill;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class BillAdmin extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ViewPagerAdapter viewPagerAdapter;
    ArrayList<Bill> billsArrayList;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_admin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quản lý đơn hàng");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.supply1);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));
        Init();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menuReload:
                startActivity(new Intent(this, BillAdmin.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminHome.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reload, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void Init() {
        fStore = FirebaseFirestore.getInstance();
        billsArrayList = new ArrayList<>();
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        GetDataFromFireStore();
    }

    private void GetDataFromFireStore() {
        billsArrayList.clear();
        fStore.collection("Bill").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<Map<String, Object>> billArrayList = new ArrayList<>();
                                    billArrayList.add(document.getData());
                                    ArrayList<Map<String, Object>> statusArrayList = (ArrayList<Map<String, Object>>) billArrayList.get(0).get("status");
                                    ArrayList<StatusBill> statusBillArrayList = new ArrayList<>();
                                    for (int i = 0; i < statusArrayList.size(); i++) {
                                        Boolean isPresent = Boolean.valueOf(statusArrayList.get(i).get("isPresent").toString());
                                        String name = statusArrayList.get(i).get("name").toString();
                                        Timestamp ts = (Timestamp) statusArrayList.get(i).get("createAt");
                                        Date createAt = ts.toDate();
                                        StatusBill status = new StatusBill(isPresent, name, createAt);
                                        statusBillArrayList.add(status);
                                    }

                                    ArrayList<Map<String, Object>> productArrayList = (ArrayList<Map<String, Object>>) billArrayList.get(0).get("products");
                                    ArrayList<Products> productsArrayList = new ArrayList<>();
                                    for (int i = 0; i < productArrayList.size(); i++) {
                                        String id = productArrayList.get(i).get("productID").toString();
                                        String name = productArrayList.get(i).get("name").toString();
                                        String price = productArrayList.get(i).get("price").toString();
                                        String imgUrl = productArrayList.get(i).get("imageUrl").toString();
                                        int quantity = Integer.parseInt(productArrayList.get(i).get("quantity").toString());
                                        Products product = new Products(id, name, price, imgUrl, quantity);
                                        productsArrayList.add(product);
                                    }

                                    String id = document.getId();
                                    String addressID = billArrayList.get(0).get("addressID").toString();
                                    Timestamp ts = (Timestamp) billArrayList.get(0).get("createAt");
                                    Date createAt = ts.toDate();
                                    String paymentType = billArrayList.get(0).get("paymentType").toString();
                                    int quantity = Integer.parseInt(billArrayList.get(0).get("quantityProduct").toString());
                                    String totalPrice = billArrayList.get(0).get("totalPrice").toString();
                                    String uID = billArrayList.get(0).get("userID").toString();
                                    String feeShip = billArrayList.get(0).get("feeShip").toString();
                                    String message = billArrayList.get(0).get("message").toString();
                                    Bill bill = new Bill(id, addressID, createAt, paymentType, productsArrayList, statusBillArrayList, totalPrice, uID, quantity, feeShip, message);
                                    billsArrayList.add(bill);
                                }
                            }
                            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), true, billsArrayList);
                            viewPager.setAdapter(viewPagerAdapter);
                            tabLayout.setupWithViewPager(viewPager);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
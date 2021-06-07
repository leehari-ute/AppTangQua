package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giftsapp.Adapter.ProductAdapter;
import com.example.giftsapp.Adapter.StatusAdapter;
import com.example.giftsapp.Model.Bill;
import com.example.giftsapp.Model.Helper;
import com.example.giftsapp.Model.Products;
import com.example.giftsapp.Model.StatusBill;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLEngineResult;

public class OrderDetailsActivity extends AppCompatActivity {

    Bill bill;
    View includeProductLayout, includeAddressLayout, includePriceLayout;
    ListView listViewProduct, listViewStatus;
    ArrayList<Products> productsArrayList;
    ArrayList<StatusBill> statusBillArrayList;
    ProductAdapter productAdapter;
    StatusAdapter statusAdapter;
    TextView txtName, txtPhone, txtDetailAddress, txtVillage, txtDistrict, txtProvince,
            txtTotalProduct, txtTotalPrice, txtPriceProduct, txtFeeShip;
    FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Init();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            bill = bundle.getParcelable("PARCEL_BILL");
            Log.d("CUCCUNG", "sl=>" + bill.getQuantityProduct());
            productsArrayList.addAll(bill.getProductsArrayList());
            productAdapter.notifyDataSetChanged();
            Helper.getListViewProductSize(listViewProduct);

            statusBillArrayList.addAll(bill.getStatus());
            statusAdapter.notifyDataSetChanged();
            Helper.getListViewStatusSize(listViewStatus);

            GetAddress();
            GetPrice();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(), SettingAccountForm.class);
            intent.putExtra("EXTRA_DOCUMENT_OPEN_BILL", true);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SettingAccountForm.class);
        intent.putExtra("EXTRA_DOCUMENT_OPEN", "Bill");
        startActivity(intent);
        finish();
    }

    private void Init() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }
        userID = user.getUid();
        includeProductLayout = findViewById(R.id.layoutProduct);
        listViewProduct = includeProductLayout.findViewById(R.id.listViewProduct);
        listViewStatus = includeProductLayout.findViewById(R.id.listViewStatus);
        productsArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), R.layout.list_view_product_bill, productsArrayList);
        listViewProduct.setAdapter(productAdapter);
        statusBillArrayList = new ArrayList<>();
        statusAdapter = new StatusAdapter(getApplicationContext(), R.layout.list_view_status, statusBillArrayList);
        listViewStatus.setAdapter(statusAdapter);


        includeAddressLayout = findViewById(R.id.layoutAddress);
        txtName = includeAddressLayout.findViewById(R.id.txtName);
        txtPhone = includeAddressLayout.findViewById(R.id.txtPhone);
        txtDetailAddress = includeAddressLayout.findViewById(R.id.txtDetailAddress);
        txtVillage = includeAddressLayout.findViewById(R.id.txtVillage);
        txtDistrict = includeAddressLayout.findViewById(R.id.txtDistrict);
        txtProvince = includeAddressLayout.findViewById(R.id.txtProvince);

        includePriceLayout = findViewById(R.id.layoutPrice);
        txtTotalProduct = includePriceLayout.findViewById(R.id.txtTotalProduct);
        txtTotalPrice = includePriceLayout.findViewById(R.id.txtTotalPrice);
        txtPriceProduct = includePriceLayout.findViewById(R.id.txtPriceProduct);
        txtFeeShip = includePriceLayout.findViewById(R.id.txtFeeShip);
    }

    private void GetAddress() {
        fStore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.getData().get("address") != null) {
                        ArrayList<Map<String, Object>> addressArray = (ArrayList<Map<String, Object>>) document.getData().get("address");
                        for (int i = 0; i < addressArray.size(); i++) {
                            if (addressArray.get(i).get("ID").toString() == bill.getAddressID()) {
                                txtName.setText(addressArray.get(i).get("name").toString().trim());
                                txtPhone.setText(addressArray.get(i).get("phone").toString().trim());
                                txtProvince.setText(addressArray.get(i).get("province").toString().trim());
                                txtDistrict.setText(addressArray.get(i).get("district").toString().trim());
                                txtVillage.setText(addressArray.get(i).get("village").toString().trim());
                                txtDetailAddress.setText(addressArray.get(i).get("detailAddress").toString().trim());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void GetPrice() {
        txtTotalProduct.setText("Giá ( " + bill.getQuantityProduct() + " sp)");
        txtTotalPrice.setText(bill.getTotalPrice() + " VND");
        Integer priceAllProduct = Integer.parseInt(bill.getTotalPrice()) - Integer.parseInt(bill.getFeeShip());
        txtPriceProduct.setText(priceAllProduct + "");
        txtFeeShip.setText(bill.getFeeShip() + " VND");
    }
}
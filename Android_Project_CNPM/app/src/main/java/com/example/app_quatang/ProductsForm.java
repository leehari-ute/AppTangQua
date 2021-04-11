package com.example.app_quatang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProductsForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_form);
        getSupportActionBar().setTitle("Products");
    }
}
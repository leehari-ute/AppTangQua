package com.example.app_quatang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Products_form extends AppCompatActivity {

    GridView gvProducts;
    ArrayList<Products> arrayP;
    products_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_form);
        getSupportActionBar().setTitle("Products");
        Init();
        adapter = new products_adapter(this,R.layout.products, arrayP);
        gvProducts.setAdapter(adapter);


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
            case R.id.menuExit:
                System.exit(0);
               // break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void Init(){
        gvProducts = findViewById(R.id.gridProducts);
        arrayP = new ArrayList<>();
        arrayP.add(new Products("Teddy","150.000 đồng",R.drawable.teddy));
        arrayP.add(new Products("Son","200.000 đồng",R.drawable.son));
        arrayP.add(new Products("Ví nam","250.000 đồng",R.drawable.vi));
        arrayP.add(new Products("Xe đồ chơi","100.000 đồng",R.drawable.xe));
        arrayP.add(new Products("Đồng hồ nữ","200.000 đồng",R.drawable.donghonu));
    }


}
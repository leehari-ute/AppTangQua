package com.example.giftsapp.Controller;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;
import com.example.giftsapp.Adapter.products_adapter;

import java.util.ArrayList;

public class ProductsForm extends AppCompatActivity{
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

package com.example.giftsapp.Controller;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toolbar;

import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;
import com.example.giftsapp.Adapter.products_adapter;

import java.util.ArrayList;

public class ProductsForm extends AppCompatActivity{
    GridView gvProducts;
    ArrayList<Products> arrayP;
    products_adapter adapter;
    ImageButton  btnDoituong;
    ImageButton btnDip, btnHoliday, btnNew, btnMoney;


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
        adapter = new products_adapter(this,R.layout.products, arrayP);
        gvProducts.setAdapter(adapter);

        Init_object();

        btnDoituong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu(v);
            }
        });

        btnDip.setOnClickListener(new View.OnClickListener() {
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

    private  void Init(){

        gvProducts = findViewById(R.id.gridProducts);
        arrayP = new ArrayList<>();
        arrayP.add(new Products("Teddy","150.000 đồng",R.drawable.teddy));
        arrayP.add(new Products("Son","200.000 đồng",R.drawable.son));
        arrayP.add(new Products("Ví nam","250.000 đồng",R.drawable.vi));
        arrayP.add(new Products("Xe đồ chơi","100.000 đồng",R.drawable.xe));
        arrayP.add(new Products("Đồng hồ nữ","200.000 đồng",R.drawable.donghonu));
    }

    private  void Init_object(){
        btnDoituong = findViewById(R.id.btnDoituong);
        btnDip = findViewById(R.id.btnDip);
        btnHoliday = findViewById(R.id.btnHoliday);
        btnNew = findViewById(R.id.btnNew);
        btnMoney = findViewById(R.id.btnMoney);

    }

    private void ShowMenu(View view){
        switch (view.getId()) {
            case R.id.btnDoituong:
                PopupMenu popupMenu_doituong = new PopupMenu(this,btnDoituong);
                popupMenu_doituong.getMenuInflater().inflate(R.menu.menu_doituong, popupMenu_doituong.getMenu());
                popupMenu_doituong.show();
                break;
            case  R.id.btnDip:
                PopupMenu popupMenu_dip = new PopupMenu(this,btnDip);
                popupMenu_dip.getMenuInflater().inflate(R.menu.menu_dip, popupMenu_dip.getMenu());
                popupMenu_dip.show();
                break;
            case R.id.btnHoliday:
                PopupMenu popupMenu_holiday = new PopupMenu(this,btnHoliday);
                popupMenu_holiday.getMenuInflater().inflate(R.menu.menu_holiday, popupMenu_holiday.getMenu());
                popupMenu_holiday.show();
                break;
            case R.id.btnNew:
                break;
            case R.id.btnMoney:
                PopupMenu popupMenu_money = new PopupMenu(this,btnMoney);
                popupMenu_money.getMenuInflater().inflate(R.menu.menu_money, popupMenu_money.getMenu());
                popupMenu_money.show();
                break;
        }

    }
}

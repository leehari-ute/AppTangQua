package com.example.giftsapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.giftsapp.R;

public class CustomerHome extends AppCompatActivity {

    ImageButton imgMenu ;
    ImageView imgBuy;
    ImageView imgInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_home);
        AnhXa();
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

        imgBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SettingAccountForm.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void AnhXa()
    {
        imgMenu = (ImageButton) findViewById(R.id.imageMenu);
        imgBuy = (ImageView) findViewById(R.id.imageBuy);
        imgInfor = findViewById(R.id.infor);
    }

    public void showMenu()
    {
        PopupMenu popupMenu = new PopupMenu(this,imgMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_customer,popupMenu.getMenu());
        popupMenu.show();
    }
}
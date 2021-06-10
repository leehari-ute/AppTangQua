package com.example.giftsapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.giftsapp.Adapter.MainAdapter;
import com.example.giftsapp.Model.MainModel;
import com.example.giftsapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {

    ViewFlipper             flipper;
    RecyclerView            recyclerview;
    ArrayList<MainModel>    mainModel;
    MainAdapter             mainAdapter;
    TextView                txtLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        int images[] = {R.drawable.quatangmot,R.drawable.quatanghai,R.drawable.quatangba};
        // tạo mảng icon
        int rcImages[] = {R.drawable.contact,R.drawable.documents,R.drawable.product,R.drawable.profile};
        //tao mảng tên cac icon tuong ứng
        String iconName[] = {"Liên hệ","Hóa đơn","Sản phẩm","Thông tin"};
        Init();
        for(int image:images) // phần carousel
        {
            FlipperImage(image);
        }

        // tạo Arraylist
        int l = iconName.length;
        mainModel = new ArrayList<>();
        for(int i = 0; i < l; i++)
        {
            MainModel model = new MainModel(rcImages[i], iconName[i]);
            mainModel.add(model);
        }

        // thiết kế horizantal layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        // khoi tao MainAdapter
        mainAdapter = new MainAdapter(AdminHome.this,mainModel);
        recyclerview.setAdapter(mainAdapter);


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

    }

    public void Init()
    {
        flipper         = findViewById(R.id.viewFlipperImages);
        recyclerview    = findViewById(R.id.recycleImages);
        txtLogout       = findViewById(R.id.txtLogout);
    }

    public void FlipperImage(int image){
        ImageView imageview = new ImageView(this);
        imageview.setBackgroundResource(image);
        flipper.addView(imageview);
        flipper.setFlipInterval(3000); // 3s thì chuyển
        flipper.setAutoStart(true);

        flipper.setInAnimation(this,android.R.anim.slide_in_left);
        flipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginForm.class));
        finish();
    }

}
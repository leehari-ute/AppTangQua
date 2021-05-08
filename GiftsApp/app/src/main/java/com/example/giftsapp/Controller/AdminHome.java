package com.example.giftsapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.giftsapp.Adapter.MainAdapter;
import com.example.giftsapp.Model.MainModel;
import com.example.giftsapp.R;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {

    ViewFlipper flipper;
    RecyclerView rcview;
    ArrayList<MainModel> mainModel;
    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        int images[] = {R.drawable.quatangmot,R.drawable.quatanghai,R.drawable.quatangba};
        // tạo mảng icon
        int rcImages[] = {R.drawable.contact,R.drawable.documents,R.drawable.product,R.drawable.profile};
        //tao mảng tên cac icon tuong ứng
        String iconName[] = {"Liên hệ","Chính sách","Sản phẩm","Thông tin"};
        AnhXa();
        for(int image:images) // phần carousel
        {
            flipperImage(image);
        }

        // tạo Arraylist
        int l = iconName.length;
        mainModel = new ArrayList<>();
        for(int i=0;i<l;i++)
        {
            MainModel model = new MainModel(rcImages[i],iconName[i]);
            mainModel.add(model);
        }

        // thiết kế horizantal layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rcview.setLayoutManager(layoutManager);
        rcview.setItemAnimator(new DefaultItemAnimator());
        // khoi tao MainAdapter
        mainAdapter = new MainAdapter(AdminHome.this,mainModel);
        rcview.setAdapter(mainAdapter);

    }

    public void AnhXa()
    {
        flipper = (ViewFlipper) findViewById(R.id.viewFlipperImages);
        rcview = (RecyclerView) findViewById(R.id.recycleImages);
    }
    public void flipperImage(int image){
        ImageView imageview = new ImageView(this);
        imageview.setBackgroundResource(image);
        flipper.addView(imageview);
        flipper.setFlipInterval(3000); // 3s thì chuyển
        flipper.setAutoStart(true);

        flipper.setInAnimation(this,android.R.anim.slide_in_left);
        flipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }

}
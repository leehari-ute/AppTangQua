package com.example.giftsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Home extends AppCompatActivity {

    ViewFlipper flipper;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        int images[] = {R.drawable.quatangmot,R.drawable.quatanghai,R.drawable.quatangba};
        flipper = (ViewFlipper) findViewById(R.id.viewFlipperImage);
        imageView = (ImageView) findViewById(R.id.imageProduct);

        for(int image:images)
        {
            flipperImage(image);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DenTrangMuaHang();
            }
        });
    }

    private void DenTrangMuaHang()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Đến trang đặt hàng");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setMessage("Bạn có muốn chuyển đến trang đặt hàng không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), ProductsForm.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
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
package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerHome extends AppCompatActivity {

    ImageButton         imgMenu ;
    ImageView           imgBuy, imgInfor;
    TextView            txtHelloCustomer;
    FirebaseAuth        fAuth;
    FirebaseFirestore   fStore;
    FirebaseUser        user;
    String              userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_home);


        Init();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
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

    private void Init() {
        imgMenu             = (ImageButton) findViewById(R.id.imgMenu);
        imgBuy              = (ImageView) findViewById(R.id.imgBuy);
        imgInfor            = findViewById(R.id.imgInformation);
        txtHelloCustomer    = findViewById(R.id.txtHelloCustomer);
        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();
        user                = fAuth.getCurrentUser();
        userID              = user.getUid();
        LoadData();
    }

    public void ShowMenu() {
        PopupMenu popupMenu = new PopupMenu(this, imgMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_customer,popupMenu.getMenu());
        popupMenu.show();
    }

    public void LogOut(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginForm.class));
        finish();
    }

    private void LoadData() {
        DocumentReference docRef = fStore.collection("Users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        txtHelloCustomer.setText("Xin chào, " + document.getString("fullName"));
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

}
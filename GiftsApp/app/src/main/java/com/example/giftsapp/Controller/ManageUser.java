package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giftsapp.Adapter.UserAdapter;
import com.example.giftsapp.Model.Address;
import com.example.giftsapp.Model.User;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageUser extends AppCompatActivity {

    TextView txtAmountUser;
    ListView listViewUsers;
    UserAdapter userAdapter;
    ArrayList<User> userArrayList;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quản lý người dùng");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.supply1);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        Init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminHome.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), AdminHome.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        fStore = FirebaseFirestore.getInstance();
        txtAmountUser = findViewById(R.id.txtAmountUser);
        listViewUsers = findViewById(R.id.listViewUsers);
        userArrayList = new ArrayList<>();
        userAdapter = new UserAdapter(getApplicationContext(), R.layout.listview_user, userArrayList);
        listViewUsers.setAdapter(userAdapter);
        LoadUsers();
    }

    private void LoadUsers() {
        fStore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = new User(document.getId(), document.getString("avtUrl"), document.getString("bio"),
                                    document.getString("email"), document.getString("fullName"), document.getString("gender"),
                                    document.getString("phone"), document.getString("role"), null);

                            userArrayList.add(user);
                        }
                        userAdapter.notifyDataSetChanged();
                        txtAmountUser.setText("Hệ thống có " + userArrayList.size()+" người dùng");
                    }
                }
            }
        });
    }
}
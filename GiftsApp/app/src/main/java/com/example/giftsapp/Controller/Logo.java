package com.example.giftsapp.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.giftsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Timer;
import java.util.TimerTask;

public class Logo extends AppCompatActivity {

    String userID = "";
    private FirebaseAuth    fAuth;
    private FirebaseFirestore fStore;
    int time = 3;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Init();
        StartTimer();
    }

    private void Init() {
        fAuth   = FirebaseAuth.getInstance();
        fStore  = FirebaseFirestore.getInstance();
    }

    private void CheckRole() {
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String role = value.getString("role");
                Intent intent = new Intent();
                switch (role) {
                    case "Customer":
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case "Admin":
                        intent = new Intent(getApplicationContext(), AdminHome.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void StartTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (time == 1) {
                    timer.cancel();
                    timer.purge();
                    if (fAuth.getCurrentUser() == null) {
                        startActivity(new Intent(getApplicationContext(), LoginForm.class));
                        finish();
                    } else {
                        CheckRole();
                    }
                } else {
                    time--;
                }
            }
        }, 1000, 1000);

    }
}
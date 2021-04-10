package com.example.app_quatang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login_form extends AppCompatActivity {

    TextView txtviewDKN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login");
        Init();

        txtviewDKN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Signup_form.class);
                startActivity(intent);
            }
        });


    }

    private void Init(){
        txtviewDKN = findViewById(R.id.textViewDKN);
    }
}
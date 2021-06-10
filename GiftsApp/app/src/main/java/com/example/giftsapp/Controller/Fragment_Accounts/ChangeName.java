package com.example.giftsapp.Controller.Fragment_Accounts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.giftsapp.Controller.LoginForm;
import com.example.giftsapp.Controller.SettingAccountAdmin;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeName extends AppCompatActivity {

    EditText            edtFullName;
    Button              btnSave;
    FirebaseAuth        fAuth;
    FirebaseFirestore   fStore;
    String              userID, name = "";
    boolean isFromAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sửa tên");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));


        Init();

        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtFullName.getText().toString().trim();
                if (name.equals("")) {
                    edtFullName.setError("Tên không được bỏ trống");
                    return;
                };
                edtFullName.setError(null);
                ChangeUserName();
                if (isFromAdmin) {
                    startActivity(new Intent(getApplicationContext(), SettingAccountAdmin.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), SettingAccountForm.class));
                }
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (isFromAdmin) {
                    startActivity(new Intent(this, SettingAccountAdmin.class));
                } else {
                    startActivity(new Intent(this, SettingAccountForm.class));
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        edtFullName = findViewById(R.id.edtFullName);
        btnSave     = findViewById(R.id.btnSave);
        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();
        userID      = fAuth.getCurrentUser().getUid();
        edtFullName.setText(getIntent().getStringExtra("EXTRA_DOCUMENT_USER_NAME"));
        isFromAdmin = getIntent().getBooleanExtra("EXTRA_IS_FROM_ADMIN", false);
    }

    private void ChangeUserName() {
        fStore.collection("Users").document(userID).update("fullName", name)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "DocumentSnapshot successfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error updating document", e);
            }
        });
    }
}
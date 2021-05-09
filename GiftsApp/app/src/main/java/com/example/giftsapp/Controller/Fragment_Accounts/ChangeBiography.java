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
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeBiography extends AppCompatActivity {

    EditText            edtBio;
    Button              btnSave;
    FirebaseAuth        fAuth;
    FirebaseFirestore   fStore;
    String              userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_biography);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sửa tiểu sử");
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
                ChangeName();
                startActivity(new Intent(getApplicationContext(), SettingAccountForm.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        edtBio      = findViewById(R.id.edtBio);
        btnSave     = findViewById(R.id.btnSave);
        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();
        userID      = fAuth.getCurrentUser().getUid();
        edtBio.setText(getIntent().getStringExtra("EXTRA_DOCUMENT_USER_BIO"));
    }

    private void ChangeName() {
        fStore.collection("Users").document(userID).update("bio", edtBio.getText().toString().trim())
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
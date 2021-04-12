package com.example.app_quatang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginForm extends AppCompatActivity {

    private EditText        edtEmail, edtPassword;
    private Button          btnLogin;
    private TextView        txtRegisterNow;
    private FirebaseAuth    fAuth;
    private String          password = null,
                            email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login");

        fAuth = FirebaseAuth.getInstance();
        Init();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");

        txtRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpForm.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });

    }

    private void Init() {
        edtEmail            = findViewById(R.id.edtEmail);
        edtPassword         = findViewById(R.id.edtPassword);
        txtRegisterNow      = findViewById(R.id.txtRegisterNow);
        btnLogin            = findViewById(R.id.btnLogin);
    }

    private void login() {
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email is required");
            return;
        }
        edtEmail.setError(null);

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            return;
        }
        edtPassword.setError(null);

        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginForm.this, "Success!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SignUpForm.class);
                    startActivity(intent);
                    return;
                }

                Toast.makeText(LoginForm.this, "Error!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ShowHidePass(View view) {
        if(view.getId() == R.id.imgShowPass) {
            if(edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView)(view)).setImageResource(R.drawable.visible_password);
                //Show Password
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                return;
            }

            ((ImageView)(view)).setImageResource(R.drawable.invisible_password);
            //Hide Password
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
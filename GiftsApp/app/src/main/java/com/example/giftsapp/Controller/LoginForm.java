    package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

    public class LoginForm extends AppCompatActivity {

    private EditText        edtEmail, edtPassword;
    private Button          btnLogin;
    private TextView        txtRegisterNow, txtForgotPassword;
    private FirebaseAuth    fAuth;
    private FirebaseFirestore fStore;
    private String          password = "",
                            email = "",
                            userID = "";

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Init();

        if (fAuth.getCurrentUser() != null) {
            CheckRole();
            finish();
        }

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
                Login();
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword(v);
            }
        });


    }

    private void Init() {
        edtEmail            = findViewById(R.id.edtEmail);
        edtPassword         = findViewById(R.id.edtPassword);
        txtRegisterNow      = findViewById(R.id.txtRegisterNow);
        txtForgotPassword   = findViewById(R.id.txtForgotPassword);
        btnLogin            = findViewById(R.id.btnLogin);
        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();
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
            }
        });
    }

    private void Login() {
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
                            Toast.makeText(LoginForm.this, "Success", Toast.LENGTH_SHORT).show();
                            CheckRole();
                            return;
                        }
                        Toast.makeText(LoginForm.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void ShowHidePass(View view) {
        if(view.getId()==R.id.imgShowHidePass){
            if(edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.visible_password_2);

                //Show Password
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                return;
            }

            ((ImageView)(view)).setImageResource(R.drawable.invisible_password_2);
            //Hide Password
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void ForgotPassword(View v) {
        final EditText resetMail  = new EditText(v.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Forgot Password");
        passwordResetDialog.setMessage("Enter your email to reset password");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LoginForm.this, "Reset link was sent to your email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginForm.this, "Error!!! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        passwordResetDialog.create().show();
    }
}
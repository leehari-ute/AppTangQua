    package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

    public class LoginForm extends AppCompatActivity {

    private EditText        edtEmail, edtPassword;
    private Button          btnLogin;
    private TextView        txtRegisterNow, txtForgotPassword;
    private FirebaseAuth    fAuth;
    private FirebaseFirestore fStore;
    private String          password = "",
                            email = "",
                            userID = "";

    public static String currentUser ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Init();

        if (fAuth.getCurrentUser() != null) {
            CheckRole();
        }

        txtRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this, SignUpForm.class);
                startActivity(intent);
                finish();
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
                ForgotPassword();
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
                finish();
            }
        });
        currentUser = userID;
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

        if (Validate()) {
            fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginForm.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                CheckRole();
                                return;
                            }
                            Toast.makeText(LoginForm.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
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

    private void ForgotPassword() {
        Dialog forgotPassDialog = new Dialog(LoginForm.this);
        forgotPassDialog.setContentView(R.layout.dialog_forgot_password);
        forgotPassDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        forgotPassDialog.setCancelable(false);
        EditText edtEmail = forgotPassDialog.findViewById(R.id.edtEmail);
        Button btnCancel = forgotPassDialog.findViewById(R.id.btnCancel);
        Button btnSend = forgotPassDialog.findViewById(R.id.btnSend);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassDialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Vui lòng nhập email");
                    return;
                }
                edtEmail.setError(null);

                fStore.collection("Users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                edtEmail.setError("Email không tồn tại");
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                    fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LoginForm.this, "Một liên kết đã được gửi tới email của bạn", Toast.LENGTH_SHORT).show();
                                            forgotPassDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            edtEmail.setError("Lỗi. Vui lòng thử lại");
                                        }
                                    });
                                }
                            }
                        } else {
                            edtEmail.setError(task.getException().getMessage());
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        });


        forgotPassDialog.show();
    }

    private boolean Validate() {
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email is required");
            return false;
        }
        edtEmail.setError(null);

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            return false;
        }
        edtPassword.setError(null);
        return true;
    }
}
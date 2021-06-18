package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpForm extends AppCompatActivity {

    private Button          btnSignUp;
    private RadioButton     rdBtnMale, rdBtnFemale;
    private RadioGroup      rdGender;
    private EditText        edtFullName, edtPassword, edtEmail, edtPhone;
    private FirebaseAuth    fAuth;
    private FirebaseFirestore fStore;
    private String          gender = null,
                            fullName = null,
                            password = null,
                            email = null,
                            phone = null,
                            userID = null;
    private TextView        txtLoginHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Init();
        

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        txtLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Init() {
        btnSignUp       = findViewById(R.id.btnSignUp);
        rdBtnMale       = findViewById(R.id.rdBtnMale);
        rdBtnFemale     = findViewById(R.id.rdBtnFemale);
        rdGender        = findViewById(R.id.rdGender);
        edtFullName     = findViewById(R.id.edtFullName);
        edtPassword     = findViewById(R.id.edtPassword);
        edtEmail        = findViewById(R.id.edtEmail);
        edtPhone        = findViewById(R.id.edtPhone);
        txtLoginHere    = findViewById(R.id.txtLoginHere);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fAuth.signOut();
    }

    @SuppressLint("NonConstantResourceId")
    private void Register() {
        fullName     = edtFullName.getText().toString().trim();
        email        = edtEmail.getText().toString().trim();
        password     = edtPassword.getText().toString().trim();
        phone        = edtPhone.getText().toString().trim();

        switch (rdGender.getCheckedRadioButtonId()) {
            case R.id.rdBtnMale:
                gender = "Nam";
                break;
            case R.id.rdBtnFemale:
                gender = "Nữ";
                break;
            default:
                break;
        }

        if (Validate()) {
            CheckExistUser();
        }
    }

    private boolean Validate() {
        if (TextUtils.isEmpty(gender)) {
            rdBtnMale.setError("Hãy chọn giới tính");
            rdBtnFemale.setError("Hãy chọn giới tính");
            return false;
        }
        rdBtnMale.setError(null);
        rdBtnFemale.setError(null);

        if (TextUtils.isEmpty(fullName)) {
            edtFullName.setError("Hãy nhập tên người dùng");
            return false;
        }
        edtFullName.setError(null);

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Hãy nhập email");
            return false;
        }
        edtEmail.setError(null);

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Hãy nhập mật khẩu");
            return false;
        }
        edtPassword.setError(null);

        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Hãy nhập số điện thoại");
            return false;
        }
        edtPhone.setError(null);

        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải có hơn 5 ký tự");
            return false;
        }
        edtPassword.setError(null);
        return true;
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

    private void CheckRole() {
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    String role = value.getString("role");
                    if (role != null) {
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
                }
            }
        });
    }

    private void CheckExistUser() {
        edtEmail.setError(null);
        fStore.collection("Users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!Objects.requireNonNull(task.getResult()).isEmpty()) {
                        Toast.makeText(SignUpForm.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                        edtEmail.setError("Email đã tồn tại");
                    } else {
                        fStore.collection("Users").whereEqualTo("phone", phone).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (!Objects.requireNonNull(task.getResult()).isEmpty()) {
                                    Toast.makeText(SignUpForm.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                                    edtPhone.setError("Số điện thoại đã tồn tại");
                                } else {
                                    AddUser();
                                }
                            }
                        });
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void AddUser() {
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String, Object> User = new HashMap<>();
                            User.put("fullName", fullName);
                            User.put("email", email);
                            User.put("phone", phone);
                            User.put("gender", gender);
                            User.put("role", "Customer");
                            User.put("bio", "");
                            User.put("birthday", "28/02/2000");
                            ArrayList<String> address = new ArrayList<>();
                            User.put("address", address);
                            if (gender.equals("Nam")) {
                                User.put("avtUrl", "https://firebasestorage.googleapis.com/v0/b/android-project-se.appspot.com/o/default%2FavtMale.jpg?alt=media&token=868c8c7b-21af-4f78-bfa2-41b9d154c0e6");
                            } else {
                                User.put("avtUrl", "https://firebasestorage.googleapis.com/v0/b/android-project-se.appspot.com/o/default%2FavtFemale.jpg?alt=media&token=757d5538-61f2-4096-a74b-e2fc0e667012");
                            }
                            documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user profile is create for "+ userID);
                                    Toast.makeText(SignUpForm.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                      fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                          @Override
                                          public void onSuccess(AuthResult authResult) {
                                              if (task.isSuccessful()) {
                                                  Toast.makeText(SignUpForm.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                  startActivity(new Intent(SignUpForm.this, MainActivity.class));
                                                  finish();
                                              }
                                          }
                                      });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: "+ e.toString());
                                }
                            });
                            return;
                        }
                        Toast.makeText(SignUpForm.this, "Lỗi: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpForm extends AppCompatActivity {

    private Button              btnSignUp;
    private RadioButton         rdBtnMale, rdBtnFemale;
    private RadioGroup          rdGender;
    private EditText            edtFullName, edtPassword, edtEmail, edtPhone;
    private TextView            txtLoginHere;

    private String              gender = null,
                                fullName = null,
                                password = null,
                                email = null,
                                phone = null;

    private FirebaseAuth        fAuth;
    private DatabaseReference   mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        getSupportActionBar().setTitle("SignUp");

        fAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
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
    }

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

        //validate();

        if (TextUtils.isEmpty(gender)) {
            rdBtnMale.setError("Gender is required");
            rdBtnFemale.setError("Gender is required");
            return;
        }
        rdBtnMale.setError(null);
        rdBtnFemale.setError(null);

        if (TextUtils.isEmpty(fullName)) {
            edtFullName.setError("Username is required");
            return;
        }
        edtFullName.setError(null);

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

        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Phone is required");
            return;
        }
        edtPhone.setError(null);

        if (password.length() < 6) {
            edtPassword.setError("Password must be more than 5 characters");
            return;
        }
        edtPassword.setError(null);

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpForm.this, "Success!!!", Toast.LENGTH_SHORT).show();
                    createNewUser();
                    return;
                }

                Toast.makeText(SignUpForm.this, "Error!!!", Toast.LENGTH_SHORT).show();
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

    private void createNewUser() {
        User user = new User(fullName, phone, email, gender);
        mData.child("User").push().setValue(user);
    }
    //bug :D :)
    private void validate() {
        if (TextUtils.isEmpty(gender)) {
            rdBtnMale.setError("Gender is required");
            rdBtnFemale.setError("Gender is required");
            return;
        }
        rdBtnMale.setError(null);
        rdBtnFemale.setError(null);

        if (TextUtils.isEmpty(fullName)) {
            edtFullName.setError("Username is required");
            return;
        }
        edtFullName.setError(null);

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

        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Phone is required");
            return;
        }
        edtPhone.setError(null);

        if (password.length() < 6) {
            edtPassword.setError("Password must be more than 5 characters");
            return;
        }
        edtPassword.setError(null);
    }
}
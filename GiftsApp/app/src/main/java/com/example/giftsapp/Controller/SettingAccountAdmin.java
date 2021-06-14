package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.giftsapp.Controller.Fragment_Accounts.ChangeBiography;
import com.example.giftsapp.Controller.Fragment_Accounts.ChangeName;
import com.example.giftsapp.Model.Revenue;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SettingAccountAdmin extends AppCompatActivity {

    TextView txtName, txtGender, txtBirthday, txtBio, txtPhone, txtEmail, txtChangePass, txtChangeAvt;
    ImageView imgAvt;
    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Uri                 filePath;
    final int           PICK_IMAGE_REQUEST = 71;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageRef;
    String              userID;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account_admin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cài đặt tài khoản");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.supply1);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        Init();

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingAccountAdmin.this, ChangeName.class);
                intent.putExtra("EXTRA_DOCUMENT_USER_NAME", txtName.getText().toString());
                intent.putExtra("EXTRA_IS_FROM_ADMIN", true);
                startActivity(intent);
            }
        });

        txtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Gender();
            }
        });


        txtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Birthday();
            }
        });

        txtBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingAccountAdmin.this, ChangeBiography.class);
                intent.putExtra("EXTRA_DOCUMENT_USER_BIO", txtBio.getText().toString());
                intent.putExtra("EXTRA_IS_FROM_ADMIN", true);
                startActivity(intent);
            }
        });

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword(v);
            }
        });

        txtChangeAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
    }

    private void Init() {
        txtChangePass   = findViewById(R.id.txtChangePass);
        txtChangeAvt    = findViewById(R.id.txtChangeAvt);
        txtName         = findViewById(R.id.txtName);
        txtGender       = findViewById(R.id.txtGender);
        txtBirthday     = findViewById(R.id.txtBirthday);
        txtBio          = findViewById(R.id.txtBio);
        txtPhone        = findViewById(R.id.txtPhone);
        txtEmail        = findViewById(R.id.txtEmail);
        imgAvt          = findViewById(R.id.imgAvt);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        storageRef      = FirebaseStorage.getInstance().getReference();
        userID          = fAuth.getCurrentUser().getUid();
        user            = fAuth.getCurrentUser();
        LoadUserInformation();
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

    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void Dialog_Gender() {
        Dialog dialog = new Dialog(SettingAccountAdmin.this);
        dialog.setContentView(R.layout.dialog_gender);
        TextView            txtMale     = dialog.findViewById(R.id.txtMale),
                txtFemale   = dialog.findViewById(R.id.txtFemale);

        txtMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeGender("Nam");
                dialog.dismiss();
            }
        });

        txtFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeGender("Nữ");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void Dialog_Birthday() {
        int date    = calendar.get(Calendar.DATE);
        int month   = calendar.get(Calendar.MONTH);
        int year    = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(SettingAccountAdmin.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                ChangeBirthday(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private void ForgotPassword(View v) {
        final EditText resetPass  = new EditText(v.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Đổi mật khẩu?");
        passwordResetDialog.setMessage("Nhập mật khẩu mới để đổi mật khẩu");
        passwordResetDialog.setView(resetPass);

        passwordResetDialog.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPassword = resetPass.getText().toString();
                if (newPassword.length() < 6) {
                    resetPass.setError("Mật khẩu phải có hơn 5 ký tự");
                    return;
                }
                resetPass.setError(null);
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SettingAccountAdmin.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SettingAccountAdmin.this, "Lỗi vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        passwordResetDialog.create().show();
    }

    private void LoadUserInformation() {
        DocumentReference docRef = fStore.collection("Users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        txtName.setText(document.getString("fullName"));
                        txtGender.setText(document.getString("gender"));
                        txtBio.setText(document.getString("bio"));
                        txtBirthday.setText(document.getString("birthday"));
                        txtPhone.setText(document.getString("phone"));
                        txtEmail.setText(document.getString("email"));
                        String avtUrl =  document.getString("avtUrl");
                        Glide.with(getApplicationContext())
                                .load(avtUrl)
                                .into(imgAvt);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgAvt.setImageBitmap(bitmap);
                UploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UploadImage() {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Đang tải lên...");
            progressDialog.show();

            StorageReference ref = storageRef.child("users/" + userID + "/profile.png");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(SettingAccountAdmin.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SettingAccountAdmin.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            GetImageUrl(ref);
        }
    }

    private void GetImageUrl(StorageReference ref) {
        UploadTask uploadTask = ref.putFile(filePath);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String imgUrl = downloadUri.toString();
                    fStore.collection("Users").document(userID).update("avtUrl", imgUrl)
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
                } else {
                    Toast.makeText(SettingAccountAdmin.this, "Failed "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ChangeGender(String gender) {
        fStore.collection("Users").document(userID).update("gender", gender)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                        LoadUserInformation();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error updating document", e);
            }
        });
    }

    private void ChangeBirthday(String birthday) {
        fStore.collection("Users").document(userID).update("birthday", birthday)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                        LoadUserInformation();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error updating document", e);
            }
        });
    }
}
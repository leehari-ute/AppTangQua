package com.example.giftsapp.Controller.Fragment_Accounts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.giftsapp.Controller.LoginForm;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Information extends Fragment {

    TextView            txtName, txtGender, txtBirthday, txtBio, txtPhone, txtEmail, txtChangePass, txtChangeAvt;
    ImageView           imgAvt;
    Calendar            calendar = Calendar.getInstance();
    SimpleDateFormat    simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    FirebaseAuth        fAuth;
    FirebaseFirestore   fStore;
    StorageReference    storageRef;
    String              userID;
    FirebaseUser        user;

    private SettingAccountForm settingAccountForm;

    public Information() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        Init(view);

        //txtBirthday.setText(simpleDateFormat.format(calendar.getTime()));

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingAccountForm, ChangeName.class);
                intent.putExtra("EXTRA_DOCUMENT_USER_NAME", txtName.getText().toString());
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
                Intent intent = new Intent(settingAccountForm, ChangeBiography.class);
                intent.putExtra("EXTRA_DOCUMENT_USER_BIO", txtBio.getText().toString());
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
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingAccountForm) {
            this.settingAccountForm = (SettingAccountForm) context;
        }
    }

    private void Init(View view) {
        txtChangePass   = view.findViewById(R.id.txtChangePass);
        txtChangeAvt    = view.findViewById(R.id.txtChangeAvt);
        txtName         = view.findViewById(R.id.txtName);
        txtGender       = view.findViewById(R.id.txtGender);
        txtBirthday     = view.findViewById(R.id.txtBirthday);
        txtBio          = view.findViewById(R.id.txtBio);
        txtPhone        = view.findViewById(R.id.txtPhone);
        txtEmail        = view.findViewById(R.id.txtEmail);
        imgAvt          = view.findViewById(R.id.imgAvt);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        storageRef      = FirebaseStorage.getInstance().getReference();
        userID          = fAuth.getCurrentUser().getUid();
        user            = fAuth.getCurrentUser();
        LoadUserInformation();
    }

    private void Dialog_Gender() {
        Dialog dialog = new Dialog(settingAccountForm);
        dialog.setContentView(R.layout.dialog_gender);
        TextView            txtMale     = dialog.findViewById(R.id.txtMale),
                            txtFemale   = dialog.findViewById(R.id.txtFemale);

        txtMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeGender("Nam");
                LoadUserInformation();
                dialog.dismiss();
            }
        });

        txtFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeGender("Nữ");
                LoadUserInformation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void Dialog_Birthday() {
        int date    = calendar.get(Calendar.DATE);
        int month   = calendar.get(Calendar.MONTH);
        int year    = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(settingAccountForm, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                ChangeBirthday(simpleDateFormat.format(calendar.getTime()));
                LoadUserInformation();
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private void ForgotPassword(View v) {
        final EditText resetPass  = new EditText(v.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Reset Password?");
        passwordResetDialog.setMessage("Enter new password to reset password");
        passwordResetDialog.setView(resetPass);

        passwordResetDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPassword = resetPass.getText().toString();
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(settingAccountForm, "Reset Password Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(settingAccountForm, "Reset Password Unsuccessfully", Toast.LENGTH_SHORT).show();
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
                        Glide.with(settingAccountForm)
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

    private void UploadAvtToStorage(Uri imgUri) {
        final ProgressDialog progressDialog = new ProgressDialog(settingAccountForm);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference fileRef = storageRef.child("users/" + userID + "/profile.png");
        fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
//                String urlDownload = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                UploadAvtToFireStore(imgUri);
                Toast.makeText(settingAccountForm, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(settingAccountForm, "Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                        .getTotalByteCount());
                progressDialog.setMessage("Uploaded "+(int)progress+"%");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imgUri = data.getData();
                UploadAvtToStorage(imgUri);
                Glide.with(settingAccountForm)
                        .load(imgUri)
                        .into(imgAvt);
            }
        }
    }

    private void UploadAvtToFireStore(Uri imgUri) {
        fStore.collection("Users").document(userID).update("avtUrl", imgUri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void ChangeGender(String gender) {
        fStore.collection("Users").document(userID).update("gender", gender)
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

    private void ChangeBirthday(String birthday) {
        fStore.collection("Users").document(userID).update("birthday", birthday)
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
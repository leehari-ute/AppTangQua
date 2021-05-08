package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EditProductForm extends AppCompatActivity {

    ArrayAdapter<String> holidayAdapter;
    ArrayAdapter<String> objectAdapter;
    ArrayAdapter<String> occasionAdapter;
    Button btnSave;
    EditText edtDes, edtNameProduct, edtQuantity, edtPrice;
    ImageView imgProduct;
    Spinner spnHoliday, spnObject, spnOccasion;
    List<String> holidayList , objectList, occasionList;
    Uri filePath;
    final int           PICK_IMAGE_REQUEST = 71;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    String              userID, productID;
    TextView txtHoliday, txtObject, txtOccasion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sửa sản phẩm");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.supply1);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageRef = fStorage.getReference();
        Init();

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProduct();
                Intent intent = new Intent(getApplicationContext(), ProductsForm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                Intent intent = new Intent(getApplicationContext(), ProductsForm.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        productID           = getIntent().getStringExtra("EXTRA_DOCUMENT_PRODUCT");
        btnSave             = findViewById(R.id.btnSave);
        edtDes              = findViewById(R.id.edtDesProduct);
        edtNameProduct      = findViewById(R.id.edtNameProduct);
        edtQuantity         = findViewById(R.id.edtQuantity);
        edtPrice            = findViewById(R.id.edtPrice);
        imgProduct          = findViewById(R.id.imgProduct);
        spnHoliday          = findViewById(R.id.spnHoliday);
        spnObject           = findViewById(R.id.spnObject);
        spnOccasion         = findViewById(R.id.spnOccasion);
        txtHoliday          = findViewById(R.id.txtHoliday);
        txtObject           = findViewById(R.id.txtObject);
        txtOccasion         = findViewById(R.id.txtOccasion);
        holidayList         = new ArrayList<>();
        objectList          = new ArrayList<>();
        occasionList        = new ArrayList<>();
        loadSpinner();
        LoadProduct();
    }

    private void loadSpinner() {
        holidayList.add("Valentine");
        holidayList.add("8/3");
        holidayList.add("Giáng sinh");
        objectList.add("Nam");
        objectList.add("Nữ");
        objectList.add("Bé");
        objectList.add("Nam & Nữ");
        occasionList.add("Sinh nhật");
        occasionList.add("Tân gia");

        holidayAdapter = new ArrayAdapter<String>(EditProductForm.this, android.R.layout.simple_spinner_item, holidayList);
        objectAdapter = new ArrayAdapter<String>(EditProductForm.this, android.R.layout.simple_spinner_item, objectList);
        occasionAdapter = new ArrayAdapter<String>(EditProductForm.this, android.R.layout.simple_spinner_item, occasionList);

        holidayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        objectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occasionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnHoliday.setAdapter(holidayAdapter);
        spnObject.setAdapter(objectAdapter);
        spnOccasion.setAdapter(occasionAdapter);
    }

    private void EditProduct() {
        String name         = edtNameProduct.getText().toString();
        String price        = edtPrice.getText().toString();
        String description  = edtDes.getText().toString();
        Integer quantity    = Integer.parseInt(edtQuantity.getText().toString());
        String holiday      = spnHoliday.getSelectedItem().toString();
        String object       = spnObject.getSelectedItem().toString();
        String occasion     = spnOccasion.getSelectedItem().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("price", price);
        map.put("description", description);
        map.put("quantity", quantity);
        map.put("holiday", holiday);
        map.put("object", object);
        map.put("occasion", occasion);

        fStore.collection("Products").document(productID).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UploadImage(productID);
                Log.d("TAG", "Successfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Unsuccessfully updated!");
            }
        });

    }

    private void LoadProduct() {
        DocumentReference docRef = fStore.collection("Products").document(productID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        edtNameProduct.setText(document.get("name").toString());
                        edtPrice.setText(document.get("price").toString());
                        edtDes.setText(document.get("description").toString());
                        edtQuantity.setText(document.get("quantity").toString());
                        spnHoliday.setSelection(holidayAdapter.getPosition(document.get("holiday").toString()));
                        spnObject.setSelection(objectAdapter.getPosition(document.get("object").toString()));
                        spnOccasion.setSelection(occasionAdapter.getPosition(document.get("occasion").toString()));
                        String imgUrl       =  document.getString("imageUrl");
                        Glide.with(getApplicationContext())
                                .load(imgUrl)
                                .into(imgProduct);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgProduct.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void UploadImage(String productID) {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("images/products/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProductForm.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProductForm.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
            GetImageUrl(ref, productID);
        }
    }

    private void GetImageUrl(StorageReference ref, String productID) {
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
                    fStore.collection("Products").document(productID).update("imageUrl", imgUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                else {
                    Toast.makeText(EditProductForm.this, "Failed "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ClearForm() {
        imgProduct.setImageResource(R.drawable.product1);
        edtNameProduct.setText("");
        edtDes.setText("");
        edtQuantity.setText("");
        edtPrice.setText("");
    }
}
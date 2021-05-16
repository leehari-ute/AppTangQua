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

import com.example.giftsapp.Model.Products;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddProductsForm extends AppCompatActivity {

    Button              btnAdd;
    EditText            edtDes, edtNameProduct, edtQuantity, edtPrice;
    ImageView           imgProduct;
    Spinner             spnHoliday, spnObject, spnOccasion;
    List<String>        holidayList , objectList, occasionList;
    Uri                 filePath;
    final int           PICK_IMAGE_REQUEST = 71;
    FirebaseAuth        fAuth;
    FirebaseFirestore   fStore;
    FirebaseStorage     fStorage;
    FirebaseUser        user;
    StorageReference    storageRef;
    TextView            txtHoliday, txtObject, txtOccasion;
    String              name, price, description, holiday, object, occasion, createAt;
    Integer             quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm sản phẩm");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.supply1);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));


        Init();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), ProductsForm.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        btnAdd              = findViewById(R.id.btnAdd);
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
        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();
        fStorage            = FirebaseStorage.getInstance();
        storageRef          = fStorage.getReference();
        user                = fAuth.getCurrentUser();
        LoadSpinner();
    }

    private void LoadSpinner() {
        holidayList.add("Valentine");
        holidayList.add("8/3");
        holidayList.add("Giáng sinh");
        objectList.add("Nam");
        objectList.add("Nữ");
        objectList.add("Bé");
        objectList.add("Nam & Nữ");
        occasionList.add("Sinh nhật");
        occasionList.add("Tân gia");
        occasionList.add("Ngày cưới");

        ArrayAdapter<String> holidayAdapter = new ArrayAdapter<String>(AddProductsForm.this, android.R.layout.simple_spinner_item, holidayList);
        ArrayAdapter<String> objectAdapter = new ArrayAdapter<String>(AddProductsForm.this, android.R.layout.simple_spinner_item, objectList);
        ArrayAdapter<String> occasionAdapter = new ArrayAdapter<String>(AddProductsForm.this, android.R.layout.simple_spinner_item, occasionList);

        holidayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        objectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occasionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnHoliday.setAdapter(holidayAdapter);
        spnObject.setAdapter(objectAdapter);
        spnOccasion.setAdapter(occasionAdapter);
    }

    private void AddProduct() {
        GetDataFromUI();
        if (!CheckRequired()) {
            return;
        }
        Products product    = new Products(name, price, "", description, createAt, quantity, holiday, object, occasion);
        fStore.collection("Products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        UploadImage(documentReference.getId());
                        ClearForm();
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
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

            StorageReference ref = storageRef.child("products/"+ productID);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddProductsForm.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddProductsForm.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AddProductsForm.this, "Failed "+task.getException(), Toast.LENGTH_SHORT).show();
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

    private void GetDataFromUI() {
        name         = edtNameProduct.getText().toString();
        price        = edtPrice.getText().toString();
        description  = edtDes.getText().toString();
        quantity     = Integer.parseInt(edtQuantity.getText().toString());
        holiday      = spnHoliday.getSelectedItem().toString();
        object       = spnObject.getSelectedItem().toString();
        occasion     = spnOccasion.getSelectedItem().toString();
        createAt     = new Date().toString();
    }

    private boolean CheckRequired() {
        if (name.equals("")) {
            edtNameProduct.setError("Tên sản phẩm không được bỏ trống");
            return false;
        }

        edtNameProduct.setError(null);
        if (price.equals("") || Integer.parseInt(price) < 1000) {
            edtPrice.setError("Giá sản phẩm không hợp lệ");
            return false;
        }
        edtPrice.setError(null);

        if (description.equals("") || Integer.parseInt(price) < 1000) {
            edtDes.setError("Giá sản phẩm không hợp lệ");
            return false;
        }
        edtDes.setError(null);

        if (quantity <= 0) {
            edtQuantity.setError("Số lượng không thể nhỏ hơn 1");
            return false;
        }
        edtQuantity.setError(null);

        return true;
    }
}
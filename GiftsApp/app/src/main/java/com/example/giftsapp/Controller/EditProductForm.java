package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProductForm extends AppCompatActivity {

    ArrayAdapter<String> holidayAdapter;
    ArrayAdapter<String> objectAdapter;
    ArrayAdapter<String> occasionAdapter;
    Button btnSave, btnDelete;
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
    FirebaseUser user;
    String   userID, productID, name, price, description, holiday, object, occasion;
    Integer  quantity;
    TextView txtHoliday, txtObject, txtOccasion;
    Products product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("S???a s???n ph???m");
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProduct();
                Intent intent = new Intent(getApplicationContext(), ProductsForm.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteProduct();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//onBackPressed();
            Intent intent = new Intent(getApplicationContext(), ProductsForm.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();
        fStorage            = FirebaseStorage.getInstance();
        storageRef          = fStorage.getReference();
        user                = fAuth.getCurrentUser();
        userID              = user.getUid();
        product             = getIntent().getParcelableExtra("EXTRA_DOCUMENT_PRODUCT");
        productID           = product.getId();
        btnSave             = findViewById(R.id.btnSave);
        btnDelete           = findViewById(R.id.btnDelete);
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
        LoadSpinner();
        LoadProduct();
    }

    private void LoadSpinner() {
        holidayList.add("Valentine");
        holidayList.add("8/3");
        holidayList.add("Gi??ng sinh");
        objectList.add("Nam");
        objectList.add("N???");
        objectList.add("B??");
        objectList.add("Nam & N???");
        occasionList.add("Sinh nh???t");
        occasionList.add("T??n gia");
        occasionList.add("Ng??y c?????i");

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
        GetDataFromUI();

        if (!CheckRequired()) {
            return;
        }
        Log.d("TAG", "Successfully updated!");
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

    @SuppressLint("SetTextI18n")
    private void LoadProduct() {
        edtNameProduct.setText(product.getName());
        edtPrice.setText(product.getPrice());
        edtDes.setText(product.getDescription());
        edtQuantity.setText(product.getQuantity()+"");
        spnHoliday.setSelection(holidayAdapter.getPosition(product.getHoliday()));
        spnObject.setSelection(objectAdapter.getPosition(product.getObject()));
        spnOccasion.setSelection(occasionAdapter.getPosition(product.getOccasion()));
        String imgUrl = product.getImageUrl();
        Glide.with(getApplicationContext())
                .load(imgUrl)
                .into(imgProduct);
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
                && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgProduct.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UploadImage(String productID) {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("??ang t???i...");
            progressDialog.show();

            StorageReference ref = storageRef.child("products/"+ productID);
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
                        public void onProgress(@NotNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("??ang t???i "+(int)progress+"%");
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

    private void GetDataFromUI() {
        name         = edtNameProduct.getText().toString();
        price        = edtPrice.getText().toString();
        description  = edtDes.getText().toString();
        quantity     = Integer.parseInt(edtQuantity.getText().toString());
        holiday      = spnHoliday.getSelectedItem().toString();
        object       = spnObject.getSelectedItem().toString();
        occasion     = spnOccasion.getSelectedItem().toString();
    }

    private boolean CheckRequired() {
        if (name.equals("")) {
            edtNameProduct.setError("T??n s???n ph???m kh??ng ???????c b??? tr???ng");
            return false;
        }

        edtNameProduct.setError(null);
        if (price.equals("") || Integer.parseInt(price) < 1000) {
            edtPrice.setError("Gi?? s???n ph???m kh??ng h???p l???");
            return false;
        }
        edtPrice.setError(null);

        if (description.equals("") || Integer.parseInt(price) < 1000) {
            edtDes.setError("Gi?? s???n ph???m kh??ng h???p l???");
            return false;
        }
        edtDes.setError(null);

        if (quantity <= 0) {
            edtQuantity.setError("S??? l?????ng kh??ng th??? nh??? h??n 1");
            return false;
        }
        edtQuantity.setError(null);

        return true;
    }

    private void DeleteProduct() {
        fStore.collection("Products").document(product.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "???? x??a th??nh c??ng", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProductsForm.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "L???i vui l??ng th??? l???i", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
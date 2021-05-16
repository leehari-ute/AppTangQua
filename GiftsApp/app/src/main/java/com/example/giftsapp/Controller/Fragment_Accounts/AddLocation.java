package com.example.giftsapp.Controller.Fragment_Accounts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giftsapp.Controller.DistrictForm;
import com.example.giftsapp.Controller.LoginForm;
import com.example.giftsapp.Controller.ProvinceForm;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Controller.VillageForm;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddLocation extends AppCompatActivity {

    EditText            edtName, edtPhone;
    TextView            txtProvince, txtDistrict, txtVillage, txtAddress;
    Switch              switchDefaultAddress;
    Button              btnSave;
    FirebaseAuth        fAuth;
    FirebaseFirestore   fStore;
    FirebaseUser        user;
    String              userID, name = "", phone = "", province = "", district = "", village = "", detailAddress = "";
    Integer             provinceID, districtID;
    Boolean             isDefault = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm địa chỉ");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        Init();
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        txtVillage.setEnabled(false);
        txtDistrict.setEnabled(false);

        switchDefaultAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isDefault = true;
                } else {
                    isDefault = false;
                }
            }
        });

        txtProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProvinceForm.class);
                startActivityForResult(intent, 1);
            }
        });

        txtDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DistrictForm.class);
                intent.putExtra("EXTRA_DOCUMENT_PROVINCE_ID", provinceID);
                startActivityForResult(intent, 2);
            }
        });

        txtVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VillageForm.class);
                intent.putExtra("EXTRA_DOCUMENT_DISTRICT_ID", districtID);
                startActivityForResult(intent, 3);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataFromUI();
                if (CheckRequired()) {
                    if (isDefault) {
                        FindDefaultAddress();
                    }
                    AddNewAddress();
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    province = data.getStringExtra("EXTRA_DOCUMENT_PROVINCE");
                    provinceID = data.getIntExtra("EXTRA_DOCUMENT_PROVINCE_ID", 0);
                    txtProvince.setText(province);
                    txtProvince.setError(null);
                    txtDistrict.setText(null);
                    txtVillage.setText(null);
                    txtDistrict.setEnabled(true);
                    districtID = 0;
                    break;
                case 2:
                    district = data.getStringExtra("EXTRA_DOCUMENT_DISTRICT");
                    districtID = data.getIntExtra("EXTRA_DOCUMENT_DISTRICT_ID", 0);
                    txtDistrict.setText(district);
                    txtDistrict.setError(null);
                    txtVillage.setText(null);
                    txtVillage.setEnabled(true);
                    break;
                case 3:
                    village = data.getStringExtra("EXTRA_DOCUMENT_VILLAGE");
                    txtVillage.setText(village);
                    txtVillage.setError(null);
                    break;
                default:
                    break;
            }
        }
    }

    private void Init() {
        edtName                 = findViewById(R.id.edtName);
        edtPhone                = findViewById(R.id.edtPhone);
        txtProvince             = findViewById(R.id.txtProvince);
        txtDistrict             = findViewById(R.id.txtDistrict);
        txtVillage              = findViewById(R.id.txtVillage);
        txtAddress              = findViewById(R.id.txtAddAddress);
        switchDefaultAddress    = findViewById(R.id.switchDefaultAddress);
        btnSave                 = findViewById(R.id.btnSave);
        fAuth                   = FirebaseAuth.getInstance();
        fStore                  = FirebaseFirestore.getInstance();
        user                    = fAuth.getCurrentUser();
        userID                  = user.getUid();
    }

    private void AddNewAddress() {
        HashMap<String, Object> newAddress = new HashMap<String, Object>();
        newAddress.put("isDefault", isDefault);
        newAddress.put("name", name);
        newAddress.put("phone", phone);
        newAddress.put("province", province);
        newAddress.put("district", district);
        newAddress.put("village", village);
        newAddress.put("detailAddress", detailAddress);

        fStore.collection("Users").document(userID).update("address", FieldValue.arrayUnion(newAddress));
    }

    private void GetDataFromUI() {
        name = edtName.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        province = txtProvince.getText().toString().trim();
        district = txtDistrict.getText().toString().trim();
        village = txtVillage.getText().toString().trim();
        detailAddress = txtAddress.getText().toString().trim();
    }

    private void GetDataFromFireStore(ArrayList<Map<String, Object>> addressArray, int i) {
        name = addressArray.get(i).get("name").toString().trim();
        phone = addressArray.get(i).get("phone").toString().trim();
        province = addressArray.get(i).get("province").toString().trim();
        district = addressArray.get(i).get("district").toString().trim();
        village = addressArray.get(i).get("village").toString().trim();
        detailAddress = addressArray.get(i).get("detailAddress").toString().trim();
    }

    private void SetNotDefault() {
        HashMap<String, Object> disableDefault = new HashMap<String, Object>();
        disableDefault.put("isDefault", false);
        disableDefault.put("name", name);
        disableDefault.put("phone", phone);
        disableDefault.put("province", province);
        disableDefault.put("district", district);
        disableDefault.put("village", village);
        disableDefault.put("detailAddress", detailAddress);

        fStore.collection("Users").document(userID).update("address", FieldValue.arrayUnion(disableDefault));
    }

    private void FindDefaultAddress() {
        fStore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList<Map<String, Object>> addressArray = (ArrayList<Map<String, Object>>) document.getData().get("address");
                    for (int i = 0; i < addressArray.size(); i++) {
                        if (addressArray.get(i).get("isDefault").toString().equals("true")) {
                            HashMap<String, Object> defaultAddress = new HashMap<String, Object>();

                            GetDataFromFireStore(addressArray, i);
                            defaultAddress.put("isDefault", true);
                            defaultAddress.put("name", name);
                            defaultAddress.put("phone", phone);
                            defaultAddress.put("province", province);
                            defaultAddress.put("district", district);
                            defaultAddress.put("village", village);
                            defaultAddress.put("detailAddress", detailAddress);

                            fStore.collection("Users").document(userID).update("address", FieldValue.arrayRemove(defaultAddress));

                            SetNotDefault();
                        }
                    }
                } else {
                    Log.d("TAG", "Lỗi");
                }
            }
        });
    }

    private boolean CheckRequired() {
        if (name.trim().equals("")) {
            edtName.setError("Bạn chưa nhập tên");
            return false;
        }
        edtName.setError(null);

        if (phone.trim().equals("")) {
            edtPhone.setError("Bạn chưa nhập số điện thoại");
            return false;
        }
        edtPhone.setError(null);

        if (province.trim().equals("")) {
            txtProvince.setError("Bạn chưa chọn tỉnh");
            return false;
        }
        txtProvince.setError(null);

        if (district.trim().equals("")) {
            txtDistrict.setError("Bạn chưa chọn huyện");
            return false;
        }
        txtDistrict.setError(null);

        if (village.trim().equals("")) {
            txtVillage.setError("Bạn chưa chọn xã");
            return false;
        }
        txtVillage.setError(null);

        if (detailAddress.trim().equals("")) {
            txtAddress.setError("Bạn chưa nhập địa chỉ cụ thể");
            return false;
        }
        txtAddress.setError(null);

        return true;
    }
}
package com.example.giftsapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.giftsapp.Model.Address;
import com.example.giftsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditLocation extends AppCompatActivity {

    Address address;
    EditText edtName, edtPhone;
    TextView txtProvince, txtDistrict, txtVillage, txtAddress;
    Switch switchDefaultAddress;
    Button btnSave;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String           userID, name = "", phone = "", province = "", district = "", village = "", detailAddress = "";
    Integer          provinceID, districtID, addressID, quantityAddress = 0;
    Boolean          isDefault = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        Init();
    }

    private void Init() {
        address = getIntent().getParcelableExtra("PARCEL_ADDRESS");
    }
}
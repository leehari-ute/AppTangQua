package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giftsapp.Adapter.AddressAdapter;
import com.example.giftsapp.Controller.Fragment_Accounts.AddLocation;
import com.example.giftsapp.Model.Address;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SelectLocationForm extends AppCompatActivity {

    TextView txtAddAddress;
    ListView listViewAddress;
    ArrayList<Address> addressArrayList;
    AddressAdapter addressAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    String userID;
    private boolean isBackFromAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chọn địa chỉ nhận hàng");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));
        Init();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddLocation.class));
            }
        });

        listViewAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address addressSelected = (Address) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("PARCEL_ADDRESS", addressSelected);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isBackFromAdd) {
            GetAddressFromFireStore();
        }
        isBackFromAdd = false;
    }

    @Override
    protected void onPause() {
        isBackFromAdd = true;
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

    private void Init(){
        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();
        user                = fAuth.getCurrentUser();
        userID              = user.getUid();
        txtAddAddress       = findViewById(R.id.txtAddAddress);
        listViewAddress     = findViewById(R.id.listViewAddress);
        addressArrayList    = new ArrayList<>();
        addressAdapter      = new AddressAdapter(getApplicationContext(), R.layout.layout_lisview_address, addressArrayList);
        listViewAddress.setAdapter(addressAdapter);
        GetAddressFromFireStore();
    }

    private void GetAddressFromFireStore() {
        addressArrayList.clear();
        fStore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.getData().get("address") != null) {
                        ArrayList<Map<String, Object>> addressArray = (ArrayList<Map<String, Object>>) document.getData().get("address");
                        for (int i = 0; i < addressArray.size(); i++) {
                            Integer ID = Integer.parseInt(addressArray.get(i).get("ID").toString());
                            Boolean isDefault = addressArray.get(i).get("isDefault").toString().equals("true");
                            String name = addressArray.get(i).get("name").toString();
                            String phone = addressArray.get(i).get("phone").toString();
                            String province = addressArray.get(i).get("province").toString();
                            String district = addressArray.get(i).get("district").toString();
                            String village = addressArray.get(i).get("village").toString();
                            String detailAddress = addressArray.get(i).get("detailAddress").toString();
                            Address newAddress = new Address(ID, name, phone, detailAddress, village, district, province, isDefault);
                            addressArrayList.add(newAddress);
                            addressAdapter.notifyDataSetChanged();
                        }
                    }
                }
                else {
                    Log.d("TAG", "Lỗi: " + task.getException());
                }
            }
        });
    }
}
package com.example.giftsapp.Controller.Fragment_Accounts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giftsapp.Adapter.AddressAdapter;
import com.example.giftsapp.Controller.LoginForm;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Model.Address;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Location extends Fragment {

    TextView txtAddAddress;
    ListView listViewAddress;
    ArrayList<Address> addressArrayList;
    AddressAdapter addressAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    String userID;

    private SettingAccountForm settingAccountForm;

    public Location() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        Init(view);
        if (user == null) {
            startActivity(new Intent(settingAccountForm, LoginForm.class));
            settingAccountForm.finish();
        }

        txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settingAccountForm, AddLocation.class));
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

    private void Init(View view){
        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();
        user                = fAuth.getCurrentUser();
        userID              = user.getUid();
        txtAddAddress       = view.findViewById(R.id.txtAddAddress);
        listViewAddress     = view.findViewById(R.id.listViewAddress);
        addressArrayList    = new ArrayList<>();
        addressAdapter      = new AddressAdapter(settingAccountForm, R.layout.layout_lisview_address, addressArrayList);
        listViewAddress.setAdapter(addressAdapter);
        GetAddressFromFireStore();
//        addressArrayList.add(new Address("Đỗ Phạm Trúc Quỳnh", "0393304218", "200", "xã Xuân Trường", "Thành phố Đà Lạt", "Tỉnh Lâm Đồng", true));
//        addressArrayList.add(new Address("Đỗ Phạm Trúc Quỳnh", "0393304218", "200", "xã Xuân Trường", "Thành phố Đà Lạt", "Tỉnh Lâm Đồng", false));

    }

    private void GetAddressFromFireStore() {
        fStore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList<Map<String, Object>> addressArray = (ArrayList<Map<String, Object>>) document.getData().get("address");
                    for (int i = 0; i < addressArray.size(); i++) {
                        Boolean isDefault = addressArray.get(i).get("isDefault").toString().equals("true");
                        String name = addressArray.get(i).get("name").toString();
                        String phone = addressArray.get(i).get("phone").toString();
                        String province = addressArray.get(i).get("province").toString();
                        String district = addressArray.get(i).get("district").toString();
                        String village = addressArray.get(i).get("village").toString();
                        String detailAddress = addressArray.get(i).get("detailAddress").toString();
                        Address newAddress = new Address(name, phone, detailAddress, village, district, province, isDefault);
                        addressArrayList.add(newAddress);
                    }
                    addressAdapter.notifyDataSetChanged();
                }
                else {
                    Log.d("QUYNH", "Looxi: " + task.getException());
                }

            }
        });
    }



}
package com.example.giftsapp.Controller.Fragment_Accounts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giftsapp.Adapter.AddressAdapter;
import com.example.giftsapp.Controller.EditLocation;
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

import org.jetbrains.annotations.NotNull;

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
    private boolean isBackFromAdd;
    private SettingAccountForm settingAccountForm;

    public Location() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBackFromAdd = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isBackFromAdd = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isBackFromAdd) {
            GetAddressFromFireStore();
        }
        isBackFromAdd = false;
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
                Intent intent = new Intent(settingAccountForm, AddLocation.class);
                intent.putExtra("EXTRA_DOCUMENT_FROM_ACTIVITY", false);
                startActivity(intent);
            }
        });

        listViewAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "ssssssss", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), EditLocation.class);
                Address address = (Address) parent.getAdapter().getItem(position);
                intent.putExtra("PARCEL_ADDRESS", address);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NotNull Context context) {
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
package com.example.giftsapp.Controller.Fragment_Accounts;

//import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.giftsapp.Adapter.BillAdapter;
import com.example.giftsapp.Adapter.ViewPagerAdapter;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Model.BillModel;
import com.example.giftsapp.Model.Products;
import com.example.giftsapp.Model.StatusBill;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Bill extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SettingAccountForm settingAccountForm;
    FragmentManager fragmentManager;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<com.example.giftsapp.Model.Bill> billsArrayList;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;

    public Bill() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        Init(view);

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
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userID = user.getUid();
        fStore = FirebaseFirestore.getInstance();
        billsArrayList = new ArrayList<>();
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        GetDataFromFireStore();
    }

    private void GetDataFromFireStore() {
        billsArrayList.clear();
        fStore.collection("Bill").whereEqualTo("userID", userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<Map<String, Object>> billArrayList = new ArrayList<>();
                                    billArrayList.add(document.getData());
                                    ArrayList<Map<String, Object>> statusArrayList = (ArrayList<Map<String, Object>>) billArrayList.get(0).get("status");
                                    ArrayList<StatusBill> statusBillArrayList = new ArrayList<>();
                                    for (int i = 0; i < statusArrayList.size(); i++) {
                                        Boolean isPresent = Boolean.valueOf(statusArrayList.get(i).get("isPresent").toString());
                                        String name = statusArrayList.get(i).get("name").toString();
                                        Timestamp ts = (Timestamp) statusArrayList.get(i).get("createAt");
                                        Date createAt = ts.toDate();
                                        StatusBill status = new StatusBill(isPresent, name, createAt);
                                        statusBillArrayList.add(status);
                                    }

                                    ArrayList<Map<String, Object>> productArrayList = (ArrayList<Map<String, Object>>) billArrayList.get(0).get("products");
                                    ArrayList<Products> productsArrayList = new ArrayList<>();
                                    for (int i = 0; i < productArrayList.size(); i++) {
                                        String id = productArrayList.get(i).get("productID").toString();
                                        String name = productArrayList.get(i).get("name").toString();
                                        String price = productArrayList.get(i).get("price").toString();
                                        String imgUrl = productArrayList.get(i).get("imageUrl").toString();
                                        int quantity = Integer.parseInt(productArrayList.get(i).get("quantity").toString());
                                        Products product = new Products(id, name, price, imgUrl, quantity);
                                        productsArrayList.add(product);
                                    }

                                    String id = document.getId();
                                    String addressID = billArrayList.get(0).get("addressID").toString();
                                    Timestamp ts = (Timestamp) billArrayList.get(0).get("createAt");
                                    Date createAt = ts.toDate();
                                    String paymentType = billArrayList.get(0).get("paymentType").toString();
                                    int quantity = Integer.parseInt(billArrayList.get(0).get("quantityProduct").toString());
                                    String totalPrice = billArrayList.get(0).get("totalPrice").toString();
                                    String uID = billArrayList.get(0).get("userID").toString();
                                    String feeShip = billArrayList.get(0).get("feeShip").toString();
                                    String message = billArrayList.get(0).get("message").toString();
                                    com.example.giftsapp.Model.Bill bill = new com.example.giftsapp.Model.Bill(id, addressID, createAt, paymentType, productsArrayList, statusBillArrayList, totalPrice, uID, quantity, feeShip, message);
                                    billsArrayList.add(bill);
                                }
                            }
                            fragmentManager = getChildFragmentManager();
                            viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), false, billsArrayList);
                            viewPager.setAdapter(viewPagerAdapter);
                            tabLayout.setupWithViewPager(viewPager);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
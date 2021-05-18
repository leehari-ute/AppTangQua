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

import com.example.giftsapp.Adapter.BillAdapter;
import com.example.giftsapp.Controller.LoginForm;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Model.BillModel;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class BillStatus extends Fragment {

    ListView listViewBill;
    ArrayList<BillModel> arrayListBill;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    BillAdapter billAdapter;
    String statusRequest, userID;
    private SettingAccountForm settingAccountForm;

    public BillStatus() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_bill_status, container, false);

        assert getArguments() != null;
        statusRequest = getArguments().getString("status");
        RenameStatus();
        Init(view);

        if (user == null) {
            startActivity(new Intent(settingAccountForm, LoginForm.class));
            getActivity().finish();
        }
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
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userID = user.getUid();
        listViewBill = view.findViewById(R.id.listViewBill);
        arrayListBill = new ArrayList<>();
        billAdapter = new BillAdapter(settingAccountForm,R.layout.list_bill, arrayListBill);
        listViewBill.setAdapter(billAdapter);
        GetDataFromFireStore();
    }

    private void GetDataFromFireStore() {
        fStore.collection("Bill").whereEqualTo("userID", userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<Map<String, Object>> billArrayList = new ArrayList<>();
                        billArrayList.add(document.getData());
                        ArrayList<Map<String, Object>> productArrayList = (ArrayList<Map<String, Object>>) billArrayList.get(0).get("products");
                        ArrayList<Map<String, Object>> statusArrayList = (ArrayList<Map<String, Object>>) billArrayList.get(0).get("status");

                        String productID = productArrayList.get(0).get("productID").toString();
                        String totalPrice = billArrayList.get(0).get("totalPrice").toString();
                        String quantity = billArrayList.get(0).get("quantityProduct").toString();
                        String statusBill = "";

                        for (int i = 0; i < statusArrayList.size(); i++) {
                            if (statusArrayList.get(i).get("isDone").toString().equals("true")) {
                                statusBill = statusArrayList.get(i).get("name").toString();
                                break;
                            }
                        }

                        if (statusBill.equals(statusRequest)) {
                            String firstProduct = productArrayList.get(0).get("name").toString();
                            String firstPrice = productArrayList.get(0).get("price").toString();
                            String imgUrl = productArrayList.get(0).get("imageUrl").toString();
                            BillModel billModel = new BillModel(productID, statusBill, firstProduct, firstPrice, totalPrice, quantity, imgUrl);
                            arrayListBill.add(billModel);
                            billAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void RenameStatus() {
        switch (statusRequest) {
            case "WaitForConfirm":
                statusRequest = "Chờ xác nhận";
                break;
            case "WaitForTheGift":
                statusRequest = "Chờ lấy hàng";
                break;
            case "Delivering":
                statusRequest = "Đang giao hàng";
                break;
            case "Delivered":
                statusRequest = "Đã giao hàng";
                break;
            default:
                statusRequest = "Error";
                break;
        }
    }
}
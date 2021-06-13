package com.example.giftsapp.Controller.Fragment_Accounts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.giftsapp.Adapter.BillAdapter;
import com.example.giftsapp.Controller.BillAdmin;
import com.example.giftsapp.Controller.LoginForm;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Model.Bill;
import com.example.giftsapp.Model.Products;
import com.example.giftsapp.Model.StatusBill;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class BillStatusAdmin extends Fragment {

    ListView listViewBill;
    ArrayList<Bill> billsArrayList;
    ArrayList<Bill> bills;
    FirebaseAuth fAuth;
    FirebaseUser user;

    FirebaseFirestore fStore;
    BillAdapter billAdapter;
    String statusRequest;
    String userID;
    private BillAdmin billAdmin;

    public BillStatusAdmin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_bill_status_admin, container, false);

        Init(view);

        if (user == null) {
            startActivity(new Intent(billAdmin, LoginForm.class));
            getActivity().finish();
        }

        return view;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof BillAdmin) {
            this.billAdmin = (BillAdmin) context;
        }
    }

    private void Init(View view){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userID = user.getUid();
        listViewBill = view.findViewById(R.id.listViewBill);
        bills = new ArrayList<>();
        billsArrayList = new ArrayList<>();
        billAdapter = new BillAdapter(billAdmin, R.layout.list_bill_admin, bills, true);
        listViewBill.setAdapter(billAdapter);
        GetBillsByStatus();
    }

    private void GetBillsByStatus() {
        assert getArguments() != null;
        statusRequest = getArguments().getString("STATUS");
        billsArrayList = getArguments().getParcelableArrayList("LIST_BILL");
        for (int i = 0; i < billsArrayList.size(); i++) {
            ArrayList<StatusBill> statusBill = billsArrayList.get(i).getStatus();
            for (int j = 0; j < statusBill.size(); j ++) {
                if (statusBill.get(j).getDone()) {
                    if (statusBill.get(j).getName().equals(statusRequest)) {
                        Bill bill = billsArrayList.get(i);
                        bills.add(bill);
                    }
                }
            }
        }
        billAdapter.notifyDataSetChanged();
    }
}
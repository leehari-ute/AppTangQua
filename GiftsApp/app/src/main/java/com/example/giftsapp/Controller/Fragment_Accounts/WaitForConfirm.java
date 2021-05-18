package com.example.giftsapp.Controller.Fragment_Accounts;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.giftsapp.Adapter.BillAdapter;
import com.example.giftsapp.Adapter.ViewPagerAdapter;
import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.Model.BillModel;
import com.example.giftsapp.R;

import java.util.ArrayList;

public class WaitForConfirm extends Fragment {

    ListView listView;
    ArrayList<BillModel> arrBill;
    BillAdapter adapter;
    private SettingAccountForm settingAccountForm;
    public WaitForConfirm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_wait_for_confirm, container, false);
        Init(view);
        adapter = new BillAdapter(settingAccountForm,R.layout.list_bill, arrBill);
        listView.setAdapter(adapter);
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
        listView = view.findViewById(R.id.listView);
        arrBill = new ArrayList<>();


//        arrBill.add(new BillModel("Chờ xác nhận","Đồng hồ xinh ơi là xinh đó nha","200.000VNĐ",
//                "200.000VNĐ","1 sản phẩm",R.drawable.donghonu));
//        arrBill.add(new BillModel("Chờ xác nhận","Đồng hồ xinh","200.000VNĐ",
//                "400.000VNĐ","2 sản phẩm",R.drawable.donghonu));
//        arrBill.add(new BillModel("Chờ xác nhận","Đồng hồ xinh","200.000VNĐ",
//                "600.000VNĐ","3 sản phẩm",R.drawable.donghonu));
//        arrBill.add(new BillModel("Chờ xác nhận","Đồng hồ xinh","200.000VNĐ",
//                "800.000VNĐ","4 sản phẩm",R.drawable.donghonu));
//        arrBill.add(new BillModel("Chờ xác nhận","Đồng hồ xinh","200.000VNĐ",
//                "200.000VNĐ","5 sản phẩm",R.drawable.donghonu));
    }
}
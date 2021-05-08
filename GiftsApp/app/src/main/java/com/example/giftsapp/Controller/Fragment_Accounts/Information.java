package com.example.giftsapp.Controller.Fragment_Accounts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.giftsapp.Controller.SettingAccountForm;
import com.example.giftsapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Information extends Fragment {

    TextView txtTen;
    TextView txtGender;
    TextView txtBirthday;
    Calendar calendar = Calendar.getInstance();
    TextView txtBio;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private SettingAccountForm settingAccountForm;

    public Information() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        Init(view);

        //txtBirthday.setText(simpleDateFormat.format(calendar.getTime()));

        txtTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingAccountForm, ChangeName.class);
                startActivity(intent);
            }
        });

        txtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Gender();
            }
        });


        txtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Birthday();
            }
        });

        txtBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingAccountForm, ChangeBiography.class);
                startActivity(intent);
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

    private void Init(View view) {

        txtTen = view.findViewById(R.id.txtTen);
        txtGender = view.findViewById(R.id.txtGender);
        txtBirthday = view.findViewById(R.id.txtBirthday);
        txtBio = view.findViewById(R.id.txtBio);
    }

    private  void Dialog_Gender(){
        Dialog dialog = new Dialog(settingAccountForm);
        dialog.setContentView(R.layout.dialog_gender);
        dialog.show();
    }

    private void Dialog_Birthday(){

        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(settingAccountForm, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                txtBirthday.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

}
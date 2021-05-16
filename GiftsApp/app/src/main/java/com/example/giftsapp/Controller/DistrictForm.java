package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.giftsapp.API.Address.ApiService;
import com.example.giftsapp.API.Address.Model.District;
import com.example.giftsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictForm extends AppCompatActivity {

    ArrayList<String> districtList;
    ArrayList<District> districtArrayList;
    ListView listViewDistrict;
    ArrayAdapter districtAdapter;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;
    Integer provinceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm Quận/Huyện");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        Init();
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        listViewDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_DOCUMENT_DISTRICT", districtList.get(position));
                intent.putExtra("EXTRA_DOCUMENT_DISTRICT_ID", districtArrayList.get(position).getID());
                setResult(RESULT_OK, intent);
                finish();
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

    private void Init() {
        provinceID          = getIntent().getIntExtra("EXTRA_DOCUMENT_PROVINCE_ID", 0);
        listViewDistrict    = findViewById(R.id.listViewDistrict);
        fAuth               = FirebaseAuth.getInstance();
        user                = fAuth.getCurrentUser();
        userID              = user.getUid();
        districtList        = new ArrayList<>();
        districtAdapter     = new ArrayAdapter(DistrictForm.this, android.R.layout.simple_list_item_1, districtList);
        listViewDistrict.setAdapter(districtAdapter);
        CallApi();
    }

    private void CallApi() {
        ApiService.api.getListDistrict(provinceID).enqueue(new Callback<ArrayList<District>>() {
            @Override
            public void onResponse(Call<ArrayList<District>> call, Response<ArrayList<District>> response) {
                districtArrayList = response.body();
                for (int i = 0; i < districtArrayList.size(); i++) {
                    if (districtArrayList.get(i).getTitle().equals("Chưa rõ")) {
                        districtArrayList.remove(i);
                        continue;
                    }
                    districtList.add(districtArrayList.get(i).getTitle());
                }
                districtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<District>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
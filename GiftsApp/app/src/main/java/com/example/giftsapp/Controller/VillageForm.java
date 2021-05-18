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
import com.example.giftsapp.API.Address.Model.Village;
import com.example.giftsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VillageForm extends AppCompatActivity {

    ArrayList<String> villageList;
    ArrayList<Village> villageArrayList;
    ListView listViewVillage;
    ArrayAdapter villageAdapter;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;
    Integer districtID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm Phường/Xã");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        Init();
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        listViewVillage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_DOCUMENT_VILLAGE", villageList.get(position));
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
        districtID      = getIntent().getIntExtra("EXTRA_DOCUMENT_DISTRICT_ID", 0);
        listViewVillage = findViewById(R.id.listViewVillage);
        fAuth           = FirebaseAuth.getInstance();
        user            = fAuth.getCurrentUser();
        userID          = user.getUid();
        villageList     = new ArrayList<>();
        villageAdapter  = new ArrayAdapter(VillageForm.this, android.R.layout.simple_list_item_1, villageList);
        listViewVillage.setAdapter(villageAdapter);
        CallApi();
    }

    private void CallApi() {
        ApiService.api.getListVillage(districtID).enqueue(new Callback<ArrayList<Village>>() {
            @Override
            public void onResponse(Call<ArrayList<Village>> call, Response<ArrayList<Village>> response) {
                villageArrayList = response.body();
                for (int i = 0; i < villageArrayList.size(); i++) {
                    if (villageArrayList.get(i).getTitle().equals("Chưa rõ")) {
                        villageArrayList.remove(i);
                        continue;
                    }
                    villageList.add(villageArrayList.get(i).getTitle());
                }
                villageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Village>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
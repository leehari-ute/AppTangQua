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
import android.widget.Toast;

import com.example.giftsapp.API.Address.Model.Province;
import com.example.giftsapp.API.Address.Model.ProvinceList;
import com.example.giftsapp.API.Address.ApiService;
import com.example.giftsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinceForm extends AppCompatActivity {
    ArrayList<String> provinceList;
    ArrayList<Province> provinceArrayList;
    ListView listViewProvince;
    ArrayAdapter provinceAdapter;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm Tỉnh/Thành phố");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));

        Init();
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        listViewProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_DOCUMENT_PROVINCE", provinceArrayList.get(position).getTitle());
                intent.putExtra("EXTRA_DOCUMENT_PROVINCE_ID", provinceArrayList.get(position).getID());
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
        listViewProvince    = findViewById(R.id.listViewProvince);
        fAuth               = FirebaseAuth.getInstance();
        user                = fAuth.getCurrentUser();
        userID              = user.getUid();
        provinceList        = new ArrayList<>();
        provinceAdapter     = new ArrayAdapter(ProvinceForm.this, android.R.layout.simple_list_item_1, provinceList);
        listViewProvince.setAdapter(provinceAdapter);
        CallApi();
    }

    private void CallApi() {
        ApiService.api.getListProvince().enqueue(new Callback<ProvinceList>() {
            @Override
            public void onResponse(Call<ProvinceList> call, Response<ProvinceList> response) {
                ProvinceList provinceLists = response.body();
                provinceArrayList = provinceLists.getLtsItem();

                for (int i = 0; i < provinceArrayList.size(); i++) {
                    if (provinceArrayList.get(i).getTitle().equals("Chưa rõ")) {
                        provinceArrayList.remove(i);
                        continue;
                    }
                    provinceList.add(provinceArrayList.get(i).getTitle());
                }
                provinceAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<ProvinceList> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}

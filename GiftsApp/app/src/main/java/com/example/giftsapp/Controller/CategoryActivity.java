package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.giftsapp.Adapter.CategoryAdapter;
import com.example.giftsapp.Adapter.GridProductLayoutAdapter;
import com.example.giftsapp.Adapter.HomePageAdapter;
import com.example.giftsapp.Adapter.HorizontalProductScrollAdapter;
import com.example.giftsapp.Model.CategoryModel;
import com.example.giftsapp.Model.HomePageModel;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.Model.sliderModel;
import com.example.giftsapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        // ánh xạ
        categoryRecyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);

        //////// Banner Slider
       // List<sliderModel> sliderModelList = new ArrayList<sliderModel>();

        //////// Banner Slider

        //////// Horizontal product layout
       /* List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Ví Nam","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Ví Nữ","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"gấu bông","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.xe,"xe hơi","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Thắt lưng","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Nón","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Váy","Ví dành cho nam","200000-vnđ"));
        */
        //HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);

        //////// Horizontal product layout

        //////////////////////////////////////////////////

        LinearLayoutManager TestingLayoutManager = new LinearLayoutManager(this);
        TestingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(TestingLayoutManager);


        List<HomePageModel> homePageModelList = new ArrayList<>();
        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.main_search_icon)
        {
            // search chỗ này
            return true;
        }else if(id== android.R.id.home){
            finish();
           return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
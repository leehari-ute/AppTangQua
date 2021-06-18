package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giftsapp.Adapter.CategoryAdapter;
import com.example.giftsapp.Adapter.GridProductLayoutAdapter;
import com.example.giftsapp.Adapter.HomePageAdapter;
import com.example.giftsapp.Adapter.HorizontalProductScrollAdapter;
import com.example.giftsapp.Adapter.SearchProductAdapter;
import com.example.giftsapp.Model.CategoryModel;
import com.example.giftsapp.Model.HomePageModel;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.Model.ProductSearchModel;
import com.example.giftsapp.Model.sliderModel;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private FirebaseFirestore firebaseFirestore;
    private String TypeProduct = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(title.equals("Baby"))
        {
            TypeProduct = "Bé";
        }
        else if(title.equals("Woman"))
        {
            TypeProduct = "Nữ";
        }
        else if(title.equals("Man"))
        {
            TypeProduct = "Nam";
        }
        else if(title.equals("Birthday"))
        {
            TypeProduct = "Sinh nhật";
        }
        else if(title.equals("Tangia"))
        {
            TypeProduct = "Tân gia";
        }
        else if(title.equals("Wedding"))
        {
            TypeProduct = "Ngày cưới";
        }



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

        firebaseFirestore = FirebaseFirestore.getInstance();
        LinearLayoutManager TestingLayoutManager = new LinearLayoutManager(this);
        TestingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(TestingLayoutManager);


        List<HomePageModel> homePageModelList = new ArrayList<>();
       //HomePageAdapter adapter = new HomePageAdapter(homePageModelList);

        List<ProductSearchModel> productSearchModelList = new ArrayList<>();
        SearchProductAdapter adapter = new SearchProductAdapter(productSearchModelList);
        categoryRecyclerView.setAdapter(adapter);

        firebaseFirestore.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        if(documentSnapshot.get("object").toString().equals(TypeProduct)) {
                            productSearchModelList.add(new ProductSearchModel(documentSnapshot.get("imageUrl").toString()
                                    , documentSnapshot.get("name").toString()
                                    , documentSnapshot.get("price").toString()
                                    , documentSnapshot.get("object").toString()
                                    , documentSnapshot.getId()
                                    , documentSnapshot.get("description").toString()));
                            adapter.notifyDataSetChanged();
                        }
                        /*if(documentSnapshot.get("occasion").toString().equals(TypeProduct))
                        {
                            productSearchModelList.add(new ProductSearchModel(documentSnapshot.get("imageUrl").toString()
                                    , documentSnapshot.get("name").toString()
                                    , documentSnapshot.get("price").toString()
                                    , documentSnapshot.get("object").toString()
                                    , documentSnapshot.getId()
                                    , documentSnapshot.get("description").toString()));
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            productSearchModelList.add(new ProductSearchModel(documentSnapshot.get("imageUrl").toString()
                                    , documentSnapshot.get("name").toString()
                                    , documentSnapshot.get("price").toString()
                                    , documentSnapshot.get("object").toString()
                                    , documentSnapshot.getId()
                                    , documentSnapshot.get("description").toString()));
                            adapter.notifyDataSetChanged();
                        }*/


                    }


                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(CategoryActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
            Intent intent = new Intent(getApplicationContext(),SearchProductActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if(id== android.R.id.home){
            finish();
           return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
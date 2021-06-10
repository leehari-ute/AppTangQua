package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.giftsapp.Adapter.SearchProductAdapter;
import com.example.giftsapp.Model.HomePageModel;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.Model.ProductSearchModel;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {

    private RecyclerView SearchPro_recyclerView;
    private List<ProductSearchModel> productSearchModelList;
    private SearchProductAdapter searchProductAdapter;
    private FirebaseFirestore firebaseFirestore;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        SearchPro_recyclerView = findViewById(R.id.Search_product_recyclerView);

        firebaseFirestore = FirebaseFirestore.getInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SearchPro_recyclerView.setLayoutManager(layoutManager);

        productSearchModelList = new ArrayList<>();
        searchProductAdapter = new SearchProductAdapter(productSearchModelList);
        SearchPro_recyclerView.setAdapter(searchProductAdapter);

        firebaseFirestore.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        productSearchModelList.add(new ProductSearchModel(documentSnapshot.get("imageUrl").toString()
                                ,documentSnapshot.get("name").toString()
                                ,documentSnapshot.get("price").toString()
                                ,documentSnapshot.get("object").toString()
                                ,documentSnapshot.getId()
                                ,documentSnapshot.get("description").toString()));
                        searchProductAdapter.notifyDataSetChanged();

                    }


                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(SearchProductActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        SearchManager searchManager =  (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_icon).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // thực hiện search theo tên
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProductAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProductAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
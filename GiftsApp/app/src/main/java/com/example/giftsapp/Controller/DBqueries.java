package com.example.giftsapp.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giftsapp.Adapter.CategoryAdapter;
import com.example.giftsapp.Adapter.HomePageAdapter;
import com.example.giftsapp.Model.CategoryModel;
import com.example.giftsapp.Model.HomePageModel;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.Model.sliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBqueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();
    public static void loadCategories(CategoryAdapter categoryAdapter, Context context){

        firebaseFirestore.collection("Categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public static void loadBannerTripAd(HomePageAdapter adapter,Context context){
        firebaseFirestore.collection("Categories")
                .document("HOME")
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()) {
                                if (((long) documentSnapshot.get("view_type")) == 0) {
                                    List<sliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                    for (long x = 1; x < no_of_banners + 1; x++) {
                                        sliderModelList.add(new sliderModel(documentSnapshot.get("banner_" + x).toString(),
                                                documentSnapshot.get("banner_" + x + "_background").toString()));

                                    }

                                    homePageModelList.add(new HomePageModel(0, sliderModelList));
                                } else if (((long) documentSnapshot.get("view_type")) == 1) {
                                    homePageModelList.add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("background").toString()));
                                    Log.i("aaa",documentSnapshot.get("background").toString());
                                } else if (((long) documentSnapshot.get("view_type")) == 2) {

                                } else if (((long) documentSnapshot.get("view_type")) == 3)
                                {

                                }
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadProductData(HomePageAdapter adapter, Context context){
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();

        firebaseFirestore.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("imageUrl").toString()
                                ,documentSnapshot.get("name").toString()
                                ,documentSnapshot.get("occasion").toString()
                                ,documentSnapshot.get("price").toString()));
                    }
                    homePageModelList.add(new HomePageModel(2,"Bán chạy",horizontalProductScrollModelList ));
                    homePageModelList.add(new HomePageModel(3,"#Trending",horizontalProductScrollModelList ));
                    adapter.notifyDataSetChanged();
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

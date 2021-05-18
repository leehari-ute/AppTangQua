package com.example.giftsapp.Controller;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.giftsapp.Adapter.CategoryAdapter;
import com.example.giftsapp.Adapter.GridProductLayoutAdapter;
import com.example.giftsapp.Adapter.HomePageAdapter;
import com.example.giftsapp.Adapter.HorizontalProductScrollAdapter;
import com.example.giftsapp.Adapter.SliderAdapter;
import com.example.giftsapp.Controller.ui.home.HomeViewModel;
import com.example.giftsapp.Model.CategoryModel;
import com.example.giftsapp.Model.HomePageModel;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.Model.sliderModel;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;;import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.giftsapp.Controller.DBqueries.categoryModelList;
import static com.example.giftsapp.Controller.DBqueries.firebaseFirestore;
import static com.example.giftsapp.Controller.DBqueries.homePageModelList;
import static com.example.giftsapp.Controller.DBqueries.loadBannerTripAd;
import static com.example.giftsapp.Controller.DBqueries.loadCategories;
import static com.example.giftsapp.Controller.DBqueries.loadProductData;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;




    private  HomePageAdapter adapter;

    //FirebaseFirestore firebaseFirestore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // ánh xạ recyclerview
        categoryRecyclerView = root.findViewById(R.id.category_recyclerview);
        /*LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);*/
        // set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());


        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // truy vấn ngược qua DBqueries
        if(categoryModelList.size()==0)
        {
            loadCategories(categoryAdapter,getContext());
        }else{
            categoryAdapter.notifyDataSetChanged();
        }

        /*firebaseFirestore = FirebaseFirestore.getInstance();
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
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/



        //////////////////////// Banner Slider

        //bannerSliderViewPager = root.findViewById(R.id.banner_slider_viewPager);
       /* List<sliderModel> sliderModelList = new ArrayList<sliderModel>();

        sliderModelList.add(new sliderModel(R.drawable.contact,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.facebook,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.gmail,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.documents,"#077AE4"));

        sliderModelList.add(new sliderModel(R.drawable.giftapp,"#077AE4"));

        sliderModelList.add(new sliderModel(R.drawable.contact,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.facebook,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.gmail,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.documents,"#077AE4"));*/


        ///////////////////////// Banner Slider

        //////////////////////// Strip Ad
        /*StripAdImage = root.findViewById(R.id.strip_ad_image);
        StripAdContainer = root.findViewById(R.id.trip_ad_container);

        StripAdImage.setImageResource(R.drawable.stripadd);
        StripAdContainer.setBackgroundColor(Color.parseColor("#F84710"));*/
        //////////////////////// Strip Ad

        /////////////////////// Horizontal Products
       /* horizontalLayoutTitle = root.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalLayoutViewAllBtn = root.findViewById(R.id.horizontal_scroll_layout_button);
        horizontalRecyclerview = root.findViewById(R.id.horizontal_scroll_layout_recyclerview);*/

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

        /////////////////////// Grid Products Layout

        ////////////////////////////// Testing
        homePageRecyclerView = root.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager TestingLayoutManager = new LinearLayoutManager(getContext());
        TestingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(TestingLayoutManager);

        adapter = new HomePageAdapter(homePageModelList);
        homePageRecyclerView.setAdapter(adapter);

        // truy vấn ngược qua DBqueries
        if(homePageModelList.size()==0)
        {
            loadBannerTripAd(adapter,getContext());
        }else{
            adapter.notifyDataSetChanged();
        }

        // load ảnh xho slider và strip_ad
       /* firebaseFirestore.collection("Categories")
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
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

        // load ảnh cho horizontal_item // và gridLayout
/*        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();

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
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        loadProductData(adapter,getContext());
        ////////////////////////////// Testing
        return root;
    }

}
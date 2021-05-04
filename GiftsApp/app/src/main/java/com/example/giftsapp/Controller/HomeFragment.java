package com.example.giftsapp.Controller;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.giftsapp.R;;import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;

    //////////////////////// Banner Slider

   /* private ViewPager bannerSliderViewPager;
    private List<sliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000; // chu kỳ lập lại*/
    ///////////////////////// Banner Slider

    //////////////////////// Strip Ad
    /*ImageView StripAdImage;
    ConstraintLayout StripAdContainer;*/
    //////////////////////// Strip Ad

    /////////////////////// Horizontal Products
   /* private TextView horizontalLayoutTitle;
    private Button horizontalLayoutViewAllBtn;
    private RecyclerView horizontalRecyclerview;*/
    /////////////////////// Horizontal Products

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


       final List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Child"));
        categoryModelList.add(new CategoryModel("link","Nam"));
        categoryModelList.add(new CategoryModel("link","Nữ"));
        categoryModelList.add(new CategoryModel("link","dịp 8/3"));
        categoryModelList.add(new CategoryModel("link","Valentines"));
        categoryModelList.add(new CategoryModel("link","Birthday"));
        categoryModelList.add(new CategoryModel("link","Toys"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        //////////////////////// Banner Slider

        //bannerSliderViewPager = root.findViewById(R.id.banner_slider_viewPager);
        List<sliderModel> sliderModelList = new ArrayList<sliderModel>();

        sliderModelList.add(new sliderModel(R.drawable.contact,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.facebook,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.gmail,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.documents,"#077AE4"));

        sliderModelList.add(new sliderModel(R.drawable.giftapp,"#077AE4"));

        sliderModelList.add(new sliderModel(R.drawable.contact,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.facebook,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.gmail,"#077AE4"));
        sliderModelList.add(new sliderModel(R.drawable.documents,"#077AE4"));

       /* SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE);
                {
                    PageLoop();
                }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PageLoop();
                stopBannerSlideShow();
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    startBannerSlideShow();
                }
                return false;
            }
        });*/

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

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Ví Nam","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Ví Nữ","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"gấu bông","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.xe,"xe hơi","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Thắt lưng","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Nón","Ví dành cho nam","200000-vnđ"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.vi,"Váy","Ví dành cho nam","200000-vnđ"));

        //HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);

        //Tạo layout chứa các item product
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerview.setLayoutManager(linearLayoutManager);

        horizontalRecyclerview.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();*/
        /////////////////////// Horizontal Products

        /////////////////////// Grid Products Layout
        /*TextView gridLayoutTitle = root.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutViewAllBtn = root.findViewById(R.id.grid_product_layout_viewAl_btn);
        GridView gridView = root.findViewById(R.id.grid_product_layout_gridview);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));*/
        /////////////////////// Grid Products Layout

        ////////////////////////////// Testing
        testing = root.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager TestingLayoutManager = new LinearLayoutManager(getContext());
        TestingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(TestingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.stripadd,"#F84710"));
        homePageModelList.add(new HomePageModel(3,"Bán chạy",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.stripadd,"#ffff00"));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.stripadd,"#ff0000"));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ////////////////////////////// Testing
        return root;
    }
    ///////////////////////// Banner Slider

       /* private void PageLoop(){
            if(currentPage == sliderModelList.size()-2)
            {
                currentPage =2 ;
                bannerSliderViewPager.setCurrentItem(currentPage,false);
            }
            if(currentPage == 1)
            {
                currentPage =sliderModelList.size()-3 ;
                bannerSliderViewPager.setCurrentItem(currentPage,false);
            }
        }
        private void startBannerSlideShow(){
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if(currentPage>= sliderModelList.size()){
                       currentPage=1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++,true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            } ,DELAY_TIME,PERIOD_TIME);
        }
        private void stopBannerSlideShow(){
            timer.cancel();
        }*/
    ///////////////////////// Banner Slider

}
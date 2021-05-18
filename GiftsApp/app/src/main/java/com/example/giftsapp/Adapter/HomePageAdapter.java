package com.example.giftsapp.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giftsapp.Controller.ProductDetailsActivity;
import com.example.giftsapp.Controller.ViewAllActivity;
import com.example.giftsapp.Model.HomePageModel;
import com.example.giftsapp.Model.HorizontalProductScrollModel;
import com.example.giftsapp.Model.sliderModel;
import com.example.giftsapp.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool= new RecyclerView.RecycledViewPool();
    }


    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType())
        {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case HomePageModel.BANNER_SLIDER: {
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout, parent, false);
                return new BannerSliderViewholder(bannerSliderView);
            }
            case HomePageModel.STRIP_AD_BANNER:{
                View StripAdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewholer(StripAdView);
            }
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:{
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalProductViewholder(horizontalProductView);
            }
            case HomePageModel.GRID_PRODUCT_VIEW:{
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                return new GridProductViewholder(gridProductView);
            }
            default:
                return null;
        }
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()){
            case HomePageModel.BANNER_SLIDER:{
                List<sliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewholder)holder).setBannerSliderViewPager(sliderModelList);
                break;
            }
            case HomePageModel.STRIP_AD_BANNER:{
                String resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewholer)holder).setStripAd(resource,color);
                break;
            }
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:{
                String HorizontalLayoutTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewholder)holder).setHorizontalProductLayout(horizontalProductScrollModelList,HorizontalLayoutTitle);
                break;
            }
            case HomePageModel.GRID_PRODUCT_VIEW:{
                String GridLayoutTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewholder)holder).setGridViewProduct(gridProductScrollModelList,GridLayoutTitle);
                break;
            }
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewholder extends RecyclerView.ViewHolder{
        private ViewPager bannerSliderViewPager;
        private int currentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000; // chu kỳ lập lại

        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_viewPager);

        }
        private void setBannerSliderViewPager(List<sliderModel> sliderModelList){
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
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
                        PageLoop(sliderModelList);
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(sliderModelList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    PageLoop(sliderModelList);
                    stopBannerSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP)
                    {
                        startBannerSlideShow(sliderModelList);
                    }
                    return false;
                }
            });
        }
        private void PageLoop(List<sliderModel> sliderModelList){
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
        private void startBannerSlideShow(List<sliderModel> sliderModelList){
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
        }
    }
    public class StripAdBannerViewholer extends  RecyclerView.ViewHolder{
        ImageView StripAdImage;
        ConstraintLayout StripAdContainer;
        public StripAdBannerViewholer(@NonNull View itemView) {
            super(itemView);
            StripAdImage = itemView.findViewById(R.id.strip_ad_image);
            StripAdContainer = itemView.findViewById(R.id.trip_ad_container);
        }
        private void setStripAd(String resource, String color){
            //StripAdImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic__homec)).into(StripAdImage);
            StripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }
    public class HorizontalProductViewholder extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView horizontalLayoutTitle;
        private Button horizontalLayoutViewAllBtn;
        private RecyclerView horizontalRecyclerview;
        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalLayoutViewAllBtn = itemView.findViewById(R.id.horizontal_scroll_layout_button);
            horizontalRecyclerview = itemView.findViewById(R.id.horizontal_scroll_layout_recyclerview);

            horizontalRecyclerview.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title){
            horizontalLayoutTitle.setText(title);// set title cho horizontal Layout product
            if(horizontalProductScrollModelList.size()>8)
            {
                horizontalLayoutViewAllBtn.setVisibility(View.VISIBLE);
                horizontalLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewAllIntent = new Intent( itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }
            else{
                horizontalLayoutViewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);

            //Tạo layout chứa các item product
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerview.setLayoutManager(linearLayoutManager);

            horizontalRecyclerview.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }
    public class GridProductViewholder extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridLayout gridProductLayout;
        public GridProductViewholder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_viewAl_btn);
            gridProductLayout = itemView.findViewById(R.id.gridLayout);
        }
        private void setGridViewProduct(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title){
            gridLayoutTitle.setText(title);

            for(int x=0;x<4;x++)
            {
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_name);
                TextView productDescription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.ic__homec)).into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductName());
                productDescription.setText(horizontalProductScrollModelList.get(x).getProductDescription());
                productPrice.setText(horizontalProductScrollModelList.get(x).getProductPrice()+".VND");

                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                // sự kiện click vào item sản phẩm trên grid layout
                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }

            gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                    Intent viewAllIntent = new Intent( itemView.getContext(), ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }
}

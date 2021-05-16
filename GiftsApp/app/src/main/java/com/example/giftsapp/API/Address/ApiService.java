package com.example.giftsapp.API.Address;

import com.example.giftsapp.API.Address.Model.District;
import com.example.giftsapp.API.Address.Model.ProvinceList;
import com.example.giftsapp.API.Address.Model.Village;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy--MM--dd HH:mm:ss").create();
    ApiService api = new Retrofit.Builder()
            .baseUrl("https://thongtindoanhnghiep.co/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/city")
    Call<ProvinceList> getListProvince();

    @GET("api/city/{id}/district")
    Call<ArrayList<District>> getListDistrict(@Path("id") int provinceID);

    @GET("api/district/{id}/ward")
    Call<ArrayList<Village>> getListVillage(@Path("id") int districtID);
}

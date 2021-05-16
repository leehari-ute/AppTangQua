package com.example.giftsapp.API.Address.Model;

import java.util.ArrayList;

public class ProvinceList {
    private ArrayList<Province> LtsItem;
    private int TotalDoanhNghiep;

    public ProvinceList(ArrayList<Province> ltsItem, int totalDoanhNghiep) {
        LtsItem = ltsItem;
        TotalDoanhNghiep = totalDoanhNghiep;
    }

    public ArrayList<Province> getLtsItem() {
        return LtsItem;
    }

    public void setLtsItem(ArrayList<Province> ltsItem) {
        LtsItem = ltsItem;
    }

    public int getTotalDoanhNghiep() {
        return TotalDoanhNghiep;
    }

    public void setTotalDoanhNghiep(int totalDoanhNghiep) {
        TotalDoanhNghiep = totalDoanhNghiep;
    }
}

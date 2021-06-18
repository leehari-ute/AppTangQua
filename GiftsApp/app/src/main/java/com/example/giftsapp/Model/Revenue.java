package com.example.giftsapp.Model;

import java.util.Calendar;

public class Revenue {
    private String revenue;
    private Integer totalBill;

    public Revenue(String revenue, Integer totalBill) {
        this.revenue = revenue;
        this.totalBill = totalBill;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public Integer getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Integer totalBill) {
        this.totalBill = totalBill;
    }
}

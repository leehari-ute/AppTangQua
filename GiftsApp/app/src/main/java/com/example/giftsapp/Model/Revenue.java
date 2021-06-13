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

    public static String getMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String monthEnglish = "January";
        switch (month) {
            case 1:
                monthEnglish = "February";
                break;
            case 2:
                monthEnglish = "March";
                break;
            case 3:
                monthEnglish = "April";
                break;
            case 4:
                monthEnglish = "May";
                break;
            case 5:
                monthEnglish = "June";
                break;
            case 6:
                monthEnglish = "July";
                break;
            case 7:
                monthEnglish = "August";
                break;
            case 8:
                monthEnglish = "September";
                break;
            case 9:
                monthEnglish = "October";
                break;
            case 10:
                monthEnglish = "November";
                break;
            case 11:
                monthEnglish = "December";
                break;
            default:
                break;
        }
        return monthEnglish;
    }
}

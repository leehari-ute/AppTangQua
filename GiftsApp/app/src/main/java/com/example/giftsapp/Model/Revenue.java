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
            case 2:
                monthEnglish = "February";
                break;
            case 3:
                monthEnglish = "March";
                break;
            case 4:
                monthEnglish = "April";
                break;
            case 5:
                monthEnglish = "May";
                break;
            case 6:
                monthEnglish = "June";
                break;
            case 7:
                monthEnglish = "July";
                break;
            case 8:
                monthEnglish = "August";
                break;
            case 9:
                monthEnglish = "September";
                break;
            case 10:
                monthEnglish = "October";
                break;
            case 11:
                monthEnglish = "November";
                break;
            case 12:
                monthEnglish = "December";
                break;
            default:
                break;
        }
        return monthEnglish;
    }
}

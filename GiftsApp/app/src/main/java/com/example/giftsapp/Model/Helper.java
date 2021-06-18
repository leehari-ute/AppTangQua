package com.example.giftsapp.Model;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.giftsapp.Adapter.ProductAdapter;
import com.example.giftsapp.Adapter.StatusAdapter;

import java.security.SecureRandom;
import java.util.Calendar;

public class Helper {
    public static void getListViewProductSize(ListView myListView) {
        ProductAdapter myListAdapter = (ProductAdapter) myListView.getAdapter();

        if (myListAdapter==null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight=0;
        for (int size=0; size < myListAdapter.getCount(); size++) {
            View listItem=myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight+=listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params=myListView.getLayoutParams();
        params.height=totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    public static void getListViewStatusSize(ListView myListView) {
        StatusAdapter myListAdapter = (StatusAdapter) myListView.getAdapter();
        if (myListAdapter==null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight=0;
        for (int size=0; size < myListAdapter.getCount(); size++) {
            View listItem=myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight+=listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params=myListView.getLayoutParams();
        params.height=totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
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

    private static volatile SecureRandom numberGenerator = null;
    private static final long MSB = 0x8000000000000000L;

    public static String RandomID() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }
}

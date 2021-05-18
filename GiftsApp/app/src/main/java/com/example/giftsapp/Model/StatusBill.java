package com.example.giftsapp.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class StatusBill {
    private Boolean isDone;
    private String name;
    @ServerTimestamp
    private Date date;
}

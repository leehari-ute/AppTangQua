package com.example.app_quatang;

import android.provider.ContactsContract;

public class User {
    public String FullName;
    public String Phone;
    public String Email;
    public String Gender;

    public User() {

    }

    public User(String fullName, String phone, String email, String gender) {
        FullName = fullName;
        Phone = phone;
        Email = email;
        Gender = gender;
    }
}

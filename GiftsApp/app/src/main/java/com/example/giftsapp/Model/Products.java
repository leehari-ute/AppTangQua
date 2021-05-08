package com.example.giftsapp.Model;

import java.util.HashMap;
import java.util.Map;

public class Products {
    public String id;
    public String name;
    public String price;
    public String imageUrl;
    public String description;
    public int quantity;
    public String holiday;
    public String object;
    public String occasion;

    public Products(String id, String name, String price, String imageUrl, String description, int quantity, String holiday, String object,  String occasion) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.holiday = holiday;
        this.object = object;
        this.occasion = occasion;
    }

    public Products(String name, String price, String imageUrl, String description, int quantity, String holiday, String object,  String occasion) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.holiday = holiday;
        this.object = object;
        this.occasion = occasion;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getHoliday() {
        return holiday;
    }

    public String getObject() {
        return object;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }
}

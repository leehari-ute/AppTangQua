package com.example.giftsapp.Model;

import java.util.HashMap;
import java.util.Map;

public class Products {
    public String name;
    public String price;
    public String imageUrl;
    public String description;
    public int quantity;
    public String holiday;
    public String object;
    public String occasion;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice() {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setImageUrl() {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

//    public Map<String, Object> toMap() {
//        HashMap<String, Object> newProduct = new HashMap<>();
//
//        newProduct.put("Name", this.name);
//        newProduct.put("Price", this.price);
//        newProduct.put("ImageUrl", this.imageUrl);
//        newProduct.put("Quantity", this.quantity);
//        newProduct.put("Object", this.object);
//        newProduct.put("Holiday", this.holiday);
//        newProduct.put("Occasion", this.occasion);
//
//        return newProduct;
//    }

}

package com.example.giftsapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.type.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Products implements Parcelable {
    public String id;
    public String name;
    public String price;
    public String imageUrl;
    public String description;
    public String createAt;
    public int quantity;
    public String holiday;
    public String object;
    public String occasion;


    //load history bill
    public Products(String id, String name, String price, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    // get from firestore
    public Products(String id, String name, String price, String imageUrl, String description, String createAt, int quantity, String holiday, String object,  String occasion) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.createAt = createAt;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.holiday = holiday;
        this.object = object;
        this.occasion = occasion;
    }

    // add into firestore
    public Products(String name, String price, String imageUrl, String description, String createAt, int quantity, String holiday, String object,  String occasion) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createAt = createAt;
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

    protected Products(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        createAt = in.readString();
        quantity = in.readInt();
        holiday = in.readString();
        object = in.readString();
        occasion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(createAt);
        dest.writeInt(quantity);
        dest.writeString(holiday);
        dest.writeString(object);
        dest.writeString(occasion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}

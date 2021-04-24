package com.example.giftsapp.Model;

public class MainModel {
    int iconImage;
    String iconName;

    public MainModel(int iconImage, String iconName) {
        this.iconImage = iconImage;
        this.iconName = iconName;
    }

    public int getIconImage() {
        return iconImage;
    }

    public String getIconName() {
        return iconName;
    }

}

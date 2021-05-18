package com.example.giftsapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int addressID;
    private float createAt;
    private String paymentType;
    private ArrayList<Products> productsArrayList;
    private ArrayList<StatusBill> status;
    private String totalPrice;
    private String userID;
    private int quantityProduct;

    public Bill(int addressID, float createAt, String paymentType, ArrayList<Products> productsArrayList, ArrayList<StatusBill> status, String totalPrice, String userID, int quantityProduct) {
        this.addressID = addressID;
        this.createAt = createAt;
        this.paymentType = paymentType;
        this.productsArrayList = productsArrayList;
        this.status = status;
        this.totalPrice = totalPrice;
        this.userID = userID;
        this.quantityProduct = quantityProduct;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public float getCreateAt() {
        return createAt;
    }

    public void setCreateAt(float createAt) {
        this.createAt = createAt;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public ArrayList<Products> getProductsArrayList() {
        return productsArrayList;
    }

    public void setProductsArrayList(ArrayList<Products> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<StatusBill> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<StatusBill> status) {
        this.status = status;
    }

    public int getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(int quantityProduct) {
        this.quantityProduct = quantityProduct;
    }
}

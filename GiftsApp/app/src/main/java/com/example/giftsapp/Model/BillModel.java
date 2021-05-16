package com.example.giftsapp.Model;

public class BillModel {
    private String Status;
    private String FirstProduct;
    private String FirstPrice;
    private String Total;
    private String Quantity;
    private int ImageProduct;

    public BillModel(String status, String firstProduct, String firstPrice, String total, String quantity, int imageProduct) {
        Status = status;
        FirstProduct = firstProduct;
        FirstPrice = firstPrice;
        Total = total;
        Quantity = quantity;
        ImageProduct = imageProduct;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFirstProduct() {
        return FirstProduct;
    }

    public void setFirstProduct(String firstProduct) {
        FirstProduct = firstProduct;
    }

    public String getFirstPrice() {
        return FirstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        FirstPrice = firstPrice;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public int getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(int imageProduct) {
        ImageProduct = imageProduct;
    }
}

package com.example.giftsapp.Model;

public class BillModel {
    private String firstProductID;
    private String Status;
    private String FirstProduct;
    private String FirstPrice;
    private String Total;
    private String Quantity;
    private String imgUrl;

    public BillModel(String firstProductID, String status, String firstProduct, String firstPrice, String total, String quantity, String imgUrl) {
        this.firstProductID = firstProductID;
        Status = status;
        FirstProduct = firstProduct;
        FirstPrice = firstPrice;
        Total = total;
        Quantity = quantity;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFirstProductID() {
        return firstProductID;
    }

    public void setFirstProductID(String firstProductID) {
        this.firstProductID = firstProductID;
    }
}

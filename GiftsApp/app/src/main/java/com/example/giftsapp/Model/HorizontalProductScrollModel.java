package com.example.giftsapp.Model;

public class HorizontalProductScrollModel {
    private String productImage;
    private String productName;
    private String productDescription;
    private String productPrice;
    private String id; // id products
    private String productDetailsDescription; // mô tả chi tiết cho sản phẩm

    public HorizontalProductScrollModel(String productImage, String productName, String productDescription, String productPrice, String id, String productDetailsDescription)
    {
        this.productImage = productImage;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.id = id;
        this.productDetailsDescription=productDetailsDescription;
    }

    public String getProductDetailsDescription() {
        return productDetailsDescription;
    }

    public void setProductDetailsDescription(String productDetailsDescription) {
        this.productDetailsDescription = productDetailsDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}

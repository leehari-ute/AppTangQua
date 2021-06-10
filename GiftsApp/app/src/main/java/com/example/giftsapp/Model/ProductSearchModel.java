package com.example.giftsapp.Model;

public class ProductSearchModel {

    private String ProductImage;
    private String ProductName;
    private String ProductPrice;
    private String productDescription; // mô tả cho loại sp
    private String productDetailDescription; // mô tả chi tiết
    private String ProductId;

    public ProductSearchModel(String productImage, String productName, String productPrice, String productDescription
            , String productId, String productDetailDescription) {
        ProductImage = productImage;
        ProductName = productName;
        ProductPrice = productPrice;
        this.productDescription = productDescription;
        ProductId = productId;
        this.productDetailDescription = productDetailDescription;
    }

    public String getProductDetailDescription() {
        return productDetailDescription;
    }

    public void setProductDetailDescription(String productDetailDescription) {
        this.productDetailDescription = productDetailDescription;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }
}

package com.example.giftsapp.Model;

public class CartCurrentModel {
    public String ProductID;
    public int ProductQuantity;

    public CartCurrentModel(String productID, int productQuantity) {
        ProductID = productID;
        ProductQuantity = productQuantity;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }
}

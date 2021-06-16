package com.example.giftsapp.Model;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1; // số lượng

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //// Cart item
    private String productImage;
    private String productTitle; // Name
    private int freeVAT;
    private String productPrice;
    private String cuttedPrice;
    private int productQuantity;
    private int codeSale;
    private String proID;
    private String proStatus; // check còn hàng hay hết hàng

    public CartItemModel(int type, String productImage, String productTitle, int freeVAT, String productPrice, String cuttedPrice
            , int productQuantity, int codeSale, String proID, String proStatus) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeVAT = freeVAT;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.codeSale = codeSale;
        this.proID = proID;
        this.proStatus = proStatus;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getFreeVAT() {
        return freeVAT;
    }

    public void setFreeVAT(int freeVAT) {
        this.freeVAT = freeVAT;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getCodeSale() {
        return codeSale;
    }

    public void setCodeSale(int codeSale) {
        this.codeSale = codeSale;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    //// Cart item

    ///////// Cart Total Amount
    private int totalItem; // số lượng sản phẩm
    private String totalItemPrice; // tổng tiền SP
    private String deliveryPrice; // tiền phí vận chuyển
    private String saveAmount; // tiết kiệm
    private String totalAmount; // tổng tiền

    public CartItemModel(int type,int totalItem, String totalItemPrice, String deliveryPrice, String saveAmount, String totalAmount) {
        this.type=type;
        this.totalItem = totalItem;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.saveAmount = saveAmount;
        this.totalAmount = totalAmount;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(String saveAmount) {
        this.saveAmount = saveAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
///////// Cart Total Amount
}

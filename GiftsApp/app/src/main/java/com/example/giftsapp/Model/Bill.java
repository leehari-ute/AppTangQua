package com.example.giftsapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Bill implements Parcelable {
    private String id;
    private String addressID;
    private Date createAt;
    private String paymentType;
    private ArrayList<Products> productsArrayList;
    private ArrayList<StatusBill> status;
    private String totalPrice;
    private String userID;
    private int quantityProduct;
    private String feeShip;
    private String message;

    //get from Firebase
    public Bill(String id, String addressID, Date createAt, String paymentType, ArrayList<Products> productsArrayList, ArrayList<StatusBill> status, String totalPrice, String userID, int quantityProduct, String feeShip, String message) {
        this.id = id;
        this.addressID = addressID;
        this.createAt = createAt;
        this.paymentType = paymentType;
        this.productsArrayList = productsArrayList;
        this.status = status;
        this.totalPrice = totalPrice;
        this.userID = userID;
        this.quantityProduct = quantityProduct;
        this.feeShip = feeShip;
        this.message = message;
    }

    //save to firebase
    public Bill(String addressID, Date createAt, String paymentType, ArrayList<Products> productsArrayList, ArrayList<StatusBill> status, String totalPrice, String userID, int quantityProduct, String feeShip, String message) {
        this.addressID = addressID;
        this.createAt = createAt;
        this.paymentType = paymentType;
        this.productsArrayList = productsArrayList;
        this.status = status;
        this.totalPrice = totalPrice;
        this.userID = userID;
        this.quantityProduct = quantityProduct;
        this.feeShip = feeShip;
        this.message = message;
    }

    protected Bill(Parcel in) {
        id = in.readString();
        addressID = in.readString();
        long tmpCreateAt = in.readLong();
        createAt = tmpCreateAt != -1 ? new Date(tmpCreateAt) : null;
        paymentType = in.readString();
        if (in.readByte() == 0x01) {
            productsArrayList = new ArrayList<Products>();
            in.readList(productsArrayList, Products.class.getClassLoader());
        } else {
            productsArrayList = null;
        }
        if (in.readByte() == 0x01) {
            status = new ArrayList<StatusBill>();
            in.readList(status, StatusBill.class.getClassLoader());
        } else {
            status = null;
        }
        totalPrice = in.readString();
        userID = in.readString();
        quantityProduct = in.readInt();
        feeShip = in.readString();
        message = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(addressID);
        dest.writeLong(createAt != null ? createAt.getTime() : -1L);
        dest.writeString(paymentType);
        if (productsArrayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productsArrayList);
        }
        if (status == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(status);
        }
        dest.writeString(totalPrice);
        dest.writeString(userID);
        dest.writeInt(quantityProduct);
        dest.writeString(feeShip);
        dest.writeString(message);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Bill> CREATOR = new Parcelable.Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(String feeShip) {
        this.feeShip = feeShip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

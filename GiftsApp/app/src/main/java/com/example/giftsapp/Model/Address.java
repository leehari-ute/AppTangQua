package com.example.giftsapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private int ID;
    private String name;
    private String phone;
    private String detailAddress;
    private String village;
    private String district;
    private String province;
    private boolean isDefault;

    public Address(int ID, String name, String phone, String detailAddress, String village, String district, String province, boolean isDefault) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.detailAddress = detailAddress;
        this.village = village;
        this.district = district;
        this.province = province;
        this.isDefault = isDefault;
    }

    protected Address(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        phone = in.readString();
        detailAddress = in.readString();
        village = in.readString();
        district = in.readString();
        province = in.readString();
        isDefault = in.readByte() != 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(detailAddress);
        dest.writeString(village);
        dest.writeString(district);
        dest.writeString(province);
        dest.writeByte((byte) (isDefault ? 1 : 0));
    }
}

package com.example.giftsapp.Model;

public class Address {
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
}

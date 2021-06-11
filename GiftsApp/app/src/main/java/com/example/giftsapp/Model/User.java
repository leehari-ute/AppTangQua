package com.example.giftsapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String id;
    private String avtUrl;
    private String bio;
    private String email;
    private String fullName;
    private String gender;
    private String phone;
    private String role;
    private List<Address> addressList;

    public User(String id, String avtUrl, String bio, String email, String fullName, String gender, String phone, String role, List<Address> addressList) {
        this.id = id;
        this.avtUrl = avtUrl;
        this.bio = bio;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
        this.addressList = addressList;
    }

    protected User(Parcel in) {
        id = in.readString();
        avtUrl = in.readString();
        bio = in.readString();
        email = in.readString();
        fullName = in.readString();
        gender = in.readString();
        phone = in.readString();
        role = in.readString();
        if (in.readByte() == 0x01) {
            addressList = new ArrayList<Address>();
            in.readList(addressList, Products.class.getClassLoader());
        } else {
            addressList = null;
        }
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(avtUrl);
        dest.writeString(bio);
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeString(role);
        if (addressList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(addressList);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvtUrl() {
        return avtUrl;
    }

    public void setAvtUrl(String avtUrl) {
        this.avtUrl = avtUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}

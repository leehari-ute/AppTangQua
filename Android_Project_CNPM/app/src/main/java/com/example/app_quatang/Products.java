package com.example.app_quatang;

public class Products {
    private String tenSp;
    private String gia;
    private int hinh;

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public Products(String tenSp, String gia, int hinh) {
        this.tenSp = tenSp;
        this.gia = gia;
        this.hinh = hinh;


    }
}

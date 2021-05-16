package com.example.giftsapp.API.Address.Model;

public class District {
    private int Type;
    private String SolrID;
    private int ID;
    private String Title;
    private int STT;
    private int TinhThanhID;
    private String TinhThanhTitle;
    private String TinhThanhTitleAscii;
    private float Created;
    private float Updated;

    public District(int type, String solrID, int ID, String title, int STT, int tinhThanhID, String tinhThanhTitle, String tinhThanhTitleAscii, float created, float updated) {
        Type = type;
        SolrID = solrID;
        this.ID = ID;
        Title = title;
        this.STT = STT;
        TinhThanhID = tinhThanhID;
        TinhThanhTitle = tinhThanhTitle;
        TinhThanhTitleAscii = tinhThanhTitleAscii;
        Created = created;
        Updated = updated;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getSolrID() {
        return SolrID;
    }

    public void setSolrID(String solrID) {
        SolrID = solrID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public int getTinhThanhID() {
        return TinhThanhID;
    }

    public void setTinhThanhID(int tinhThanhID) {
        TinhThanhID = tinhThanhID;
    }

    public String getTinhThanhTitle() {
        return TinhThanhTitle;
    }

    public void setTinhThanhTitle(String tinhThanhTitle) {
        TinhThanhTitle = tinhThanhTitle;
    }

    public String getTinhThanhTitleAscii() {
        return TinhThanhTitleAscii;
    }

    public void setTinhThanhTitleAscii(String tinhThanhTitleAscii) {
        TinhThanhTitleAscii = tinhThanhTitleAscii;
    }

    public float getCreated() {
        return Created;
    }

    public void setCreated(float created) {
        Created = created;
    }

    public float getUpdated() {
        return Updated;
    }

    public void setUpdated(float updated) {
        Updated = updated;
    }

}

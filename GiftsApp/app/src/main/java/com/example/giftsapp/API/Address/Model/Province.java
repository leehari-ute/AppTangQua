package com.example.giftsapp.API.Address.Model;

public class Province {
    private int Type;
    private String SolrID;
    private int ID;
    private String Title;
    private int STT;
    private float Created;
    private float Updated;
    private int TotalDoanhNghiep;

    public Province(int type, String solrID, int ID, String title, int STT, float created, float updated, int totalDoanhNghiep) {
        Type = type;
        SolrID = solrID;
        this.ID = ID;
        Title = title;
        this.STT = STT;
        Created = created;
        Updated = updated;
        TotalDoanhNghiep = totalDoanhNghiep;
    }

    public void setType(int type) {
        Type = type;
    }

    public void setSolrID(String solrID) {
        SolrID = solrID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public void setCreated(float created) {
        Created = created;
    }

    public void setUpdated(float updated) {
        Updated = updated;
    }

    public void setTotalDoanhNghiep(int totalDoanhNghiep) {
        TotalDoanhNghiep = totalDoanhNghiep;
    }

    public int getType() {
        return Type;
    }

    public String getSolrID() {
        return SolrID;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public int getSTT() {
        return STT;
    }

    public float getCreated() {
        return Created;
    }

    public float getUpdated() {
        return Updated;
    }

    public int getTotalDoanhNghiep() {
        return TotalDoanhNghiep;
    }
}

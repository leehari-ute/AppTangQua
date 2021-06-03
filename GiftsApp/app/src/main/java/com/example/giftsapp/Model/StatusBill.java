package com.example.giftsapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class StatusBill implements Parcelable {
    private Boolean isPresent;
    private String name;
    private Date date;

    public StatusBill(Boolean isPresent, String name, Date date) {
        this.isPresent = isPresent;
        this.name = name;
        this.date = date;
    }

    public Boolean getDone() {
        return isPresent;
    }

    public void setDone(Boolean done) {
        isPresent = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    protected StatusBill(Parcel in) {
        byte isDoneVal = in.readByte();
        isPresent = isDoneVal == 0x02 ? null : isDoneVal != 0x00;
        name = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (isPresent == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isPresent ? 0x01 : 0x00));
        }
        dest.writeString(name);
        dest.writeLong(date != null ? date.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StatusBill> CREATOR = new Parcelable.Creator<StatusBill>() {
        @Override
        public StatusBill createFromParcel(Parcel in) {
            return new StatusBill(in);
        }

        @Override
        public StatusBill[] newArray(int size) {
            return new StatusBill[size];
        }
    };
}
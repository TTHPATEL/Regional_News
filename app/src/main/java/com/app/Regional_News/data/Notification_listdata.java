package com.app.Regional_News.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Notification_listdata  implements Parcelable {
    @SerializedName("noti_id")
    public String noti_id;
    @SerializedName("noti_title")
    public String noti_title;
    @SerializedName("noti_desc")
    public String noti_desc;
    @SerializedName("noti_time")
    public String noti_time;
    @SerializedName("noti_status")
    public String noti_status;

    public String getNoti_id() {
        return noti_id;
    }

    public void setNoti_id(String noti_id) {
        this.noti_id = noti_id;
    }

    public String getNoti_title() {
        return noti_title;
    }

    public void setNoti_title(String noti_title) {
        this.noti_title = noti_title;
    }

    public String getNoti_desc() {
        return noti_desc;
    }

    public void setNoti_desc(String noti_desc) {
        this.noti_desc = noti_desc;
    }

    public String getNoti_time() {
        return noti_time;
    }

    public void setNoti_time(String noti_time) {
        this.noti_time = noti_time;
    }

    public String getNoti_status() {
        return noti_status;
    }

    public void setNoti_status(String noti_status) {
        this.noti_status = noti_status;
    }


    protected Notification_listdata(Parcel in) {
        noti_id = in.readString();
        noti_title = in.readString();
        noti_desc = in.readString();
        noti_time = in.readString();
        noti_status = in.readString();
    }

    public static final Creator<Notification_listdata> CREATOR = new Creator<Notification_listdata>() {
        @Override
        public Notification_listdata createFromParcel(Parcel in) {
            return new Notification_listdata(in);
        }

        @Override
        public Notification_listdata[] newArray(int size) {
            return new Notification_listdata[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noti_id);
        dest.writeString(noti_title);
        dest.writeString(noti_desc);
        dest.writeString(noti_time);
        dest.writeString(noti_status);
    }

}

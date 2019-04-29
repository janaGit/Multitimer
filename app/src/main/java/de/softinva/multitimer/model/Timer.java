package de.softinva.multitimer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Timer implements Parcelable {
    protected String id;
    protected String groupId;
    protected String title;
    protected Integer durationInSec;
    protected String imageName;

    public Timer(){

    }
    public Timer(String id, String title, Integer durationInSec, String imageName) {
        this.id = id;
        this.groupId = "";
        this.durationInSec = durationInSec;
        this.title = title;
        this.imageName = imageName;
    }

    public Timer(String id,String groupId, String title, Integer durationInSec, String imageName) {
        this.id = id;
        this.groupId = groupId;
        this.durationInSec = durationInSec;
        this.title = title;
        this.imageName = imageName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(Integer durationInSec) {
        this.durationInSec = durationInSec;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public static Creator<Timer> getCREATOR() {
        return CREATOR;
    }

    protected Timer(Parcel in) {
        id = in.readString();
        groupId = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            durationInSec = null;
        } else {
            durationInSec = in.readInt();
        }
        imageName = in.readString();
    }

    public static final Creator<Timer> CREATOR = new Creator<Timer>() {
        @Override
        public Timer createFromParcel(Parcel in) {
            return new Timer(in);
        }

        @Override
        public Timer[] newArray(int size) {
            return new Timer[size];
        }
    };

    @Override
    public String toString() {
        return this.title;
    }

    public Timer toTimer(){
        return new Timer(id, groupId, title, durationInSec, imageName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(groupId);
        dest.writeString(title);
        if (durationInSec == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(durationInSec);
        }
        dest.writeString(imageName);
    }
}

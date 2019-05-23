package de.softinva.multitimer.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import de.softinva.multitimer.BR;

public class Timer extends BaseObservable implements Parcelable {
    protected String id;
    protected String title;
    protected int durationInSec;
    protected String imageName;

    public Timer() {

    }

    public Timer(String id, String title, int durationInSec, String imageName) {
        this.id = id;
        this.durationInSec = durationInSec;
        this.title = title;
        this.imageName = imageName;
    }


    protected Timer(Parcel in) {
        id = in.readString();
        title = in.readString();
        durationInSec = in.readInt();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public Integer getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(Integer durationInSec) {
        this.durationInSec = durationInSec;
        notifyPropertyChanged(BR.durationInSec);
    }

    @Bindable
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
        notifyPropertyChanged(BR.imageName);
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeInt(durationInSec);
        dest.writeString(imageName);
    }


}

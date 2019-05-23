package de.softinva.multitimer.model;

import android.os.Parcel;
import android.os.Parcelable;

import de.softinva.multitimer.utility.UtilityMethods;

public class TempTimer extends Timer implements Parcelable {

    String imageName = "default.jpg";

    public TempTimer() {
        id = UtilityMethods.createID();
    }

    public TempTimer(Timer timer) {
        super(timer.id, timer.title, timer.durationInSec, timer.imageName);
    }

    public TempTimer(String id, String title, Integer durationInSec) {
        super(id, title, durationInSec, null);
    }

    public static final Creator<Timer> CREATOR = new Creator<Timer>() {
        @Override
        public TempTimer createFromParcel(Parcel in) {
            return new TempTimer(in);
        }

        @Override
        public TempTimer[] newArray(int size) {
            return new TempTimer[size];
        }
    };

    protected TempTimer(Parcel in) {
        super(in);
    }
}

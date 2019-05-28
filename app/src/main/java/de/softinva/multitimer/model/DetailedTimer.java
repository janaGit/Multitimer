package de.softinva.multitimer.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.Bindable;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.database.DetailedTimerEntity;
import de.softinva.multitimer.utility.UtilityMethods;

public class DetailedTimer extends Timer implements Parcelable {
    private String groupId;
    private String description = "";
    private int positionInGroup;
    private int coolDownInSec;
    private boolean isEnabled = true;

    public DetailedTimer() {
        super();

    }

    public DetailedTimer(String groupId) {
        super(UtilityMethods.createID(), "", 30, "");
        this.groupId = groupId;
    }

    public DetailedTimer(String id, String groupId, String title, int durationInSec, int coolDownInSec, boolean isEnabled, String imageName, int positionInGroup, String description) {
        super(id, title, durationInSec, imageName);
        this.groupId = groupId;
        this.description = description;
        this.positionInGroup = positionInGroup;
        this.coolDownInSec = coolDownInSec;
        this.isEnabled = isEnabled;
    }


    public DetailedTimer(DetailedTimerEntity entity) {
        this.id = entity.id;
        this.groupId = entity.groupId;
        this.title = entity.title;
        this.durationInSec = entity.durationInSec;
        this.imageName = entity.imageName;
        this.description = entity.description;
        this.positionInGroup = entity.positionInGroup;
        this.coolDownInSec = entity.coolDownInSec;
        this.isEnabled = entity.isEnabled == 1;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Override
    public String toString() {
        return this.title;
    }

    public void toCopy(DetailedTimer timer) {
        timer.setId(id);
        timer.setGroupId(groupId);
        timer.setTitle(title);
        timer.setDurationInSec(durationInSec);
        timer.setImageName(imageName);
        timer.setDescription(description);
        timer.setPositionInGroup(positionInGroup);
        timer.setCoolDownInSec(coolDownInSec);
        timer.setIsEnabled(isEnabled);
    }

    public DetailedTimerEntity toEntity() {
        DetailedTimerEntity entity = new DetailedTimerEntity();
        entity.id = id;
        entity.groupId = groupId;
        entity.title = title;
        entity.durationInSec = durationInSec;
        entity.imageName = imageName;
        entity.description = description;
        entity.positionInGroup = positionInGroup;
        entity.coolDownInSec = coolDownInSec;
        entity.isEnabled = isEnabled ? 1 : 0;
        return entity;
    }

    @Bindable
    public int getCoolDownInSec() {
        return coolDownInSec;
    }

    @Bindable
    public boolean getIsEnabled() {
        return isEnabled;
    }

    public int getPositionInGroup() {
        return positionInGroup;
    }

    public void setPositionInGroup(Integer positionInGroup) {
        this.positionInGroup = positionInGroup;
    }


    public void setCoolDownInSec(Integer coolDownInSec) {
        this.coolDownInSec = coolDownInSec;
        notifyPropertyChanged(BR.coolDownInSec);
    }

    public void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
        notifyPropertyChanged(BR.isEnabled);
    }

    protected DetailedTimer(Parcel in) {
        super(in);
        description = in.readString();
        groupId = in.readString();
        title = in.readString();
        isEnabled = in.readByte() != 0;
        coolDownInSec = in.readInt();
        positionInGroup = in.readInt();
    }

    public static final Creator<Timer> CREATOR = new Creator<Timer>() {
        @Override
        public DetailedTimer createFromParcel(Parcel in) {
            return new DetailedTimer(in);
        }

        @Override
        public DetailedTimer[] newArray(int size) {
            return new DetailedTimer[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(description);
        dest.writeString(groupId);
        dest.writeString(title);

        dest.writeByte((byte) (isEnabled ? 1 : 0));
        dest.writeInt(coolDownInSec);
        dest.writeInt(positionInGroup);


    }
}

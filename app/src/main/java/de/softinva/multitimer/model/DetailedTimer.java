package de.softinva.multitimer.model;

import androidx.databinding.Bindable;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.database.DetailedTimerEntity;
import de.softinva.multitimer.utility.UtilityMethods;

public class DetailedTimer extends Timer {
    private String description;
    private Integer positionInGroup;
    private int coolDownInSec;
    private boolean isEnabled;

    public DetailedTimer() {
        super();

    }

    public DetailedTimer(String groupId) {
        super(UtilityMethods.createID(), groupId, "", 30, "");
        this.groupId = groupId;
        description = "";
    }

    public DetailedTimer(String id, String groupId, String title, Integer durationInSec, Integer coolDownInSec, boolean isEnabled, String imageName, Integer positionInGroup, String description) {
        super(id, groupId, title, durationInSec, imageName);
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
    public boolean isEnabled() {
        return isEnabled;
    }

    public int getPositionInGroup() {
        return positionInGroup;
    }

    public void setPositionInGroup(int positionInGroup) {
        this.positionInGroup = positionInGroup;
    }

    public void setCoolDownInSec(int coolDownInSec) {
        this.coolDownInSec = coolDownInSec;
        notifyPropertyChanged(BR.coolDownInSec);
    }

    public void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
        notifyPropertyChanged(BR.enabled);
    }
}

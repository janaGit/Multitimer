package de.softinva.multitimer.model;

import androidx.databinding.Bindable;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.database.DetailedTimerEntity;
import de.softinva.multitimer.utility.UtilityMethods;

public class DetailedTimer extends Timer {
    protected String description;
    protected Integer positionInGroup;

    public DetailedTimer() {
        super();

    }

    public DetailedTimer(String groupId) {
        super(UtilityMethods.createID(), groupId, "", 30, "");
        this.groupId = groupId;
        description = "";
    }

    public DetailedTimer(String id, String groupId, String title, Integer durationInSec, String imageName, Integer positionInGroup, String description) {
        super(id, groupId, title, durationInSec, imageName);
        this.groupId = groupId;
        this.description = description;
        this.positionInGroup = positionInGroup;
    }

    public DetailedTimer(DetailedTimerEntity entity) {
        this.id = entity.id;
        this.groupId = entity.groupId;
        this.title = entity.title;
        this.durationInSec = entity.durationInSec;
        this.imageName = entity.imageName;
        this.description = entity.description;
        this.positionInGroup = entity.positionInGroup;
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
        return entity;
    }

    public int getPositionInGroup() {
        return positionInGroup;
    }

    public void setPositionInGroup(int positionInGroup) {
        this.positionInGroup = positionInGroup;
    }
}

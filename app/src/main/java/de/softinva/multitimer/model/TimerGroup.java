package de.softinva.multitimer.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.TreeMap;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.database.TimerGroupEntity;


public class TimerGroup extends BaseObservable {
    protected String id;
    protected String title;
    protected boolean isZipped;
    protected String imageName;
    protected String description;

    public TimerGroup() {

    }

    public TimerGroup(String id) {
        this.id = id;
        this.title = "";
        this.isZipped = false;
        this.imageName = "";
        this.description = "";
    }

    public TimerGroup(TimerGroupEntity entity) {
        this.id = entity.id;
        this.title = entity.title;
        this.isZipped = entity.isZipped;
        this.imageName = entity.imageName;
        this.description = entity.description;
    }

    public TimerGroup(String id, String title, boolean isZipped, String imageName, String description) {
        this.id = id;
        this.title = title;
        this.isZipped = isZipped;
        this.imageName = imageName;
        this.description = description;
    }

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

    public boolean isZipped() {
        return isZipped;
    }

    public void setZipped(boolean zipped) {
        isZipped = zipped;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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


    public void toCopy(TimerGroup timerGroup) {
        timerGroup.setId(id);
        timerGroup.setTitle(title);
        timerGroup.setZipped(isZipped);
        timerGroup.setImageName(imageName);
        timerGroup.setDescription(description);
    }

    public TimerGroupEntity toEntity() {
        TimerGroupEntity entity = new TimerGroupEntity();
        entity.id = id;
        entity.title = title;
        entity.isZipped = isZipped;
        entity.imageName = imageName;
        entity.description = description;
        return entity;
    }
}

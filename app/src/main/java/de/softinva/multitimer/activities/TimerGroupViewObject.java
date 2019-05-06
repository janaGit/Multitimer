package de.softinva.multitimer.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupViewObject extends AppViewObject<LiveData<TimerGroup>> {
    public TimerGroupViewObject(LiveData<TimerGroup> obj) {
        super(obj);
    }
}

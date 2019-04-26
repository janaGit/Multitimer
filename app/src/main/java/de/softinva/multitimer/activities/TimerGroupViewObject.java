package de.softinva.multitimer.activities;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupViewObject extends AppViewObject<MutableLiveData<TimerGroup>> {
    public TimerGroupViewObject(MutableLiveData<TimerGroup> obj) {
        super(obj);
    }
}

package de.softinva.multitimer.activities.timergroup.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupInfoViewObject extends AppViewObject<LiveData<TimerGroup>> {
    public TimerGroupInfoViewObject(LiveData<TimerGroup> obj) {
        super(obj);
    }
}

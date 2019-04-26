package de.softinva.multitimer.activities.timergroup.info;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupInfoViewObject extends AppViewObject<MutableLiveData<TimerGroup>> {
    public TimerGroupInfoViewObject(MutableLiveData<TimerGroup> obj) {
        super(obj);
    }
}

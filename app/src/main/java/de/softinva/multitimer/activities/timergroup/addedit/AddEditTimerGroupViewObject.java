package de.softinva.multitimer.activities.timergroup.addedit;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class AddEditTimerGroupViewObject extends AppViewObject<MutableLiveData<TimerGroup>> {
    public AddEditTimerGroupViewObject(MutableLiveData<TimerGroup> obj) {
        super(obj);
    }
}

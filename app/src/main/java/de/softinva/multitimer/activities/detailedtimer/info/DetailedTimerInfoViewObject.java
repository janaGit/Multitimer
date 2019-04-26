package de.softinva.multitimer.activities.detailedtimer.info;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.RunningTimer;

public class DetailedTimerInfoViewObject extends AppViewObject<MutableLiveData<RunningTimer>> {
    public DetailedTimerInfoViewObject(MutableLiveData<RunningTimer> obj) {
        super(obj);
    }
}

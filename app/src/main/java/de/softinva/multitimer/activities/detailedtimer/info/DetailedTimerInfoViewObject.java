package de.softinva.multitimer.activities.detailedtimer.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.RunningTimer;

public class DetailedTimerInfoViewObject extends AppViewObject<LiveData<RunningTimer>> {
    public DetailedTimerInfoViewObject(LiveData<RunningTimer> obj) {
        super(obj);
    }
}

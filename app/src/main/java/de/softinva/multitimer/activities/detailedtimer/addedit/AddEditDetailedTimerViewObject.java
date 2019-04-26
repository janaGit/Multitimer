package de.softinva.multitimer.activities.detailedtimer.addedit;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.RunningTimer;

public class AddEditDetailedTimerViewObject extends AppViewObject<MutableLiveData<RunningTimer>> {
    public AddEditDetailedTimerViewObject(MutableLiveData<RunningTimer> obj) {
        super(obj);
    }
}

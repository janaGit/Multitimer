package de.softinva.multitimer.activities.timergroup.add;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject;
import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.utility.UtilityMethods;

public class AddTimerGroupViewModel extends AppViewModel {
    protected TimerGroup timerGroup;
    public AddTimerGroupViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    public TimerGroup createNewTimerGroup() {
        if (timerGroup == null) {
            timerGroup = new TimerGroup(UtilityMethods.createID());
        } else {
            throw new Error("timer group already initialized!");
        }
        return timerGroup;
    }
    public TimerGroup getTimerGroup() {
        if(timerGroup == null){
            throw new Error("Timer group has not been initialized!");
        }
        return timerGroup;
    }
}

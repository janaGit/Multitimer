package de.softinva.multitimer.activities.timergroup.addedit;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AbstractTimerGroupViewModel;
import de.softinva.multitimer.model.TimerGroup;

public class AddEditTimerGroupViewModel extends AbstractTimerGroupViewModel {

    public AddEditTimerGroupViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    public MutableLiveData<TimerGroup> createNewTimerGroup() {
        if (timerGroup$ == null) {
            timerGroup$ = new MutableLiveData<>(new TimerGroup());
            getTimerGroupId$().setValue(timerGroup$.getValue().getId());
        } else {
            throw new Error("timer group already initialized!");
        }
        return timerGroup$;
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditTimerGroupViewObject(timerGroup$);
    }
}

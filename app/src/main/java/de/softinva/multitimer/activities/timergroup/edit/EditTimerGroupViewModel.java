package de.softinva.multitimer.activities.timergroup.edit;


import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject;
import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupViewModel;
import de.softinva.multitimer.model.TimerGroup;

public class EditTimerGroupViewModel extends AbstractTimerGroupViewModel {
    protected TimerGroup timerGroup;
    public EditTimerGroupViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    public TimerGroup getTimerGroup() {
        if (timerGroup == null) {
            timerGroup = new TimerGroup();
        }
        return timerGroup;
    }
    @Override
    protected void setViewObject() {
        viewObject = new AddEditTimerGroupViewObject(getTimerGroup());
    }
}

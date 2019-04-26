package de.softinva.multitimer.activities.timergroup.addedit;


import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.AbstractTimerGroupViewModel;

public class AddEditTimerGroupViewModel extends AbstractTimerGroupViewModel {

    public AddEditTimerGroupViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditTimerGroupViewObject(timerGroup$);
    }
}

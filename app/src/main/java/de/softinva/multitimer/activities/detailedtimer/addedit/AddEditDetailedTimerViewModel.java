package de.softinva.multitimer.activities.detailedtimer.addedit;

import androidx.lifecycle.SavedStateHandle;


import de.softinva.multitimer.classes.AbstractDetailedTimerViewModel;

public class AddEditDetailedTimerViewModel extends AbstractDetailedTimerViewModel {

    public AddEditDetailedTimerViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditDetailedTimerViewObject(timer);
    }
}

package de.softinva.multitimer.activities.detailedtimer.edit;

import androidx.lifecycle.SavedStateHandle;


import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.classes.AbstractDetailedTimerViewModel;
import de.softinva.multitimer.model.DetailedTimer;

public class EditDetailedTimerViewModel extends AbstractDetailedTimerViewModel {
    protected DetailedTimer detailedTimer;

    public EditDetailedTimerViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    public DetailedTimer getDetailedTimer() {
        if (detailedTimer == null) {
            detailedTimer = new DetailedTimer();
        }
        return detailedTimer;
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditDetailedTimerViewObject(getDetailedTimer());
    }
}

package de.softinva.multitimer.activities.detailedtimer.info;

import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.detailedtimer.AbstractDetailedTimerViewModel;

public class DetailedTimerInfoViewModel extends AbstractDetailedTimerViewModel {

    public DetailedTimerInfoViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    @Override
    protected void setViewObject() {
        viewObject = new DetailedTimerInfoViewObject(getRunningTimer(getTimerGroupId().getValue(),getTimerId().getValue()));
    }
}

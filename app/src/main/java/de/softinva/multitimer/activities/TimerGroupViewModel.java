package de.softinva.multitimer.activities;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupViewModel;
import de.softinva.multitimer.classes.ActivityTabsViewModel;
import de.softinva.multitimer.model.TABS;


public class TimerGroupViewModel extends AbstractTimerGroupViewModel implements ActivityTabsViewModel {
    private MutableLiveData<TABS> activeTab;

    public TimerGroupViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    @Override
    protected void setViewObject() {
        viewObject = new TimerGroupViewObject(timerGroup$);
    }


    @Override
    public MutableLiveData<TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

}

package de.softinva.multitimer.activities;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupViewModel;
import de.softinva.multitimer.classes.interfaces.ActivityTabsViewModel;
import de.softinva.multitimer.model.TABS;


public class TimerGroupViewModel extends AbstractTimerGroupViewModel implements ActivityTabsViewModel {
    private MutableLiveData<TABS> activeTab;

    public TimerGroupViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }


    @Override
    public MutableLiveData<TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

}

package de.softinva.multitimer.activities.main.timergroup;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupViewModel;
import de.softinva.multitimer.model.TIMER_GROUP_ACTIVITY_TABS;


public class TimerGroupViewModel extends AbstractTimerGroupViewModel {
    private MutableLiveData<TIMER_GROUP_ACTIVITY_TABS> activeTab;

    public TimerGroupViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }



    public MutableLiveData<TIMER_GROUP_ACTIVITY_TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

}

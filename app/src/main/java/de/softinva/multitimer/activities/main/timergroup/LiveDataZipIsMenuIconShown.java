package de.softinva.multitimer.activities.main.timergroup;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.model.TIMER_GROUP_ACTIVITY_TABS;

public class LiveDataZipIsMenuIconShown extends MediatorLiveData<Boolean> {
    private boolean isDetailedTimerListTabOpen;
    private boolean doDisabledTimersExists;

    public LiveDataZipIsMenuIconShown(LiveData<Boolean> _doDisabledTimersExists, MutableLiveData<TIMER_GROUP_ACTIVITY_TABS> activeTab) {
        addSource(_doDisabledTimersExists, __doDisabledTimersExists -> {
            doDisabledTimersExists = __doDisabledTimersExists;
            setValue(computeResultValue());
        });
        addSource(activeTab, activeTabValue -> {
            isDetailedTimerListTabOpen = activeTabValue == TIMER_GROUP_ACTIVITY_TABS.List;
            setValue(computeResultValue());
        });
    }

    private boolean computeResultValue() {
        return doDisabledTimersExists && isDetailedTimerListTabOpen;
    }
}

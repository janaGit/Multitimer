package de.softinva.multitimer.activities.timergroup.edit;


import android.app.Application;

import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupViewModel;
import de.softinva.multitimer.model.TimerGroup;

public class EditTimerGroupViewModel extends AbstractTimerGroupViewModel {
    protected TimerGroup timerGroup;

    public EditTimerGroupViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public TimerGroup getTimerGroup() {
        if (timerGroup == null) {
            timerGroup = new TimerGroup();
        }
        return timerGroup;
    }
}

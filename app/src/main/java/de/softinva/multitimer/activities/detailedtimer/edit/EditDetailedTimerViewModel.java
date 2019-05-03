package de.softinva.multitimer.activities.detailedtimer.edit;

import android.app.Application;

import androidx.lifecycle.SavedStateHandle;


import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.activities.detailedtimer.AbstractDetailedTimerViewModel;
import de.softinva.multitimer.model.DetailedTimer;

public class EditDetailedTimerViewModel extends AbstractDetailedTimerViewModel {
    protected DetailedTimer detailedTimer;

    public EditDetailedTimerViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public DetailedTimer getDetailedTimer() {
        if (detailedTimer == null) {
            detailedTimer = new DetailedTimer();
        }
        return detailedTimer;
    }
}

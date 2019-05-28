package de.softinva.multitimer.activities.detailedtimer.add;

import android.app.Application;

import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;
import de.softinva.multitimer.model.DetailedTimer;

public class AddDetailedTimerViewModel extends AppViewModel {
    protected DetailedTimer detailedTimer;

    public AddDetailedTimerViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public DetailedTimer getDetailedTimer() {
        if (detailedTimer == null) {
            throw new Error("Detailed timer not yet created!");
        }
        return detailedTimer;
    }

    public DetailedTimer createNewDetailedTimer(String groupId) {
        if (detailedTimer == null) {
            detailedTimer = new DetailedTimer(groupId);
        } else {
            throw new Error("detailed timer already initialized!");
        }
        return detailedTimer;
    }
}

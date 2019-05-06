package de.softinva.multitimer.activities.detailedtimer.add;

import android.app.Application;

import androidx.lifecycle.SavedStateHandle;

import java.util.TreeMap;

import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class AddDetailedTimerViewModel extends AppViewModel {
    protected DetailedTimer detailedTimer;

    public AddDetailedTimerViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public DetailedTimer getDetailedTimer() {
        if (detailedTimer == null) {
            detailedTimer = new DetailedTimer();
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

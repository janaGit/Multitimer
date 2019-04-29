package de.softinva.multitimer.activities.detailedtimer.add;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.DetailedTimer;

public class AddDetailedTimerViewModel  extends AppViewModel {
    private MutableLiveData<String> timerId$;
    protected DetailedTimer detailedTimer;

    public AddDetailedTimerViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
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
    public MutableLiveData<String> getTimerId() {
        if (timerId$ == null) {
            timerId$ = state.getLiveData("timerId");
        }
        return timerId$;
    }
    @Override
    protected void setViewObject() {
        viewObject = new AddEditDetailedTimerViewObject(getDetailedTimer());
    }
}

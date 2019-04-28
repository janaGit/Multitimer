package de.softinva.multitimer.activities.detailedtimer.addedit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;


import de.softinva.multitimer.classes.AbstractDetailedTimerViewModel;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TimerGroup;

public class AddEditDetailedTimerViewModel extends AbstractDetailedTimerViewModel {

    public AddEditDetailedTimerViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }
    public MutableLiveData<RunningTimer> createNewRunningTimer(String groupId) {
        if (timer$ == null) {
            timer$ = new MutableLiveData<>(new RunningTimer(new DetailedTimer(groupId)));
            getTimerId().setValue(timer$.getValue().getTimer().getId());
        } else {
            throw new Error("timer group already initialized!");
        }
        return timer$;
    }
    @Override
    protected void setViewObject() {
        viewObject = new AddEditDetailedTimerViewObject(timer$);
    }
}

package de.softinva.multitimer.activities.timergroup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public abstract class AbstractTimerGroupViewModel extends AppViewModel {
    protected MutableLiveData<TimerGroup> timerGroup$;
    private MutableLiveData<String> timerGroupId$;

    public AbstractTimerGroupViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }


    public MutableLiveData<TimerGroup> getTimerGroup(String groupId) {
        if (timerGroup$ == null) {
            timerGroup$ = TimerRepository.getInstance().getTimerGroup(groupId);
        }
        return timerGroup$;
    }

    public MutableLiveData<String> getTimerGroupId$() {
        if (timerGroupId$ == null) {
            timerGroupId$ = state.getLiveData("groupId");
        }
        return timerGroupId$;
    }
}

package de.softinva.multitimer.activities.timergroup.info;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.AbstractTimerGroupViewModel;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class TimerGroupInfoViewModel extends AbstractTimerGroupViewModel {
    protected MutableLiveData<TimerGroup> timerGroup$;
    private MutableLiveData<String> timerGroupId$;

    public TimerGroupInfoViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    @Override
    protected void setViewObject() {
        viewObject = new TimerGroupInfoViewObject(timerGroup$);
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

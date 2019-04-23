package de.softinva.multitimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class TimerGroupInfoViewModel extends ViewModel {
    SavedStateHandle state;
    protected MutableLiveData<TimerGroup> timerGroup;
    private MutableLiveData<String> timerGroupId;

    public TimerGroupInfoViewModel(SavedStateHandle savedStateHandle) {
        state = savedStateHandle;
    }

    public MutableLiveData<TimerGroup> getTimerGroup(String groupId) {
        if (timerGroup == null) {
            timerGroup = TimerRepository.getInstance().getTimerGroup(groupId);
        }
        return timerGroup;
    }

    public MutableLiveData<String> getTimerGroupId() {
        if (timerGroupId == null) {
            timerGroupId = state.getLiveData("groupId");
        }
        return timerGroupId;
    }
}

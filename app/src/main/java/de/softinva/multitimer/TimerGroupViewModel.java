package de.softinva.multitimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.ActivityTabsViewModel;
import de.softinva.multitimer.model.TABS;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class TimerGroupViewModel extends ActivityTabsViewModel {
    SavedStateHandle state;
    private MutableLiveData<TABS> activeTab;
    private MutableLiveData<String> timerGroupId;
    private MutableLiveData<TimerGroup> timerGroup;


    public TimerGroupViewModel(SavedStateHandle savedStateHandle) {
        state = savedStateHandle;
    }

    @Override
    public MutableLiveData<TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

    public MutableLiveData<String> getTimerGroupId() {
        if (timerGroupId == null) {
            timerGroupId = state.getLiveData("groupId");
        }
        return timerGroupId;
    }
    public MutableLiveData<TimerGroup> getTimerGroup() {
        if (timerGroup == null) {
            timerGroup = TimerRepository.getInstance().getTimerGroup(timerGroupId.getValue());
        }
        return timerGroup;
    }
}

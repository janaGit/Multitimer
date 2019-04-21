package de.softinva.multitimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class TimerGroupInfoViewModel extends ViewModel {
   protected  MutableLiveData<TimerGroup> timerGroup;

    public MutableLiveData<TimerGroup> getTimerGroup(String groupId) {
        if (timerGroup == null) {
            timerGroup = TimerRepository.getInstance().getTimerGroup(groupId);
        }
        return timerGroup;
    }
}

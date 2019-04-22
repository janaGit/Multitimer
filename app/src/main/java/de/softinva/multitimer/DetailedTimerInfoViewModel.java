package de.softinva.multitimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.repository.TimerRepository;

public class DetailedTimerInfoViewModel extends ViewModel {
    protected MutableLiveData<RunningTimer> timer;

    public MutableLiveData<RunningTimer> getTimer(String groupId, String timerId) {
        if (timer == null) {
            timer = (MutableLiveData<RunningTimer>) Transformations.map(TimerRepository.getInstance().getTimerGroup(groupId), timerGroup-> timerGroup.timerMapByTimerId.get(timerId));
        }
        return timer;
    }
}

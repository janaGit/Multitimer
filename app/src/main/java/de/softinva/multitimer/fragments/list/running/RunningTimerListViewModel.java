package de.softinva.multitimer.fragments.list.running;

import android.app.Application;

import java.util.Map;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import de.softinva.multitimer.classes.FragmentViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;

public class RunningTimerListViewModel extends FragmentViewModel {

    private LiveData<TreeMap<Long, RunningTimer>> timerList;
    private LiveData<TreeMap<Long, RunningTimer>> timerListForGroup;

    public RunningTimerListViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<TreeMap<Long, RunningTimer>> getTimerList() {
        if (timerList == null) {
            timerList = new TimerRepository(getApplication()).getRunningTimerByFinishTimeMap();
        }
        return timerList;
    }

    public LiveData<TreeMap<Long, RunningTimer>> getTimerListForGroup(String groupId) {
        if (timerListForGroup == null) {
            loadTimerListForGroup(groupId);
        }
        return timerListForGroup;
    }

    private void loadTimerListForGroup(String groupId) {
        LiveData<TreeMap<Long, RunningTimer>> map = new TimerRepository(getApplication()).getRunningTimerByFinishTimeMap();
        timerListForGroup = Transformations.map(map, runningTimerMap ->
             getTimerListForGroup(groupId, runningTimerMap)
        );
    }
    private TreeMap<Long, RunningTimer> getTimerListForGroup(String timerGroupId, TreeMap<Long, RunningTimer> timerMap) {
        TreeMap<Long, RunningTimer> detailedTimerListForGroup = new TreeMap<>();
        for (Map.Entry<Long, RunningTimer> entry : timerMap.entrySet()) {
            RunningTimer runningTimer = entry.getValue();
            Timer timer = entry.getValue().getTimer();
            String groupId = timer.getGroupId();
            if (groupId.equals(timerGroupId)) {
                detailedTimerListForGroup.put(entry.getKey(), runningTimer);
            }
        }
        return detailedTimerListForGroup;
    }
}


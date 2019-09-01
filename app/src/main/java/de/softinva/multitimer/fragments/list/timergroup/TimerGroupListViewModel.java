package de.softinva.multitimer.fragments.list.timergroup;
import android.app.Application;

import java.util.TreeMap;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import de.softinva.multitimer.classes.abstract_classes.FragmentViewModel;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class TimerGroupListViewModel extends FragmentViewModel {
    private LiveData<TreeMap<Integer,TimerGroup>> timerList;

    public TimerGroupListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<TreeMap<Integer, TimerGroup>> getTimerGroupList() {
        if (timerList == null) {
            timerList = TimerRepository.getInstance(getApplication()).getTimerGroups();
        }
        return timerList;
    }

}


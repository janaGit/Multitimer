package de.softinva.multitimer.fragments.list.timer;

import android.app.Application;

import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.FragmentViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.UtilityMethods;


public class DetailedTimerListViewModel extends FragmentViewModel {

    private MutableLiveData<TreeMap<Integer, RunningTimer>> timerList;

    public DetailedTimerListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<TreeMap<Integer, RunningTimer>> getTimerList(String timerGroupId) {
        if (timerList == null) {
            createTimerList(timerGroupId);
        }
        return timerList;
    }

    private void createTimerList(String timerGroupId) {
        timerList = (MutableLiveData<TreeMap<Integer, RunningTimer>>)Transformations.switchMap(new TimerRepository(getApplication()).getTimerGroup(timerGroupId), timerGroup -> {
            TreeMap<Integer, RunningTimer> timerMap = timerGroup.getTimerMapByPosition();
           return UtilityMethods.updateTimerList(timerMap, getApplication());

        });
    }

}


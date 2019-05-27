package de.softinva.multitimer.fragments.list.timer;

import android.app.Application;

import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.FragmentViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.LiveDataZipRunningTimer;
import de.softinva.multitimer.utility.UtilityMethods;


public class DetailedTimerListViewModel extends FragmentViewModel {

    private LiveData<TreeMap<Integer, RunningTimer>> timerList;

    public DetailedTimerListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<TreeMap<Integer, RunningTimer>> getTimerList(String timerGroupId) {
        if (timerList == null) {
            createTimerList(timerGroupId);
        }
        return timerList;
    }

    private void createTimerList(String timerGroupId) {
        timerList = Transformations.switchMap(new TimerRepository(getApplication()).getDetailedTimersForTimerGroup(timerGroupId), detailedTimerMap -> {
            TreeMap<Integer, RunningTimer> treeMap = UtilityMethods.createRunningTimerListForDetailedTimer(detailedTimerMap);
            return new LiveDataZipRunningTimer(treeMap, getApplication());

        });
    }

}


package de.softinva.multitimer.fragments.list.temp;

import android.app.Application;

import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.FragmentViewModel;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.LiveDataZipRunningTimer;
import de.softinva.multitimer.utility.UtilityMethods;

public class TempTimerListViewModel extends FragmentViewModel {

    private LiveData<TreeMap<Integer, RunningTimer>> timerList;

    public TempTimerListViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<TreeMap<Integer, RunningTimer>> getTimerList() {
        if (timerList == null) {
            timerList = new MutableLiveData<>();
            createTimerList();
        }
        return timerList;
    }

    private void createTimerList() {
        timerList = Transformations.switchMap(
                new TimerRepository(getApplication()).getTempTimer(), timerMap -> {
                    TreeMap<Integer, RunningTimer> treeMap = UtilityMethods.createRunningTimerListForTempTimer(timerMap);
                    return new LiveDataZipRunningTimer(treeMap, getApplication());
                });

    }
}


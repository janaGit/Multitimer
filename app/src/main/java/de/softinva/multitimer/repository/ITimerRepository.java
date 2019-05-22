package de.softinva.multitimer.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.TreeMap;

import de.softinva.multitimer.database.DetailedTimerEntity;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.TimerGroup;

public interface ITimerRepository {

    LiveData<TreeMap<Integer, TimerGroup>> getTimerGroups();

    LiveData<TimerGroup> getTimerGroup(String groupId);


    LiveData<DetailedTimer> getDetailedTimer(String groupId, String timerId);

    LiveData<TreeMap<Integer, DetailedTimer>> getDetailedTimersForTimerGroup(String groupId);

    LiveData<TreeMap<Integer, DetailedTimer>> getAllDisabledTimersForTimerGroup(String groupId);

    LiveData<TreeMap<Integer, TempTimer>> getTempTimer();


    LiveData<TreeMap<Long, RunningTimer>> getRunningTimerByFinishTimeMap();

    LiveData<TreeMap<String, RunningTimer>> getRunningTimerByIDMap();


    void insertTimerGroup(TimerGroup timerGroup);

    void updateTimerGroup(TimerGroup timerGroup);

    void deleteTimerGroup(TimerGroup timerGroup);


    void insertDetailedTimer(DetailedTimer detailedTimer);

    void updateDetailedTimer(DetailedTimer detailedTimer);

    void deleteDetailedTimer(DetailedTimer detailedTimer);

    void enableDetailedTimer(String timerGroupId, String detailedTimerId);

    void disableDetailedTimer(String timerGroupId, String detailedTimerId);

    void enableAllDetailedTimer(String timerGroupId);
}

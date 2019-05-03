package de.softinva.multitimer.repository;


import android.app.Application;
import android.content.Context;

import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.CountDownService;
import de.softinva.multitimer.database.AppDatabase;
import de.softinva.multitimer.model.RunningTimer;

import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.dummy.DummyNudelGericht;
import de.softinva.multitimer.repository.dummy.DummyPizza;
import de.softinva.multitimer.repository.dummy.DummyRunningTimer;
import de.softinva.multitimer.repository.dummy.DummyTempTimer;


public class TimerRepository {
    protected MutableLiveData<TreeMap<Integer, TimerGroup>> timerGroupMap;
    protected MutableLiveData<TreeMap<Integer, RunningTimer>> tempTimerMap;
    protected MutableLiveData<TreeMap<Long, RunningTimer>> runningTimerByFinishTimeMap;
    protected MutableLiveData<TreeMap<String, RunningTimer>> runningTimerByIDMap;

    protected static TimerRepository instance;
    protected Application application;

    public TimerRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<TreeMap<Integer, TimerGroup>> getTimerGroups() {

        if (this.timerGroupMap != null) {
            return timerGroupMap;
        }
        //AppDatabase.getInstance(application).timerGroupDao().insert(DummyNudelGericht.TIMER_GROUP);
        //AppDatabase.getInstance(application).timerGroupDao().insert(DummyPizza.TIMER_GROUP);
        timerGroupMap = new MutableLiveData<>();
        TreeMap<Integer, TimerGroup> timerGroupList = new TreeMap<>();
        timerGroupList.put(0, DummyNudelGericht.TIMER_GROUP);
        timerGroupList.put(1, DummyPizza.TIMER_GROUP);
        timerGroupMap.setValue(timerGroupList);

        return timerGroupMap;
    }

    public MutableLiveData<TimerGroup> getTimerGroup(String groupId) {
        return (MutableLiveData<TimerGroup>) Transformations.map(getTimerGroups(), timerGroups -> {
            return getTimerGroup(groupId, timerGroups);
        });
    }

    public MutableLiveData<TreeMap<Integer, RunningTimer>> getTempTimer() {

        if (this.tempTimerMap != null) {
            return this.tempTimerMap;
        }

        this.tempTimerMap = new MutableLiveData<>();
        this.tempTimerMap.setValue(DummyTempTimer.TEMP_TIMERS);

        return this.tempTimerMap;
    }

    public MutableLiveData<TreeMap<Long, RunningTimer>> getDummyRunningTimer() {
        if (runningTimerByFinishTimeMap == null) {
            runningTimerByFinishTimeMap = new MutableLiveData<>();
            runningTimerByFinishTimeMap.setValue(DummyRunningTimer.RUNNING_TIMER);
        }

        return runningTimerByFinishTimeMap;
    }

    public MutableLiveData<TreeMap<Long, RunningTimer>> getRunningTimerByFinishTimeMap() {
        if (runningTimerByFinishTimeMap == null) {
            runningTimerByFinishTimeMap = CountDownService.runningTimerByFinishTimeMap;
        }
        return runningTimerByFinishTimeMap;
    }

    public MutableLiveData<TreeMap<String, RunningTimer>> getRunningTimerByIDMap() {
        if (runningTimerByIDMap == null) {
            runningTimerByIDMap = CountDownService.runningTimerByIDMap;
        }
        return runningTimerByIDMap;
    }

    protected TimerGroup getTimerGroup(String timerGroupId, TreeMap<Integer, TimerGroup> timerGroupMap) {
        for (Map.Entry<Integer, TimerGroup> entry : timerGroupMap.entrySet()) {
            String groupId = entry.getValue().getId();
            if (groupId.equals(timerGroupId)) {
                return entry.getValue();
            }
        }
        throw new Error("Timer Group with Id " + timerGroupId + " not found");
    }

}
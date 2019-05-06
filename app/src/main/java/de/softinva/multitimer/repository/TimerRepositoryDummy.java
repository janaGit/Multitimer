package de.softinva.multitimer.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Map;
import java.util.TreeMap;

import de.softinva.multitimer.CountDownService;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.dummy.DummyNudelGericht;
import de.softinva.multitimer.repository.dummy.DummyPizza;
import de.softinva.multitimer.repository.dummy.DummyRunningTimer;
import de.softinva.multitimer.repository.dummy.DummyTempTimer;

public class TimerRepositoryDummy implements ITimerRepository {
    protected MutableLiveData<TreeMap<Integer, TimerGroup>> timerGroupMap;
    protected MutableLiveData<TreeMap<Integer, TempTimer>> tempTimerMap;
    protected MutableLiveData<TreeMap<Long, RunningTimer>> runningTimerByFinishTimeMap;
    protected MutableLiveData<TreeMap<String, RunningTimer>> runningTimerByIDMap;
    protected MutableLiveData<TreeMap<String, DetailedTimer>> detailedTimerMap;

    public TimerRepositoryDummy(Application application) {

    }


    public MutableLiveData<TreeMap<Integer, TimerGroup>> getTimerGroups() {

        if (this.timerGroupMap != null) {
            return timerGroupMap;
        }
        timerGroupMap = new MutableLiveData<>();
        TreeMap<Integer, TimerGroup> timerGroupList = new TreeMap<>();
        timerGroupList.put(0, DummyNudelGericht.TIMER_GROUP);
        timerGroupList.put(1, DummyPizza.TIMER_GROUP);
        timerGroupMap.setValue(timerGroupList);

        return timerGroupMap;
    }

    public MutableLiveData<TimerGroup> getTimerGroup(String groupId) {
        return (MutableLiveData<TimerGroup>) Transformations.map(getTimerGroups(), timerGroups ->
                getTimerGroup(groupId, timerGroups));
    }

    @Override
    public MutableLiveData<DetailedTimer> getDetailedTimer(String groupId, String timerId) {
        return (MutableLiveData<DetailedTimer>) Transformations.map(getDetailedTimerMap(), detailedTimerMap -> detailedTimerMap.get(timerId));
    }

    protected LiveData<TreeMap<String, DetailedTimer>> getDetailedTimerMap() {
        if (detailedTimerMap != null) {
            return detailedTimerMap;
        }
        detailedTimerMap = new MutableLiveData<>();
        TreeMap<String, DetailedTimer> treeMap = new TreeMap<>();
        treeMap.put(DummyNudelGericht.TIMER_NUDELN.getId(), DummyNudelGericht.TIMER_NUDELN);
        treeMap.put(DummyNudelGericht.TIMER_Tomatensoße.getId(), DummyNudelGericht.TIMER_Tomatensoße);
        treeMap.put(DummyPizza.TIMER_PIZZA.getId(), DummyPizza.TIMER_PIZZA);
        detailedTimerMap.setValue(treeMap);
        return detailedTimerMap;

    }

    @Override
    public MutableLiveData<TreeMap<Integer, DetailedTimer>> getDetailedTimersForTimerGroup(String groupId) {
        return (MutableLiveData<TreeMap<Integer, DetailedTimer>>) Transformations.map(getDetailedTimerMap(), detailedTimerMap -> {
            TreeMap<Integer, DetailedTimer> treeMap = new TreeMap<>();
            for (Map.Entry<String, DetailedTimer> entry : detailedTimerMap.entrySet()) {
                if (entry.getValue().getGroupId().equals(groupId)) {
                    treeMap.put(entry.getValue().getPositionInGroup(), entry.getValue());
                }
            }
            return treeMap;
        });
    }

    public MutableLiveData<TreeMap<Integer, TempTimer>> getTempTimer() {

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

    @Override
    public void insertTimerGroup(TimerGroup timerGroup) {

    }

    @Override
    public void updateTimerGroup(TimerGroup timerGroup) {

    }

    @Override
    public void deleteTimerGroup(TimerGroup timerGroup) {

    }

    @Override
    public void insertDetailedTimer(DetailedTimer detailedTimer) {

    }

    @Override
    public void updateDetailedTimer(DetailedTimer detailedTimer) {

    }

    @Override
    public void deleteDetailedTimer(DetailedTimer detailedTimer) {

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

package de.softinva.multitimer.repository;


import android.app.Application;

import java.util.TreeMap;

import androidx.lifecycle.LiveData;

import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;

import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.TimerGroup;


public class TimerRepository implements ITimerRepository {
    protected ITimerRepository repository;

    public TimerRepository(Application application) {
        repository = new TimerRepositoryDatabase(application);
    }

    @Override
    public LiveData<TreeMap<Integer, TimerGroup>> getTimerGroups() {
        return repository.getTimerGroups();
    }

    @Override
    public LiveData<TimerGroup> getTimerGroup(String groupId) {
        return repository.getTimerGroup(groupId);
    }

    @Override
    public LiveData<DetailedTimer> getDetailedTimer(String groupId, String timerId) {
        return repository.getDetailedTimer(groupId, timerId);
    }

    @Override
    public LiveData<TreeMap<Integer, DetailedTimer>> getDetailedTimersForTimerGroup(String groupId) {
        return repository.getDetailedTimersForTimerGroup(groupId);
    }

    @Override
    public LiveData<TreeMap<Integer, DetailedTimer>> getAllDisabledTimersForTimerGroup(String groupId) {
        return repository.getAllDisabledTimersForTimerGroup(groupId);
    }

    @Override
    public LiveData<TreeMap<Integer, TempTimer>> getTempTimer() {
        return repository.getTempTimer();
    }

    @Override
    public LiveData<TreeMap<Long, RunningTimer>> getRunningTimerByFinishTimeMap() {
        return repository.getRunningTimerByFinishTimeMap();
    }

    @Override
    public LiveData<TreeMap<String, RunningTimer>> getRunningTimerByIDMap() {
        return repository.getRunningTimerByIDMap();
    }

    @Override
    public LiveData<TreeMap<String, RunningTimer>> getCoolDownTimerTimerByIDMap() {
        return repository.getCoolDownTimerTimerByIDMap();
    }

    @Override
    public void insertTimerGroup(TimerGroup timerGroup) {
        repository.insertTimerGroup(timerGroup);
    }

    @Override
    public void updateTimerGroup(TimerGroup timerGroup) {
        repository.updateTimerGroup(timerGroup);
    }

    @Override
    public void deleteTimerGroup(TimerGroup timerGroup) {
        repository.deleteTimerGroup(timerGroup);
    }

    @Override
    public void insertDetailedTimer(DetailedTimer detailedTimer) {
        repository.insertDetailedTimer(detailedTimer);
    }

    @Override
    public void updateDetailedTimer(DetailedTimer detailedTimer) {
        repository.updateDetailedTimer(detailedTimer);
    }

    @Override
    public void deleteDetailedTimer(DetailedTimer detailedTimer) {
        repository.deleteDetailedTimer(detailedTimer);
    }

    @Override
    public void enableDetailedTimer(String timerGroupId, String detailedTimerId) {
        repository.enableDetailedTimer(timerGroupId, detailedTimerId);
    }

    @Override
    public void disableDetailedTimer(String timerGroupId, String detailedTimerId) {
        repository.disableDetailedTimer(timerGroupId, detailedTimerId);
    }

    @Override
    public void enableAllDetailedTimer(String timerGroupId) {
        repository.enableAllDetailedTimer(timerGroupId);
    }

}
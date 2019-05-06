package de.softinva.multitimer.repository;


import android.app.Application;
import android.os.AsyncTask;

import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.CountDownService;
import de.softinva.multitimer.database.AppDao;
import de.softinva.multitimer.database.AppDatabase;
import de.softinva.multitimer.database.AppEntity;
import de.softinva.multitimer.database.DetailedTimerDao;
import de.softinva.multitimer.database.DetailedTimerEntity;
import de.softinva.multitimer.database.TimerGroupDao;
import de.softinva.multitimer.database.TimerGroupEntity;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;

import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.dummy.DummyNudelGericht;
import de.softinva.multitimer.repository.dummy.DummyPizza;
import de.softinva.multitimer.repository.dummy.DummyRunningTimer;
import de.softinva.multitimer.repository.dummy.DummyTempTimer;


public class TimerRepository implements ITimerRepository {
    protected ITimerRepository repository;

    public TimerRepository(Application application) {
        repository = new TimerRepositoryDatabase(application);
    }


    public LiveData<TreeMap<Integer, TimerGroup>> getTimerGroups() {
        return repository.getTimerGroups();
    }

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

}
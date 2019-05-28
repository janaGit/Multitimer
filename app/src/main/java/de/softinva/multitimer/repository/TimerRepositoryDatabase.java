package de.softinva.multitimer.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import de.softinva.multitimer.services.cooldown.CoolDownService;
import de.softinva.multitimer.services.countdown.CountDownService;
import de.softinva.multitimer.database.AppDao;
import de.softinva.multitimer.database.AppDatabase;
import de.softinva.multitimer.database.AppEntity;
import de.softinva.multitimer.database.DetailedTimerDao;
import de.softinva.multitimer.database.DetailedTimerEntity;
import de.softinva.multitimer.database.DummyAppDatabase;
import de.softinva.multitimer.database.TimerGroupDao;
import de.softinva.multitimer.database.TimerGroupEntity;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.dummy.DummyTempTimer;

public class TimerRepositoryDatabase implements ITimerRepository {
    private LiveData<TreeMap<Long, RunningTimer>> runningTimerByFinishTimeMap;
    private LiveData<TreeMap<String, RunningTimer>> runningTimerByIDMap;
    private LiveData<TreeMap<String, RunningTimer>> coolDownTimerByIDMap;
    private LiveData<TreeMap<Integer, TimerGroup>> timerGroups;
    private LiveData<TreeMap<Integer, TempTimer>> tempTimerMap;

    private TreeMap<String, LiveData<TreeMap<Integer, DetailedTimer>>> detailedTimersForTimerGroups;
    private TreeMap<String, LiveData<TreeMap<Integer, DetailedTimer>>> disabledDetailedTimersForTimerGroups;
    private TreeMap<String, LiveData<DetailedTimer>> detailedTimerMap;
    private TreeMap<String, LiveData<TimerGroup>> timerGroupMap;
    private TimerGroupDao timerGroupDao;
    private DetailedTimerDao detailedTimerDao;

    TimerRepositoryDatabase(Application application) {
        AppDatabase db = DummyAppDatabase.getDatabase(application);
        timerGroupDao = db.timerGroupDao();
        detailedTimerDao = db.detailedTimerDao();
        detailedTimersForTimerGroups = new TreeMap<>();
        disabledDetailedTimersForTimerGroups = new TreeMap<>();
        detailedTimerMap = new TreeMap<>();
        timerGroupMap = new TreeMap<>();
    }


    public LiveData<TreeMap<Integer, TimerGroup>> getTimerGroups() {
        if (timerGroups == null) {
            timerGroups = Transformations.map(timerGroupDao.getAll(), timerGroupEntity -> {
                TreeMap<Integer, TimerGroup> treeMap = new TreeMap<>();
                for (TimerGroupEntity timerGroupEntity1 : timerGroupEntity) {
                    TimerGroup timerGroup = new TimerGroup(timerGroupEntity1);
                    treeMap.put(treeMap.size(), timerGroup);
                }
                return treeMap;
            });
        }

        return timerGroups;
    }

    public LiveData<TimerGroup> getTimerGroup(String groupId) {
        if (timerGroupMap.get(groupId) == null) {
            LiveData<TimerGroup> timerGroup = Transformations.map(timerGroupDao.getTimerGroup(groupId), timerGroupEntity -> {
                if (timerGroupEntity != null) {
                    return new TimerGroup(timerGroupEntity);
                } else {
                    return new TimerGroup();
                }
            });
            timerGroupMap.put(groupId, timerGroup);
        }
        return timerGroupMap.get(groupId);
    }

    @Override
    public LiveData<DetailedTimer> getDetailedTimer(String groupId, String timerId) {

        if (detailedTimerMap.get(groupId + timerId) == null) {
            LiveData<DetailedTimer> map = Transformations.map(detailedTimerDao.getDetailedTimer(groupId, timerId), detailedTimerEntity -> {
                if (detailedTimerEntity != null) {
                    return new DetailedTimer(detailedTimerEntity);
                }
                return new DetailedTimer();
            });
            detailedTimerMap.put(groupId + timerId, map);

        }
        return detailedTimerMap.get(groupId + timerId);

    }

    @Override
    public LiveData<TreeMap<Integer, DetailedTimer>> getDetailedTimersForTimerGroup(String groupId) {
        if (detailedTimersForTimerGroups.get(groupId) == null) {
            LiveData<TreeMap<Integer, DetailedTimer>> map = Transformations.map(detailedTimerDao.getAllDetailedTimersForGroup(groupId), detailedTimerEntity -> {
                TreeMap<Integer, DetailedTimer> treeMap = new TreeMap<>();
                Iterator<DetailedTimerEntity> iterator = detailedTimerEntity.iterator();
                while (iterator.hasNext()) {
                    DetailedTimerEntity entity = iterator.next();
                    DetailedTimer detailedTimer = new DetailedTimer(entity);
                    treeMap.put(detailedTimer.getPositionInGroup(), detailedTimer);
                }
                return treeMap;
            });
            detailedTimersForTimerGroups.put(groupId, map);
        }

        return detailedTimersForTimerGroups.get(groupId);
    }

    @Override
    public LiveData<TreeMap<Integer, DetailedTimer>> getAllDisabledTimersForTimerGroup(String groupId) {
        if (disabledDetailedTimersForTimerGroups.get(groupId) == null) {
            LiveData<TreeMap<Integer, DetailedTimer>> map = Transformations.map(detailedTimerDao.getAllDisabledTimerForTimerGroup(groupId), detailedTimerEntity -> {
                TreeMap<Integer, DetailedTimer> treeMap = new TreeMap<>();
                Iterator<DetailedTimerEntity> iterator = detailedTimerEntity.iterator();
                while (iterator.hasNext()) {
                    DetailedTimerEntity entity = iterator.next();
                    DetailedTimer detailedTimer = new DetailedTimer(entity);
                    treeMap.put(detailedTimer.getPositionInGroup(), detailedTimer);
                }
                return treeMap;
            });
            disabledDetailedTimersForTimerGroups.put(groupId, map);
        }

        return disabledDetailedTimersForTimerGroups.get(groupId);
    }

    public LiveData<TreeMap<Integer, TempTimer>> getDummyTempTimer() {

        if (tempTimerMap == null) {
            tempTimerMap = new MutableLiveData<>(DummyTempTimer.TEMP_TIMERS);
        }

        return tempTimerMap;
    }

    public LiveData<TreeMap<Integer, TempTimer>> getTempTimer() {
        if (tempTimerMap == null) {
            tempTimerMap = Transformations.map(getRunningTimerByFinishTimeMap(), runningTimerTreeMap -> {
                TreeMap<Integer, TempTimer> tempTimerTreeMap = new TreeMap<>();
                for (Map.Entry<Long, RunningTimer> runningTimerEntry : runningTimerTreeMap.entrySet()) {
                    RunningTimer runningTimer = runningTimerEntry.getValue();
                    Timer timer = runningTimer.getTimer();
                    if (timer instanceof TempTimer) {
                        TempTimer tempTimer = new TempTimer(timer);
                        tempTimerTreeMap.put(tempTimerTreeMap.size(), tempTimer);
                    }
                }
                return tempTimerTreeMap;
            });
        }
        return tempTimerMap;
    }

    public LiveData<TreeMap<Long, RunningTimer>> getRunningTimerByFinishTimeMap() {
        if (runningTimerByFinishTimeMap == null) {
            runningTimerByFinishTimeMap = CountDownService.runningTimerByFinishTimeMap;
        }
        return runningTimerByFinishTimeMap;
    }

    public LiveData<TreeMap<String, RunningTimer>> getRunningTimerByIDMap() {
        if (runningTimerByIDMap == null) {
            runningTimerByIDMap = CountDownService.runningTimerByIDMap;
        }
        return runningTimerByIDMap;
    }

    @Override
    public LiveData<TreeMap<String, RunningTimer>> getCoolDownTimerTimerByIDMap() {
        if (coolDownTimerByIDMap == null) {
            coolDownTimerByIDMap = CoolDownService.runningTimerByIDMap;
        }
        return coolDownTimerByIDMap;
    }


    @Override
    public void insertTimerGroup(TimerGroup timerGroup) {
        new TimerRepositoryDatabase.insert(timerGroupDao).execute(timerGroup.toEntity());
    }

    @Override
    public void updateTimerGroup(TimerGroup timerGroup) {
        new TimerRepositoryDatabase.update(timerGroupDao).execute(timerGroup.toEntity());
    }

    @Override
    public void deleteTimerGroup(TimerGroup timerGroup) {
        new TimerRepositoryDatabase.deleteTimerGroup(timerGroupDao).execute(timerGroup.toEntity());
    }

    @Override
    public void insertDetailedTimer(DetailedTimer detailedTimer) {
        new TimerRepositoryDatabase.insert(detailedTimerDao).execute(detailedTimer.toEntity());
    }

    @Override
    public void updateDetailedTimer(DetailedTimer detailedTimer) {
        new TimerRepositoryDatabase.update(detailedTimerDao).execute(detailedTimer.toEntity());
    }

    @Override
    public void deleteDetailedTimer(DetailedTimer detailedTimer) {
        new TimerRepositoryDatabase.deleteDetailedTimer(detailedTimerDao).execute(detailedTimer.toEntity());
    }

    @Override
    public void enableDetailedTimer(String timerGroupId, String detailedTimerId) {
        new TimerRepositoryDatabase.updateTimerStatus(detailedTimerDao, timerGroupId, detailedTimerId).execute(true);
    }

    @Override
    public void disableDetailedTimer(String timerGroupId, String detailedTimerId) {
        new TimerRepositoryDatabase.updateTimerStatus(detailedTimerDao, timerGroupId, detailedTimerId).execute(false);
    }

    @Override
    public void enableAllDetailedTimer(String timerGroupId) {
        new TimerRepositoryDatabase.enableAllDetailedTimerForTimerGroup(detailedTimerDao).execute(timerGroupId);
    }

    protected TimerGroup getTimerGroup(String
                                               timerGroupId, TreeMap<Integer, TimerGroup> timerGroupMap) {
        for (Map.Entry<Integer, TimerGroup> entry : timerGroupMap.entrySet()) {
            String groupId = entry.getValue().getId();
            if (groupId.equals(timerGroupId)) {
                return entry.getValue();
            }
        }
        throw new Error("Timer Group with Id " + timerGroupId + " not found");
    }

    private static class insert extends AsyncTask<AppEntity, Void, Void> {
        private AppDao dao;

        insert(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final AppEntity... params) {
            dao.insert(params[0]);
            return null;
        }
    }

    private static class update extends AsyncTask<AppEntity, Void, Void> {
        private AppDao dao;

        update(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final AppEntity... params) {
            dao.update(params[0]);
            return null;
        }
    }

    private static class deleteDetailedTimer extends AsyncTask<DetailedTimerEntity, Void, Void> {
        private DetailedTimerDao dao;

        deleteDetailedTimer(DetailedTimerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final DetailedTimerEntity... params) {
            dao.delete(params[0]);
            dao.removePosition(params[0].groupId, params[0].positionInGroup);
            return null;
        }
    }

    private static class deleteTimerGroup extends AsyncTask<AppEntity, Void, Void> {
        private AppDao dao;

        deleteTimerGroup(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final AppEntity... params) {
            dao.delete(params[0]);
            return null;
        }
    }

    private static class updateTimerStatus extends AsyncTask<Boolean, Void, Void> {
        private DetailedTimerDao dao;
        String groupId;
        String timerId;

        updateTimerStatus(DetailedTimerDao dao, String groupId, String timerId) {
            this.dao = dao;
            this.groupId = groupId;
            this.timerId = timerId;
        }

        @Override
        protected Void doInBackground(final Boolean... params) {
            if (params[0]) {
                dao.enableTimer(groupId, timerId);
            } else {
                dao.disableTimer(groupId, timerId);
            }

            return null;
        }
    }

    private static class enableAllDetailedTimerForTimerGroup extends AsyncTask<String, Void, Void> {
        private DetailedTimerDao dao;

        enableAllDetailedTimerForTimerGroup(DetailedTimerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            dao.enableAllTimerForGroup(params[0]);

            return null;
        }
    }
}

package de.softinva.multitimer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimerGroupDao extends AppDao<TimerGroupEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimerGroupEntity timerGroup);

    @Update
    void update(TimerGroupEntity timerGroup);

    @Delete
    void delete(TimerGroupEntity timerGroup);

    @Query("SELECT * FROM timergroup")
    LiveData<List<TimerGroupEntity>> getAll();

    @Query("SELECT * from timergroup where id = :groupId LIMIT 1")
    LiveData<TimerGroupEntity> getTimerGroup(String groupId);

    @Query("DELETE FROM timergroup")
    void deleteAll();
}
package de.softinva.multitimer.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimerGroupDao {
    @Query("SELECT * FROM timergroup")
    List<TimerGroupEntity> getAll();

    @Insert
    void insert(TimerGroupEntity timerGroup);

    @Update
    void update(TimerGroupEntity timerGroup);

    @Delete
    void delete(TimerGroupEntity timerGroup);

}

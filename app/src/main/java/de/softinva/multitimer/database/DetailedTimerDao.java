package de.softinva.multitimer.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface DetailedTimerDao {
    @Insert
    void insert(DetailedTimerEntity detailedTimer);

    @Update
    void update(DetailedTimerEntity detailedTimer);

    @Delete
    void delete(DetailedTimerEntity detailedTimer);

}

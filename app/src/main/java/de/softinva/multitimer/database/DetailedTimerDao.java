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
public interface DetailedTimerDao extends AppDao<DetailedTimerEntity> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DetailedTimerEntity detailedTimer);

    @Update
    void update(DetailedTimerEntity detailedTimer);

    @Delete
    void delete(DetailedTimerEntity detailedTimer);

    @Query("SELECT * FROM detailedtimer")
    LiveData<List<DetailedTimerEntity>> getAll();

    @Query("SELECT * from detailedtimer where groupId = :groupId  AND id = :timerId LIMIT 1")
    LiveData<DetailedTimerEntity> getDetailedTimer(String groupId, String timerId);

    @Query("DELETE FROM detailedtimer")
    void deleteAll();

    @Query("UPDATE detailedtimer SET positionInGroup = positionInGroup -1 where groupId = :id  AND positionInGroup > :removedPosition")
    void removePosition(String id, Integer removedPosition);
}

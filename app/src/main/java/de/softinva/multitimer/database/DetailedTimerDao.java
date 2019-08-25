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

    @Query("SELECT * FROM detailedtimer where groupId = :groupId")
    LiveData<List<DetailedTimerEntity>> getAllDetailedTimersForGroup(String groupId);

    @Query("SELECT * from detailedtimer where groupId = :groupId  AND id = :timerId LIMIT 1")
    LiveData<DetailedTimerEntity> getDetailedTimer(String groupId, String timerId);

    @Query("SELECT * FROM detailedtimer where isEnabled = 0  AND groupId = :groupId")
    LiveData<List<DetailedTimerEntity>> getAllDisabledTimerForTimerGroup(String groupId);

    @Query("DELETE FROM detailedtimer")
    void deleteAll();

    @Query("DELETE FROM detailedtimer where groupId = :timerGroupId")
    void deleteAllFromTimerGroup(String timerGroupId);

    @Query("UPDATE detailedtimer SET positionInGroup = positionInGroup -1 where groupId = :id  AND positionInGroup > :removedPosition")
    void removePosition(String id, Integer removedPosition);

    @Query("UPDATE detailedtimer SET isEnabled = 0 where groupId = :groupId  AND id = :timerId")
    void disableTimer(String groupId, String timerId);

    @Query("UPDATE detailedtimer SET isEnabled = 1 where groupId = :groupId  AND id = :timerId")
    void enableTimer(String groupId, String timerId);

    @Query("UPDATE detailedtimer SET isEnabled = 1 where groupId = :groupId")
    void enableAllTimerForGroup(String groupId);

}

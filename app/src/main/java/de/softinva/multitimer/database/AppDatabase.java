package de.softinva.multitimer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DetailedTimerEntity.class, TimerGroupEntity.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TimerGroupDao timerGroupDao();
    public abstract DetailedTimerDao detailedTimerDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "multi_timer_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

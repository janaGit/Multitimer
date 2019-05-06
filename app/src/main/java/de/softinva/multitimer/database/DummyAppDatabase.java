package de.softinva.multitimer.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import de.softinva.multitimer.repository.dummy.DummyNudelGericht;
import de.softinva.multitimer.repository.dummy.DummyPizza;

@Database(entities = {DetailedTimerEntity.class, TimerGroupEntity.class}, version = 1)
public abstract class DummyAppDatabase extends RoomDatabase {
    public abstract TimerGroupDao timerGroupDao();

    public abstract DetailedTimerDao detailedTimerDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dummy_multi_timer_database")
                            .addCallback(databaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback databaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new InsertDummyData(INSTANCE).execute();
                }
            };

    private static class InsertDummyData extends AsyncTask<Void, Void, Void> {

        private final DetailedTimerDao detailedTimerDao;
        private final TimerGroupDao timerGroupDao;

        InsertDummyData(AppDatabase db) {
            detailedTimerDao = db.detailedTimerDao();
            timerGroupDao = db.timerGroupDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            insertNudelGericht();
            insertPizza();
            return null;
        }

        void insertNudelGericht() {
            detailedTimerDao.insert(DummyNudelGericht.TIMER_NUDELN.toEntity());
            detailedTimerDao.insert(DummyNudelGericht.TIMER_Tomatensoße.toEntity());
            timerGroupDao.insert(DummyNudelGericht.TIMER_GROUP.toEntity());
        }

        void insertPizza() {
            detailedTimerDao.insert(DummyPizza.TIMER_PIZZA.toEntity());
            timerGroupDao.insert(DummyPizza.TIMER_GROUP.toEntity());
        }
    }
}

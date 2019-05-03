package de.softinva.multitimer.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timergroup")
public class TimerGroupEntity {
    @PrimaryKey
    @NonNull
    protected String id;
    protected String title;
    protected boolean isZipped;
    protected String imageName;
    protected String description;
}

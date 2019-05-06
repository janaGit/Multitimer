package de.softinva.multitimer.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timergroup")
public class TimerGroupEntity implements AppEntity {
    @PrimaryKey
    @NonNull
    public String id;
    public String title;
    public boolean isZipped;
    public String imageName;
    public String description;
}

package de.softinva.multitimer.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "detailedtimer")
public class DetailedTimerEntity implements AppEntity {
    @PrimaryKey
    @NonNull
    public String id;
    public String groupId;
    public Integer positionInGroup;
    public String title;
    public Integer durationInSec;
    public String imageName;
    public String description;
}

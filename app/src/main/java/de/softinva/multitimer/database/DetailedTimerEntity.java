package de.softinva.multitimer.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DetailedTimerEntity {
    @PrimaryKey
    @NonNull
    protected String id;
    protected String groupId;
    protected String title;
    protected Integer durationInSec;
    protected String imageName;
}

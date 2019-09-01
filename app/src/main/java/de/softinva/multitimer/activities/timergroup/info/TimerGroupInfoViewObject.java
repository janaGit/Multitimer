package de.softinva.multitimer.activities.timergroup.info;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupInfoViewObject extends AppViewObject<LiveData<TimerGroup>> {
    public TimerGroupInfoViewObject(LiveData<TimerGroup> obj, AppCompatActivity activity) {
        super(obj, activity);
    }
}

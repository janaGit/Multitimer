package de.softinva.multitimer.activities.main.timergroup;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupViewObject extends AppViewObject<LiveData<TimerGroup>> {
    public TimerGroupViewObject(LiveData<TimerGroup> obj, AppCompatActivity activity) {
        super(obj,  activity);
    }
}

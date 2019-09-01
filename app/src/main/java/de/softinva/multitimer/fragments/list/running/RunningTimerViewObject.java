package de.softinva.multitimer.fragments.list.running;


import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.RunningTimer;

public class RunningTimerViewObject extends AppViewObject<RunningTimer> {

    public RunningTimerViewObject(RunningTimer runningTimer, AppCompatActivity activity) {
        super(runningTimer, activity);
    }
    
}

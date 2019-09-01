package de.softinva.multitimer.fragments.list.temp;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.RunningTimer;

public class TempTimerViewObject extends AppViewObject<RunningTimer> {

    public TempTimerViewObject(RunningTimer runningTimer, AppCompatActivity activity) {
        super(runningTimer, activity);
    }

}

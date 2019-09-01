package de.softinva.multitimer.fragments.dialogaddtemptimer;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.TempTimer;


public class AddTempTimerViewObject extends AppViewObject<TempTimer> {
    public AddTempTimerViewObject(TempTimer obj, Activity activity) {
        super(obj, activity);
    }
}

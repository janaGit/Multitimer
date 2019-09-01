package de.softinva.multitimer.activities;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

public class MainActivityViewObject extends AppViewObject<MainActivity> {
    public MainActivityViewObject(MainActivity obj) {
        super(obj, obj);
    }

    public void onAddNewTempTimer(View view) {
        obj.showAddTempTimerDialog();
    }
}

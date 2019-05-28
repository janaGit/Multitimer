package de.softinva.multitimer.activities;

import android.view.View;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

public class MainActivityViewObject extends AppViewObject<MainActivity> {
    public MainActivityViewObject(MainActivity obj) {
        super(obj);
    }

    public void onAddNewTempTimer(View view) {
        obj.showAddTempTimerDialog();
    }
}

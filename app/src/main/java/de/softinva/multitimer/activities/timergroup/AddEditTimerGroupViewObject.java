package de.softinva.multitimer.activities.timergroup;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class AddEditTimerGroupViewObject extends AppViewObject<TimerGroup> {
    public AddEditTimerGroupViewObject(TimerGroup obj) {
        super(obj);
    }

    public void onClickSaveButton(View view){

    }

    public void onClickAbortButton(View view){
        ((AppCompatActivity)getContext()).onBackPressed();
    }
}

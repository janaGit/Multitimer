package de.softinva.multitimer.activities.detailedtimer;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.DetailedTimer;

public class AddEditDetailedTimerViewObject extends AppViewObject<DetailedTimer> {
    public AddEditDetailedTimerViewObject(DetailedTimer obj) {
        super(obj);
    }

    public void onClickSaveButton(View view){

    }

    public void onClickAbortButton(View view){
        ((AppCompatActivity)getContext()).onBackPressed();
    }
}


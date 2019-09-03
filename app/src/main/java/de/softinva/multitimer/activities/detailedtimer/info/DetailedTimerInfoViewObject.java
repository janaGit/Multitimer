package de.softinva.multitimer.activities.detailedtimer.info;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.UtilityMethods;

public class DetailedTimerInfoViewObject extends AppViewObject<LiveData<RunningTimer>> {
    public DetailedTimerInfoViewObject(LiveData<RunningTimer> obj, AppCompatActivity activity) {
        super(obj, activity);
    }

    public LiveData<String> generateTimeView() {
        return Transformations.map(obj, UtilityMethods::createDurationAndCollDownString);

    }
}

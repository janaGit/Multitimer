package de.softinva.multitimer.fragments.list.timer;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.activities.detailedtimer.info.DetailedTimerInfoActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.UtilityMethods;

public class DetailedTimerViewObject extends AppViewObject<RunningTimer> {

    public DetailedTimerViewObject(RunningTimer runningTimer, AppCompatActivity activity) {
        super(runningTimer, activity);
    }

    public void onClickTimer(View view) {
        DetailedTimerInfoActivity.startNewActivity(((DetailedTimer) obj.getTimer()).getGroupId(), obj.getTimer().getId(), view.getContext());
    }

    public String getTimeInfoString() {
        return UtilityMethods.createDurationAndCollDownString(obj);

    }
}

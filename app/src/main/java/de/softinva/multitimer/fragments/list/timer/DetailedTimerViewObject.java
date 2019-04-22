package de.softinva.multitimer.fragments.list.timer;

import android.view.View;

import de.softinva.multitimer.DetailedTimerInfoActivity;
import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.RunningTimer;

public class DetailedTimerViewObject extends AppViewObject<RunningTimer> {

    public DetailedTimerViewObject(RunningTimer runningTimer) {
        super(runningTimer);
    }

    public void onClickTimer(View view) {
        DetailedTimerInfoActivity.startNewActivity(obj.getTimer().groupId, obj.getTimer().id, view.getContext());
    }

}

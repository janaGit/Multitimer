package de.softinva.multitimer.fragments.list.timer;

import android.view.View;

import de.softinva.multitimer.activities.detailedtimer.info.DetailedTimerInfoActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;

public class DetailedTimerViewObject extends AppViewObject<RunningTimer> {

    public DetailedTimerViewObject(RunningTimer runningTimer) {
        super(runningTimer);
    }

    public void onClickTimer(View view) {
        DetailedTimerInfoActivity.startNewActivity(((DetailedTimer) obj.getTimer()).getGroupId(), obj.getTimer().getId(), view.getContext());
    }

}

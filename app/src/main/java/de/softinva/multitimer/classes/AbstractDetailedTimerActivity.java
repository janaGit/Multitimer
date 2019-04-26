package de.softinva.multitimer.classes;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;


import de.softinva.multitimer.model.RunningTimer;

public abstract class AbstractDetailedTimerActivity<T> extends AppActivity<T> {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";
    public static final String TIMER_ID = "de.softinva.multitimer.timerId";
    protected MutableLiveData<RunningTimer> runningTimer$;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setClassSpecificObjects() {
        setGroupId();
        setTimerId();
        setRunningTimer();
    }

    void setRunningTimer() {
        AbstractDetailedTimerViewModel detailedTimerModel = (AbstractDetailedTimerViewModel) model;
        runningTimer$ = detailedTimerModel.getTimer(detailedTimerModel.getTimerGroupId().getValue(), detailedTimerModel.getTimerId().getValue());

    }

    protected void setTitle() {
        runningTimer$.observe(this, rtimer -> {
            setTitle(rtimer.getTimer().title);
        });
    }

    protected void setGroupId() {
        AbstractDetailedTimerViewModel detailedTimerModel = (AbstractDetailedTimerViewModel) model;
        String groupId = detailedTimerModel.getTimerGroupId().getValue();

        if (groupId == null) {

            groupId = getIntent().getStringExtra(GROUP_ID);

            if (groupId != null) {
                detailedTimerModel.getTimerGroupId().setValue(groupId);
            } else {
                throw new Error("groupId is null !");
            }
        }
    }

    protected void setTimerId() {
        AbstractDetailedTimerViewModel detailedTimerModel = (AbstractDetailedTimerViewModel) model;
        String timerId = detailedTimerModel.getTimerId().getValue();

        if (timerId == null) {

            timerId = getIntent().getStringExtra(TIMER_ID);

            if (timerId != null) {
                detailedTimerModel.getTimerId().setValue(timerId);
            } else {
                throw new Error("timerId is null !");
            }
        }
    }
}

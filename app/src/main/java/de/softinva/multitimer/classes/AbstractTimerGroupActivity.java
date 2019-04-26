package de.softinva.multitimer.classes;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.model.TimerGroup;

public abstract class AbstractTimerGroupActivity<T> extends AppActivity<T> {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";
    protected MutableLiveData<TimerGroup> timerGroup$;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setClassSpecificObjects() {
        setGroupId();
        setTimerGroup();
    }

    protected void setTimerGroup() {
        AbstractTimerGroupViewModel tGroupModel = (AbstractTimerGroupViewModel) model;
        timerGroup$ = tGroupModel.getTimerGroup(tGroupModel.getTimerGroupId$().getValue());
    }

    protected void setTitle() {
        timerGroup$.observe(this, tGroup -> {
            setTitle(tGroup.title);
        });
    }

    protected void setGroupId() {
        AbstractTimerGroupViewModel tGroupModel = (AbstractTimerGroupViewModel) model;
        String groupId = tGroupModel.getTimerGroupId$().getValue();

        if (groupId == null) {

            groupId = getIntent().getStringExtra(GROUP_ID);

            if (groupId != null) {
                tGroupModel.getTimerGroupId$().setValue(groupId);
            } else {
                throw new Error("groupId is null !");
            }
        }
    }
}

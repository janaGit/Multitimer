package de.softinva.multitimer.fragments.list.running;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.main.timergroup.TimerGroupActivity;
import de.softinva.multitimer.activities.main.timergroup.TimerGroupViewModel;
import de.softinva.multitimer.fragments.list.AppList;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.AppRecyclerAdapter;

/**
 * A fragment representing a list of Items..
 */
public class RunningTimerList extends AppList {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            RunningTimerListViewModel model = ViewModelProviders.of(this).get(RunningTimerListViewModel.class);
            if (getActivity() instanceof TimerGroupActivity) {
                TimerGroupViewModel activityModel = ViewModelProviders.of(getActivity()).get(TimerGroupViewModel.class);
                activityModel.getTimerGroupId$().observe(this, (groupId) -> {
                    model.getTimerListForGroup(groupId).observe(this, (timerList) -> {
                        recyclerView.setAdapter(new AppRecyclerAdapter(createViewObject(timerList), R.layout.running_timer_list_item));
                    });
                });
            } else {
                model.getTimerList().observe(this, (timerList) -> {
                    recyclerView.setAdapter(new AppRecyclerAdapter(createViewObject(timerList), R.layout.running_timer_list_item));
                });
            }


        } else {
            logger.error("view not instance of RecyclerView!");
        }

        return view;
    }

    public TreeMap<Long, RunningTimerViewObject> createViewObject(TreeMap<Long, RunningTimer> tempTimerTreeMap) {
        TreeMap<Long, RunningTimerViewObject> runningTimerViewObjectMap = new TreeMap<>();
        for (Map.Entry<Long, RunningTimer>entry : tempTimerTreeMap.entrySet()) {
            RunningTimer runningTimer =  entry.getValue();
            RunningTimerViewObject tempTimerViewObject = new RunningTimerViewObject(runningTimer);
            runningTimerViewObjectMap.put(entry.getKey(), tempTimerViewObject);
        }
        return runningTimerViewObjectMap;
    }
}

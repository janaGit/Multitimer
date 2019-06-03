package de.softinva.multitimer.fragments.list.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.main.timergroup.TimerGroupViewModel;
import de.softinva.multitimer.fragments.list.AppList;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.AppRecyclerAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class DetailedTimerList extends AppList {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            DetailedTimerListViewModel model = ViewModelProviders.of(this).get(DetailedTimerListViewModel.class);
            TimerGroupViewModel activityModel = ViewModelProviders.of(getActivity()).get(TimerGroupViewModel.class);
            activityModel.getTimerGroupId$().observe(this, (groupId) -> {
                model.getTimerList(groupId).observe(this, (timerList) -> {
                    recyclerView.setAdapter(new AppRecyclerAdapter(createViewObject(timerList), R.layout.detailed_timer_list_item));
                });
            });

        } else {
            logger.error("view not instance of RecyclerView!");
        }

        return view;
    }

    public TreeMap<Integer, DetailedTimerViewObject> createViewObject(TreeMap<Integer, RunningTimer> detailedTimerTreeMap) {
        TreeMap<Integer, DetailedTimerViewObject> timerGroupMap = new TreeMap<>();
        for (Map.Entry<Integer, RunningTimer> entry : detailedTimerTreeMap.entrySet()) {
            RunningTimer runningTimer = entry.getValue();
            DetailedTimerViewObject detailedTimerViewObject = new DetailedTimerViewObject(runningTimer);
            timerGroupMap.put(entry.getKey(), detailedTimerViewObject);
        }
        return timerGroupMap;
    }
}

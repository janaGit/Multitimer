package de.softinva.multitimer.fragments.list.timergroup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import de.softinva.multitimer.fragments.list.AppList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Map;
import java.util.TreeMap;

import de.softinva.multitimer.R;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.utility.AppRecyclerAdapter;

/**
 * A fragment representing a list of Items.
 */
public class TimerGroupList extends AppList {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            TimerGroupListViewModel model = ViewModelProviders.of(this).get(TimerGroupListViewModel.class);
            model.getTimerGroupList().observe(this, (timerGroups) -> {
                recyclerView.setAdapter(new AppRecyclerAdapter(createViewObject(timerGroups),  R.layout.timer_group_list_item));
            });
        } else {
            logger.error("view not instance of RecyclerView!");
        }

        return view;
    }

    public TreeMap<Integer, TimerGroupListViewObject> createViewObject(TreeMap<Integer, TimerGroup> timerGroups) {
        TreeMap<Integer, TimerGroupListViewObject> timerGroupMap = new TreeMap<>();
        for (Map.Entry<Integer, TimerGroup> entry : timerGroups.entrySet()) {
            TimerGroup timerGroup = entry.getValue();
            TimerGroupListViewObject timerGroupViewObject = new TimerGroupListViewObject(timerGroup, (AppCompatActivity) getActivity());
            timerGroupMap.put(entry.getKey(), timerGroupViewObject);
        }
        return timerGroupMap;
    }

}

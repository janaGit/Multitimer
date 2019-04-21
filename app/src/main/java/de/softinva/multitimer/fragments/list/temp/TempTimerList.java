package de.softinva.multitimer.fragments.list.temp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import de.softinva.multitimer.R;
import de.softinva.multitimer.fragments.list.AppList;
import de.softinva.multitimer.fragments.list.timer.DetailedTimerViewObject;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.utility.AppRecyclerAdapter;

/**
 * A fragment representing a list of Items.
 */
public class TempTimerList extends AppList {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            TempTimerListViewModel model = ViewModelProviders.of(this).get(TempTimerListViewModel.class);
            model.getTimerList().observe(this, (timerList) -> {
                recyclerView.setAdapter(new AppRecyclerAdapter(createViewObject(timerList), R.layout.temp_timer_list_item));
            });
        } else {
            logger.error("view not instance of RecyclerView!");
        }

        return view;
    }

    public TreeMap<Integer, TempTimerViewObject> createViewObject(TreeMap<Integer, RunningTimer> tempTimerTreeMap) {
        TreeMap<Integer, TempTimerViewObject> tempTimerViewObjectMap = new TreeMap<>();
        for (Map.Entry<Integer,RunningTimer>entry : tempTimerTreeMap.entrySet()) {
            RunningTimer runningTimer =  entry.getValue();
            TempTimerViewObject tempTimerViewObject = new TempTimerViewObject(runningTimer);
            tempTimerViewObjectMap.put(entry.getKey(), tempTimerViewObject);
        }
        return tempTimerViewObjectMap;
    }
}

package de.softinva.multitimer.utility;

import java.util.TreeMap;

import androidx.recyclerview.widget.RecyclerView;

/**
 * From https://medium.com/androiddevelopers/android-data-binding-recyclerview-db7c40d9f0e4
 * and from generated code after created new class
 */
public class AppRunningTimerRecyclerAdapter<T> extends AppRecyclerAdapter<T> {
    public AppRunningTimerRecyclerAdapter(TreeMap<Object,T> objectList, int layoutId) {
        super(objectList, layoutId);
    }
    @Override
    protected T getObjForPosition(int position) {
       return  (T) this.objectList.values().toArray()[position];
    }




}
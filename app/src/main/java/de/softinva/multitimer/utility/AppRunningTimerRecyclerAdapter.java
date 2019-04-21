package de.softinva.multitimer.utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.TreeMap;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import de.softinva.multitimer.BR;
import de.softinva.multitimer.classes.OnTimerListInteractionListener;
import de.softinva.multitimer.fragments.list.AppList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Object} and makes a call to the
 * specified.
 * TODO: Replace the implementation with code for your data type.
 * <p>
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
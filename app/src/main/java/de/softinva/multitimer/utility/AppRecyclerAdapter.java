package de.softinva.multitimer.utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

import de.softinva.multitimer.BR;

/**
 * From https://medium.com/androiddevelopers/android-data-binding-recyclerview-db7c40d9f0e4
 * and from generated code after created new class
 */
public class AppRecyclerAdapter<T> extends RecyclerView.Adapter<AppViewHolder> {
    protected final TreeMap<Object, T> objectList;
    protected final int layoutId;

    public AppRecyclerAdapter(TreeMap<Object, T> objectList, int layoutId) {
        this.objectList = objectList;
        this.layoutId = layoutId;
    }

    public AppViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        AppViewHolder viewHolder = new AppViewHolder(binding);
        binding.setLifecycleOwner(viewHolder);
        return viewHolder;
    }

    public void onBindViewHolder(AppViewHolder holder,
                                 int position) {
        Object viewObject = getObjForPosition(position);
        holder.bind(viewObject);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    protected T getObjForPosition(int position) {
        return this.objectList.get(position);
    }

    @Override
    public void onViewAttachedToWindow(AppViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof AppViewHolder) {
            holder.markAttach();
        }
    }

    @Override
    public void onViewDetachedFromWindow(AppViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        if (holder instanceof AppViewHolder) {
            holder.markDetach();
        }
    }
}

class AppViewHolder extends RecyclerView.ViewHolder implements LifecycleOwner {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private final ViewDataBinding binding;

    public AppViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        lifecycleRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
    }

    public void bind(Object viewObject) {
        binding.setVariable(BR.viewObject, viewObject);
        binding.executePendingBindings();
    }

    public void markAttach() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    public void markDetach() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
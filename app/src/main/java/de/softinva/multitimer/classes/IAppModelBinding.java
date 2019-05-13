package de.softinva.multitimer.classes;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

public interface IAppModelBinding<T> {
    T getModel();

    Context getContext();

    ViewDataBinding getBinding();

    LifecycleOwner getLifecycleOwner();

}

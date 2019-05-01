package de.softinva.multitimer.classes;

import android.content.Context;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public abstract class AppViewModel extends ViewModel {
    protected SavedStateHandle state;

    protected AppViewObject viewObject;
    protected Context context;

    public AppViewModel(SavedStateHandle savedStateHandle) {
        super();
        state = savedStateHandle;

    }

    protected abstract void setViewObject();

    public AppViewObject getViewObject() {
        return viewObject;
    }
}

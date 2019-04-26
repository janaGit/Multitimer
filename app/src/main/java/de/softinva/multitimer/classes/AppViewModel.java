package de.softinva.multitimer.classes;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public abstract class AppViewModel extends ViewModel {
    protected SavedStateHandle state;

    protected AppViewObject viewObject;

    public AppViewModel(SavedStateHandle savedStateHandle) {
        super();
        state = savedStateHandle;
    }

    protected abstract void setViewObject();

    public AppViewObject getViewObject() {
        return viewObject;
    }
}

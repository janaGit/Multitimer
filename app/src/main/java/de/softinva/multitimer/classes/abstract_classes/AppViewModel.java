package de.softinva.multitimer.classes.abstract_classes;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;

public abstract class AppViewModel extends AndroidViewModel {
    protected SavedStateHandle state;

    public AppViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application);
        state = savedStateHandle;

    }

}

package de.softinva.multitimer.classes;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public abstract class AppViewModel extends AndroidViewModel {
    protected SavedStateHandle state;

    protected AppViewObject viewObject;
    protected Context context;

    public AppViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application);
        state = savedStateHandle;

    }

}

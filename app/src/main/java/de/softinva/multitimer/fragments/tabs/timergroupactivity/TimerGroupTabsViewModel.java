package de.softinva.multitimer.fragments.tabs.timergroupactivity;


import android.app.Application;

import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;

public class TimerGroupTabsViewModel extends AppViewModel {
    public TimerGroupTabsViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }
}

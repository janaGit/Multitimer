package de.softinva.multitimer.fragments.tabs.mainactivity;
import android.app.Application;

import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;

public class MainActivityTabsViewModel extends AppViewModel {

    public MainActivityTabsViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }
}

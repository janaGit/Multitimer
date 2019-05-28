package de.softinva.multitimer.activities;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.interfaces.ActivityTabsViewModel;
import de.softinva.multitimer.classes.abstract_classes.AppViewModel;
import de.softinva.multitimer.model.TABS;

public class MainActivityViewModel extends AppViewModel implements ActivityTabsViewModel {
    private MutableLiveData<TABS> activeTab;

    public MainActivityViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public MutableLiveData<TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

}

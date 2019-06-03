package de.softinva.multitimer.activities;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;
import de.softinva.multitimer.model.MAIN_ACTIVITY_TABS;

public class MainActivityViewModel extends AppViewModel {
    private MutableLiveData<MAIN_ACTIVITY_TABS> activeTab;

    public MainActivityViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public MutableLiveData<MAIN_ACTIVITY_TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

}

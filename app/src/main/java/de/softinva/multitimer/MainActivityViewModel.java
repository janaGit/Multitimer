package de.softinva.multitimer;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.ActivityTabsViewModel;
import de.softinva.multitimer.model.TABS;

public class MainActivityViewModel extends ActivityTabsViewModel {
    private MutableLiveData<TABS> activeTab;

    public MutableLiveData<TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

}

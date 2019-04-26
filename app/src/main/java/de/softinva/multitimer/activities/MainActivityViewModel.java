package de.softinva.multitimer.activities;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.ActivityTabsViewModel;
import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.TABS;

public class MainActivityViewModel extends AppViewModel implements ActivityTabsViewModel {
    private MutableLiveData<TABS> activeTab;

    public MainActivityViewModel(SavedStateHandle savedStateHandle) {
        super(savedStateHandle);
    }

    @Override
    protected void setViewObject() {
        viewObject = new MainActivityViewObject(null);
    }

    public MutableLiveData<TABS> getActiveTab() {
        if (activeTab == null) {
            activeTab = new MutableLiveData<>();
        }
        return activeTab;
    }

}

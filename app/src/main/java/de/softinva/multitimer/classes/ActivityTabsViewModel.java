package de.softinva.multitimer.classes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import de.softinva.multitimer.model.MAIN_ACTIVITY_TABS;
import de.softinva.multitimer.model.TABS;

public abstract class ActivityTabsViewModel extends ViewModel {
    public abstract MutableLiveData<TABS> getActiveTab();
}

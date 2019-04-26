package de.softinva.multitimer.classes;

import androidx.lifecycle.MutableLiveData;
import de.softinva.multitimer.model.TABS;

public interface ActivityTabsViewModel {
    MutableLiveData<TABS> getActiveTab();
}

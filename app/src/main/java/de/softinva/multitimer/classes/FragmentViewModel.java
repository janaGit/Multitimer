package de.softinva.multitimer.classes;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import de.softinva.multitimer.utility.AppLogger;

public abstract class FragmentViewModel extends ViewModel {
    protected AppLogger logger = new AppLogger(this);

}


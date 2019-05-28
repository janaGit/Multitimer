package de.softinva.multitimer.classes.abstract_classes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import de.softinva.multitimer.utility.AppLogger;

public abstract class FragmentViewModel extends AndroidViewModel {
    protected AppLogger logger = new AppLogger(this);

    public FragmentViewModel(@NonNull Application application) {
        super(application);
    }
}


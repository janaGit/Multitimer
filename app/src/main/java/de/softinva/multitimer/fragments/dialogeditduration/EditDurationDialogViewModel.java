package de.softinva.multitimer.fragments.dialogeditduration;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;

public class EditDurationDialogViewModel extends AppViewModel {
    private MutableLiveData<Integer> durationInSec$;

    public EditDurationDialogViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }


    public MutableLiveData<Integer> getDurationInSec$() {
        if (durationInSec$ == null) {
            durationInSec$ = state.getLiveData("durationInSec");
        }
        return durationInSec$;
    }

}

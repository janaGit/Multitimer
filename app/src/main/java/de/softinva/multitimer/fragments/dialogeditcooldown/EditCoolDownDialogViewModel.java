package de.softinva.multitimer.fragments.dialogeditcooldown;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;

public class EditCoolDownDialogViewModel extends AppViewModel {
    private MutableLiveData<Integer> coolDownInSec$;
    public EditCoolDownDialogViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }
    public MutableLiveData<Integer> getCoolDownInSec$() {
        if (coolDownInSec$ == null) {
            coolDownInSec$ = state.getLiveData("durationInSec");
        }
        return coolDownInSec$;
    }
}

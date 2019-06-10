package de.softinva.multitimer.activities.takephoto;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;

public class TakePhotoViewModel extends AppViewModel {
    private MutableLiveData<String> timerGroupId$;
    private MutableLiveData<String> timerId$;

    public TakePhotoViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public MutableLiveData<String> getTimerGroupId() {
        if (timerGroupId$ == null) {
            timerGroupId$ = state.getLiveData("groupId");
        }
        return timerGroupId$;
    }

    public MutableLiveData<String> getTimerId() {
        if (timerId$ == null) {
            timerId$ = state.getLiveData("timerId");
        }
        return timerId$;
    }
}

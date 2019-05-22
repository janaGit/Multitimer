package de.softinva.multitimer.activities.timergroup;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.AppViewModel;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public abstract class AbstractTimerGroupViewModel extends AppViewModel {
    protected LiveData<TimerGroup> timerGroup$;
    private MutableLiveData<String> timerGroupId$;
    private LiveData<Boolean> doDisabledTimersExists;

    public AbstractTimerGroupViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }


    public LiveData<TimerGroup> getTimerGroup(String groupId) {
        if (timerGroup$ == null) {
            timerGroup$ = new TimerRepository(getApplication()).getTimerGroup(groupId);
        }
        return timerGroup$;
    }

    public LiveData<Boolean> doDisabledTimersExists() {
        if (doDisabledTimersExists == null) {
            doDisabledTimersExists = Transformations.switchMap(timerGroupId$, timerGroupId ->
                    Transformations.map(new TimerRepository(getApplication()).getAllDisabledTimersForTimerGroup(timerGroupId), disabledTimers
                            -> new Boolean(disabledTimers.size() > 0)));
        }
        return doDisabledTimersExists;
    }

    public MutableLiveData<String> getTimerGroupId$() {
        if (timerGroupId$ == null) {
            timerGroupId$ = state.getLiveData("groupId");
        }
        return timerGroupId$;
    }
}

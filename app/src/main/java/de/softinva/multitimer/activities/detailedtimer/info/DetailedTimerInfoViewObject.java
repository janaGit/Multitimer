package de.softinva.multitimer.activities.detailedtimer.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.UtilityMethods;

public class DetailedTimerInfoViewObject extends AppViewObject<LiveData<RunningTimer>> {
    public DetailedTimerInfoViewObject(LiveData<RunningTimer> obj) {
        super(obj);
    }

    public LiveData<String> generateTimeView() {
        return Transformations.map(obj, runningTimer -> {
            DetailedTimer detailedTimer = (DetailedTimer) runningTimer.getTimer();
            String string = UtilityMethods.transformSecIntoString(detailedTimer.getDurationInSec());

            if (detailedTimer.getCoolDownInSec() > 0) {
                return string + " (" + UtilityMethods.transformSecIntoString(detailedTimer.getCoolDownInSec()) + ")";
            }

            return string;
        });


    }
}

package de.softinva.multitimer.model;

import java.util.Date;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.utility.AppLogger;


public class RunningTimer {
    AppLogger logger = new AppLogger(this);
    protected Timer timer;
    protected Long finishTimeInSec;
    protected MutableLiveData<Long> countDownInSec;
    protected MutableLiveData<Boolean> isRunning;

    public RunningTimer(Timer timer) {
        this.timer = timer;
        countDownInSec = new MutableLiveData<>();
        isRunning = new MutableLiveData<>(false);
    }

    public MutableLiveData<Boolean> isRunning() {
        return isRunning;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public MutableLiveData<Long> getCountDownInSec() {
        return countDownInSec;
    }


    public Long getFinishTimeInSec() {
        if (finishTimeInSec == null) {
            logger.info("Timer not yet started!");
            finishTimeInSec = new Date().getTime();
        }
        return finishTimeInSec;
    }

    public void setToStart() {
        if (finishTimeInSec != null) {
            throw new Error("Finish time already set!");
        }
        finishTimeInSec = new Date().getTime() + (timer.durationInSec * 1000);
        isRunning.setValue(true);
    }

    public void setToStop() {
        finishTimeInSec = null;
        isRunning.setValue(false);
    }

}

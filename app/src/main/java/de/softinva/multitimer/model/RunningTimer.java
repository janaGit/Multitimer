package de.softinva.multitimer.model;

import java.util.Date;

import androidx.lifecycle.MutableLiveData;


public class RunningTimer {
    protected Timer timer;
    protected Long finishTimeInSec;
    protected MutableLiveData<Long> countDownInSec;
    protected MutableLiveData<Boolean> isRunning;

    public RunningTimer(Timer timer) {
        this.timer = timer;
        countDownInSec = new MutableLiveData<>();
        isRunning= new MutableLiveData<>();
        isRunning.setValue(false);
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
            throw new Error("Timer not yet started!");
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

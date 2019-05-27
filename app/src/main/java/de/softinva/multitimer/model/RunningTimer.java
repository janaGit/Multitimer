package de.softinva.multitimer.model;

import de.softinva.multitimer.BR;

import java.util.Date;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.utility.AppLogger;


public class RunningTimer extends BaseObservable {
    private AppLogger logger = new AppLogger(this);
    protected Timer timer;

    private Long finishTimeCountDownInSec;
    private MutableLiveData<Long> countDownInSec;

    private MutableLiveData<Boolean> isCountDownRunning;

    private MutableLiveData<Long> coolDownInSec;
    private MutableLiveData<Boolean> isCoolDownRunning;

    public RunningTimer(Timer timer) {
        this.timer = timer;
        countDownInSec = new MutableLiveData<>();
        coolDownInSec = new MutableLiveData<>();
        isCountDownRunning = new MutableLiveData<>(false);
        isCoolDownRunning = new MutableLiveData<>(false);
    }

    public void setCountDownInSec(MutableLiveData<Long> countDownInSec) {
        this.countDownInSec = countDownInSec;
    }

    public void setCoolDownInSec(MutableLiveData<Long> coolDownInSec) {
        this.coolDownInSec = coolDownInSec;
    }

    public void setIsCountDownRunning(MutableLiveData<Boolean> isCountDownRunning) {
        this.isCountDownRunning = isCountDownRunning;
    }

    public void setIsCoolDownRunning(MutableLiveData<Boolean> isCoolDownRunning) {
        this.isCoolDownRunning = isCoolDownRunning;
    }

    public MutableLiveData<Boolean> isCountDownRunning() {
        return isCountDownRunning;
    }

    public MutableLiveData<Boolean> isCoolDownRunning() {
        return isCoolDownRunning;
    }

    @Bindable
    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
        notifyPropertyChanged(BR.timer);
    }

    public MutableLiveData<Long> getCountDownInSec() {
        return countDownInSec;
    }

    public MutableLiveData<Long> getCoolDownInSec() {
        return coolDownInSec;
    }


    public Long getFinishTimeCountDownInSec() {
        if (finishTimeCountDownInSec == null) {
            logger.info("Timer not yet started!");
            finishTimeCountDownInSec = new Date().getTime();
        }
        return finishTimeCountDownInSec;
    }

    public void startCountDown() {
        if (finishTimeCountDownInSec != null) {
            throw new Error("Finish time already set!");
        }
        finishTimeCountDownInSec = new Date().getTime() + (timer.durationInSec * 1000);
        isCountDownRunning.setValue(true);
    }

    public Long stopCountDown() {
        Long finishTimeInSec = finishTimeCountDownInSec;
        finishTimeCountDownInSec = null;
        isCountDownRunning.setValue(false);
        return finishTimeInSec;
    }

    public void startCoolDown() {
        isCoolDownRunning.setValue(true);
    }

    public void stopCoolDown() {
        isCoolDownRunning.setValue(false);
    }

}

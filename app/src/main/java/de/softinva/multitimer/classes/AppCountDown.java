package de.softinva.multitimer.classes;


import android.os.CountDownTimer;

import de.softinva.multitimer.CountDownService;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;

public class AppCountDown {
    protected CountDownTimer countDownTimer;
    protected AppLogger logger;
    protected RunningTimer runningTimer;
    protected CountDownService service;

    public AppCountDown(RunningTimer runningTimer, CountDownService service) {
        logger = UtilityMethods.createLogger(this);
        this.runningTimer = runningTimer;
        this.service = service;

        countDownTimer = new CountDownTimer(runningTimer.getTimer().getDurationInSec() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                runningTimer.getCountDownInSec().setValue(millisUntilFinished / 1000);
                logger.info(runningTimer.getTimer().getTitle() + ": seconds remaining " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                service.removeTimer(runningTimer.getTimer());
                runningTimer.setToStop();
                logger.info(runningTimer.getTimer().getTitle() + " timer stopped running!");
            }
        };
    }

    public void run() {
        logger.info("start timer: " + runningTimer.getTimer().getTitle());
        runningTimer.setToStart();
        countDownTimer.start();
    }

    public void cancel() {
        logger.info("on cancel Timer: " + runningTimer.getTimer().getTitle());
        runningTimer.setToStop();
        countDownTimer.cancel();
    }
}

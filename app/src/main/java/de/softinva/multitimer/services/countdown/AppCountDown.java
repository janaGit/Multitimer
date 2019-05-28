package de.softinva.multitimer.services.countdown;


import android.os.CountDownTimer;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;

public class AppCountDown {
    private CountDownTimer countDownTimer;
    private AppLogger logger;
    private RunningTimer runningTimer;
    private CountDownService service;

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
                service.onStopTimer(runningTimer.getTimer());
                service.onFinishTimer(runningTimer.getTimer());
                logger.info(runningTimer.getTimer().getTitle() + " timer stopped running!");
            }
        };
    }

    public void run() {
        logger.info("start count down timer: " + runningTimer.getTimer().getTitle());
        runningTimer.startCountDown();
        countDownTimer.start();
    }

    public void cancel() {
        logger.info("on cancel count down timer: " + runningTimer.getTimer().getTitle());
        service.onStopTimer(runningTimer.getTimer());
        countDownTimer.cancel();

    }
}

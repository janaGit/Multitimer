package de.softinva.multitimer.classes;


import android.os.CountDownTimer;

import de.softinva.multitimer.CoolDownService;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;

public class AppCoolDown {
    private CountDownTimer countDownTimer;
    private AppLogger logger;
    private RunningTimer runningTimer;
    CoolDownService service;

    public AppCoolDown(RunningTimer runningTimer, CoolDownService service) {
        logger = UtilityMethods.createLogger(this);
        this.runningTimer = runningTimer;
        this.service = service;
        if (!(runningTimer.getTimer() instanceof DetailedTimer)) {
            throw new Error("timer not instance of DetailedTtmer: " + runningTimer.getTimer().getClass());
        }
        DetailedTimer detailedTimer = (DetailedTimer) runningTimer.getTimer();

        countDownTimer = new CountDownTimer(detailedTimer.getCoolDownInSec() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                runningTimer.getCoolDownInSec().setValue(millisUntilFinished / 1000);
                logger.info(runningTimer.getTimer().getTitle() + ": seconds remaining " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                service.onStopTimer((DetailedTimer) runningTimer.getTimer());
                service.onFinishTimer((DetailedTimer) runningTimer.getTimer());
                logger.info(runningTimer.getTimer().getTitle() + " timer stopped running!");
            }
        };
    }

    public void run() {
        logger.info("start cool down timer: " + runningTimer.getTimer().getTitle());
        runningTimer.startCoolDown();
        countDownTimer.start();
    }

    public void cancel() {
        service.onStopTimer((DetailedTimer) runningTimer.getTimer());
        logger.info("on cancel cool down timer: " + runningTimer.getTimer().getTitle());
        countDownTimer.cancel();
    }
}

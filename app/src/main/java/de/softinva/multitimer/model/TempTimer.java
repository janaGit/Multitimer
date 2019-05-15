package de.softinva.multitimer.model;

import de.softinva.multitimer.utility.UtilityMethods;

public class TempTimer extends Timer {

    String imageName = "default.jpg";

    public TempTimer() {
        id = UtilityMethods.createID();
        groupId = "";
    }

    public TempTimer(Timer timer) {
        super(timer.id, timer.title, timer.durationInSec, timer.imageName);
    }

    public TempTimer(String id, String title, Integer durationInSec) {
        super(id, title, durationInSec, null);
    }
}

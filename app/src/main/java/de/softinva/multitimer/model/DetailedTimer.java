package de.softinva.multitimer.model;

import de.softinva.multitimer.utility.UtilityMethods;

public class DetailedTimer extends Timer {
    protected String description;

    public DetailedTimer(String groupId) {
        super(UtilityMethods.createID(), groupId, "", 0, "");
        description = "";

    }

    public DetailedTimer(String id, String groupId, String title, Integer durationInSec, String imageName, String description) {
        super(id, groupId, title, durationInSec, imageName);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.title;
    }
}

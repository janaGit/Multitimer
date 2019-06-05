package de.softinva.multitimer.fragments.dialogimageselection;

public class ImageSelectionItem {
    ACTION_TYPE actionType;
    String title;
    int imageResId;

    ImageSelectionItem(ACTION_TYPE actionType, String title, int imageResId) {
        this.actionType = actionType;
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}

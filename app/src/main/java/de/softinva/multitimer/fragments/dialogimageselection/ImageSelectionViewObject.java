package de.softinva.multitimer.fragments.dialogimageselection;

import android.view.View;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

public class ImageSelectionViewObject extends AppViewObject<ImageSelectionItem> {
    ImageSelectionDialog.OnClickImageSelectionItem activity;
    ImageSelectionDialog dialog;

    public ImageSelectionViewObject(ImageSelectionItem obj, ImageSelectionDialog.OnClickImageSelectionItem activity, ImageSelectionDialog dialog) {
        super(obj);
        this.activity = activity;
        this.dialog = dialog;
    }

    public void onClickListItem(View view) {
        activity.onClickImageSelectionItem(obj.actionType);
        dialog.dismiss();
    }
}

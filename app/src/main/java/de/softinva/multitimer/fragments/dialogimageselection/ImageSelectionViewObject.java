package de.softinva.multitimer.fragments.dialogimageselection;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.softinva.multitimer.classes.abstract_classes.AppCompatViewObject;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

public class ImageSelectionViewObject extends AppCompatViewObject<ImageSelectionItem> {
    ImageSelectionDialog.OnClickImageSelectionItem activity;
    ImageSelectionDialog dialog;

    public ImageSelectionViewObject(ImageSelectionItem obj, ImageSelectionDialog.OnClickImageSelectionItem activity, ImageSelectionDialog dialog) {
        super(obj, (AppCompatActivity) activity);
        this.activity = activity;
        this.dialog = dialog;
    }

    public void onClickListItem(View view) {
        activity.onClickImageSelectionItem(obj.actionType);
        dialog.dismiss();
    }
}

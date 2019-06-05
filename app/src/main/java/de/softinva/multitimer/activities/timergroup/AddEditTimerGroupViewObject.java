package de.softinva.multitimer.activities.timergroup;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import de.softinva.multitimer.activities.selectimage.SelectImageActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class AddEditTimerGroupViewObject extends AppViewObject<TimerGroup> {
    protected boolean isEditTimerGroup;
    public final static Integer REQUESTCODE_SELECT_IMAGE_ACTIVITY = 10;
    private Boolean isSelectImageActivityOpen = false;

    public AddEditTimerGroupViewObject(boolean isEditTimerGroup, TimerGroup obj) {
        super(obj);
        this.isEditTimerGroup = isEditTimerGroup;
    }

    public void onClickSaveButton(View view) {
        if (isEditTimerGroup) {
            new TimerRepository(((AppCompatActivity) getContext()).getApplication()).updateTimerGroup(obj);
        } else {
            new TimerRepository(((AppCompatActivity) getContext()).getApplication()).insertTimerGroup(obj);
        }
        ((AppCompatActivity) getContext()).onBackPressed();
    }

    public void onClickAbortButton(View view) {
        ((AppCompatActivity) getContext()).onBackPressed();
    }

    public void onClickImage(View view) {
        if (!isSelectImageActivityOpen) {
            isSelectImageActivityOpen = true;
            new ImageSelectionDialog((ImageSelectionDialog.OnClickImageSelectionItem) getContext()).show(((FragmentActivity) getContext()).getSupportFragmentManager(), "selectImageDialog");
        }
    }

    public void setSelectImageActivityIsOpen(Boolean isSelectImageActivityOpen) {
        this.isSelectImageActivityOpen = isSelectImageActivityOpen;
    }
}

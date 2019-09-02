package de.softinva.multitimer.activities.timergroup;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import de.softinva.multitimer.classes.abstract_classes.AppCompatViewObject;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class AddEditTimerGroupViewObject extends AppCompatViewObject<TimerGroup> {
    protected boolean isEditTimerGroup;
    public final static Integer REQUESTCODE_SELECT_IMAGE_ACTIVITY = 10;
    private Boolean isSelectImageActivityOpen = false;

    public AddEditTimerGroupViewObject(boolean isEditTimerGroup, TimerGroup obj, AppCompatActivity activity) {
        super(obj, activity);
        this.isEditTimerGroup = isEditTimerGroup;
    }

    public void onClickSaveButton(View view) {
        if (isEditTimerGroup) {
            TimerRepository.getInstance(getActivity().getApplication()).updateTimerGroup(obj);
        } else {
            TimerRepository.getInstance(getActivity().getApplication()).insertTimerGroup(obj);
        }
        getActivity().onBackPressed();
    }

    public void onClickAbortButton(View view) {
        getActivity().onBackPressed();
    }

    public void onClickImage(View view) {
        if (!isSelectImageActivityOpen) {
            isSelectImageActivityOpen = true;
            ImageSelectionDialog.showDialog(activity);
        }
    }

    public void setSelectImageActivityIsOpen(Boolean isSelectImageActivityOpen) {
        this.isSelectImageActivityOpen = isSelectImageActivityOpen;
    }
}

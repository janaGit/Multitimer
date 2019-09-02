package de.softinva.multitimer.activities.detailedtimer;

import android.app.Application;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import de.softinva.multitimer.classes.abstract_classes.AppCompatViewObject;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.fragments.dialogeditcooldown.EditCoolDownDialog;
import de.softinva.multitimer.fragments.dialogeditduration.EditDurationDialog;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.Action;
import de.softinva.multitimer.utility.UtilityMethods;

public class AddEditDetailedTimerViewObject extends AppCompatViewObject<DetailedTimer> {
    protected boolean isEditDetailedTimer;
    public final static Integer REQUESTCODE_SELECT_IMAGE_ACTIVITY = 10;
    private Boolean isSelectImageActivityOpen = false;
    private AppCompatActivity activity;

    public AddEditDetailedTimerViewObject(boolean isEditDetailedTimer, DetailedTimer obj, AppCompatActivity activity) {
        super(obj, activity);
        this.activity = activity;
        this.isEditDetailedTimer = isEditDetailedTimer;
    }

    public void onClickSaveButton(View view) {
        Application application = getActivity().getApplication();
        if (isEditDetailedTimer) {
            if (obj.getIsEnabled()) {
                Action.cancelCoolDownIfRunning(getActivity().getApplication(), obj.getGroupId(), obj.getId());
            }
            TimerRepository.getInstance(application).updateDetailedTimer(obj);
            getActivity().onBackPressed();
        } else {
            TimerRepository.getInstance(application).getDetailedTimersForTimerGroup(obj.getGroupId()).observe(getActivity(), treeMap -> {
                obj.setPositionInGroup(treeMap.size());
                TimerRepository.getInstance(application).insertDetailedTimer(obj);
                getActivity().onBackPressed();
            });

        }
    }

    public void onClickDuration(View view) {
        EditDurationDialog.showDialog(getActivity());
    }

    public void onClickCoolDown(View view) {
        EditCoolDownDialog.showDialog(getActivity());

    }

    public void onClickAbortButton(View view) {
        getActivity().onBackPressed();
    }

    public void onClickStatusButton(View view) {
        obj.setIsEnabled(!obj.getIsEnabled());
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


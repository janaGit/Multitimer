package de.softinva.multitimer.activities.detailedtimer;

import android.app.Application;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.fragments.dialogeditcooldown.EditCoolDownDialog;
import de.softinva.multitimer.fragments.dialogeditduration.EditDurationDialog;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.Action;

public class AddEditDetailedTimerViewObject extends AppViewObject<DetailedTimer> {
    protected boolean isEditDetailedTimer;
    protected EditDurationDialog editDurationDialog;
    protected EditCoolDownDialog editCoolDownDialog;
    public final static Integer REQUESTCODE_SELECT_IMAGE_ACTIVITY = 10;
    private Boolean isSelectImageActivityOpen = false;

    public AddEditDetailedTimerViewObject(boolean isEditDetailedTimer, DetailedTimer obj, EditDurationDialog editDurationDialog, EditCoolDownDialog editCoolDownDialog) {
        super(obj);
        this.isEditDetailedTimer = isEditDetailedTimer;
        this.editDurationDialog = editDurationDialog;
        this.editCoolDownDialog = editCoolDownDialog;
    }

    public void onClickSaveButton(View view) {
        Application application = ((AppCompatActivity) getContext()).getApplication();
        if (isEditDetailedTimer) {
            if (obj.getIsEnabled()) {
                Action.cancelCoolDownIfRunning(((AppCompatActivity) getContext()).getApplication(), obj.getGroupId(), obj.getId());
            }
            TimerRepository.getInstance(application).updateDetailedTimer(obj);
            ((AppCompatActivity) getContext()).onBackPressed();
        } else {
            TimerRepository.getInstance(application).getDetailedTimersForTimerGroup(obj.getGroupId()).observe(((AppCompatActivity) getContext()), treeMap -> {
                obj.setPositionInGroup(treeMap.size());
                TimerRepository.getInstance(application).insertDetailedTimer(obj);
                ((AppCompatActivity) getContext()).onBackPressed();
            });

        }
    }

    public void onClickDuration(View view) {
        if (!editDurationDialog.isAdded()) {
            editDurationDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "editDuration");
        }

    }

    public void onClickCoolDown(View view) {
        if (!editCoolDownDialog.isAdded()) {
            editCoolDownDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "editCoolDown");
        }

    }

    public void onClickAbortButton(View view) {
        ((AppCompatActivity) getContext()).onBackPressed();
    }

    public void onClickStatusButton(View view) {
        obj.setIsEnabled(!obj.getIsEnabled());
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


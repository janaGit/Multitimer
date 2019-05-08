package de.softinva.multitimer.activities.detailedtimer;

import android.app.Application;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.fragments.editdurationdialog.EditDurationDialog;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.repository.TimerRepository;

public class AddEditDetailedTimerViewObject extends AppViewObject<DetailedTimer> {
    protected boolean isEditDetailedTimer;
    protected EditDurationDialog editDurationDialog;

    public AddEditDetailedTimerViewObject(boolean isEditDetailedTimer, DetailedTimer obj, EditDurationDialog editDurationDialog) {
        super(obj);
        this.isEditDetailedTimer = isEditDetailedTimer;
        this.editDurationDialog = editDurationDialog;
    }

    public void onClickSaveButton(View view) {
        Application application = ((AppCompatActivity) getContext()).getApplication();
        if (isEditDetailedTimer) {
            new TimerRepository(application).updateDetailedTimer(obj);
            ((AppCompatActivity) getContext()).onBackPressed();
        } else {
            new TimerRepository(application).getDetailedTimersForTimerGroup(obj.getGroupId()).observe(((AppCompatActivity) getContext()), treeMap -> {
                obj.setPositionInGroup(treeMap.size());
                new TimerRepository(application).insertDetailedTimer(obj);
                ((AppCompatActivity) getContext()).onBackPressed();
            });

        }
    }

    public void onClickDurationView(View view) {
        if (!editDurationDialog.isAdded()) {
            editDurationDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "editDuration");
        }

    }

    public void onClickAbortButton(View view) {
        ((AppCompatActivity) getContext()).onBackPressed();
    }
}


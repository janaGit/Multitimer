package de.softinva.multitimer.fragments.dialogaddtemptimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import de.softinva.multitimer.services.countdown.CountDownService;
import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityAddTempTimerBinding;
import de.softinva.multitimer.utility.EditDuration;

public class AddTempTimerDialog extends AppDialogFragmentDataBinding<AddTempTimerDialogViewModel> {
    EditDuration editDuration;

    public AddTempTimerDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = (AlertDialog) super.onCreateDialog(savedInstanceState);
        editDuration = new EditDuration(this, false);
        model.getDurationinSec$().observe(this, durationInSec -> editDuration.durationInSec.setValue(durationInSec));

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.start_timer), (_dialog, id) -> {
            model.tempTimer.setDurationInSec(editDuration.getDurationInSec());
            CountDownService.startNewTimer(model.tempTimer, getContext());
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.button_abort), (_dialog, id) -> {
        });

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editDuration.setCallbackToNull();
        model.getDurationinSec$().setValue(editDuration.getDurationInSec());

    }


    @Override
    protected void setClassSpecificObjects() {
    }

    @Override
    protected AppViewObject setViewObject() {
        return new AddTempTimerViewObject(model.tempTimer, getActivity());
    }

    @Override
    protected Class<AddTempTimerDialogViewModel> returnModelClass() {
        return AddTempTimerDialogViewModel.class;
    }

    @Override
    protected ViewDataBinding setBinding() {
        ActivityAddTempTimerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.activity_add_temp_timer, null, false);
        return binding;
    }

}

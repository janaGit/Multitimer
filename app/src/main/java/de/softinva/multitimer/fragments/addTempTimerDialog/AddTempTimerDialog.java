package de.softinva.multitimer.fragments.addTempTimerDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.services.countdown.CountDownService;
import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityAddTempTimerBinding;
import de.softinva.multitimer.utility.EditDuration;

public class AddTempTimerDialog extends AppDialogFragmentDataBinding<AddTempTimerDialogViewModel> {
    EditDuration editDuration;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        editDuration = new EditDuration(this, false);
        this.editDuration.durationInSec.setValue(30);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot())
                .setPositiveButton(R.string.start_timer, (dialog, id) -> {
                    model.tempTimer.setDurationInSec(editDuration.getDurationInSec());
                    CountDownService.startNewTimer(model.tempTimer, getContext());
                })
                .setNegativeButton(R.string.button_abort, (dialog, id) -> {
                });
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unbind();
        editDuration.setCallbackToNull();
    }


    @Override
    protected void setClassSpecificObjects() {
    }

    @Override
    protected AppViewObject setViewObject() {
        return new AddTempTimerViewObject(model.tempTimer);
    }

    @Override
    protected AddTempTimerDialogViewModel setModel() {
        return new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddTempTimerDialogViewModel.class);
    }

    @Override
    protected ViewDataBinding setBinding() {
        ActivityAddTempTimerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.activity_add_temp_timer, null, false);
        return binding;
    }

}

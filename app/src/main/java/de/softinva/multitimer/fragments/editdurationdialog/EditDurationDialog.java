package de.softinva.multitimer.fragments.editdurationdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.databinding.EditDurationDialogBinding;
import de.softinva.multitimer.utility.EditDuration;

public class EditDurationDialog extends AppDialogFragmentDataBinding<EditDurationDialogViewModel> {
    EditDuration editDuration;
    public MutableLiveData<Integer> durationInSec = new MutableLiveData<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        editDuration = new EditDuration(this);
        durationInSec.observe(this, durationInSec -> this.editDuration.durationInSec.setValue(durationInSec));
        return dialog;
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

    public void setDurationInSec(int durationInSec) {
        this.durationInSec.setValue(durationInSec);
    }

    @Override
    protected AppViewObject setViewObject() {
        return null;
    }

    @Override
    protected EditDurationDialogViewModel setModel() {
        return new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(EditDurationDialogViewModel.class);
    }

    @Override
    protected ViewDataBinding setBinding() {
        EditDurationDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.edit_duration_dialog, null, false);
        return binding;
    }


}

package de.softinva.multitimer.fragments.dialogeditduration;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

import de.softinva.multitimer.utility.EditDuration;

public class EditDurationDialog extends AppDialogFragmentDataBinding<EditDurationDialogViewModel> implements EditDuration.EditDurationActionsListener, EditDuration.UpdateDurationInSecListener {
    EditDuration editDuration;
    public MutableLiveData<Integer> durationInSec = new MutableLiveData<>();
    private UpdateDurationInSecListener callbackDurationInSec;

    public EditDurationDialog() {
        super();
    }

    public EditDurationDialog(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        editDuration = new EditDuration(this, true);
        durationInSec.observe(this, durationInSec -> this.editDuration.durationInSec.setValue(durationInSec));
        setCallBackListener();
        return dialog;
    }

    private void setCallBackListener() {
        if (getContext() instanceof UpdateDurationInSecListener) {
            callbackDurationInSec = (UpdateDurationInSecListener) getContext();
        } else {
            throw new RuntimeException(getContext().toString()
                    + " must implement UpdateCollDownInSecListener");
        }
    }

    public interface UpdateDurationInSecListener {
        void updateDurationInSec(int durationInSec);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    protected Class<EditDurationDialogViewModel> returnModelClass() {
        return EditDurationDialogViewModel.class;
    }

    @Override
    protected ViewDataBinding setBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.edit_duration_dialog, null, false);
    }


    @Override
    public void onCancel() {
        dismiss();
    }

    @Override
    public void onSave() {
        dismiss();
    }

    @Override
    public void updateDurationInSec(int durationInSec) {
        callbackDurationInSec.updateDurationInSec(durationInSec);
    }
}

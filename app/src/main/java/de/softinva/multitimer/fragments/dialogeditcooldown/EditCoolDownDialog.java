package de.softinva.multitimer.fragments.dialogeditcooldown;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.EditDurationDialogBinding;
import de.softinva.multitimer.fragments.dialogeditduration.EditDurationDialog;
import de.softinva.multitimer.utility.EditDuration;

public class EditCoolDownDialog extends AppDialogFragmentDataBinding<EditCoolDownDialogViewModel> implements EditDuration.EditDurationActionsListener, EditDuration.UpdateDurationInSecListener {
    EditDuration editDuration;
    public static final String EDIT_COOLDOWN_DIALOG = "editCoolDownDialog";
    public MutableLiveData<Integer> coolDownInSec = new MutableLiveData<>();
    private UpdateCollDownInSecListener callbackCoolDownInSec;
    private static EditCoolDownDialog instance;

    public static EditCoolDownDialog showDialog(FragmentActivity activity) {
        EditCoolDownDialog dialog = EditCoolDownDialog.getInstance(activity);

        if (!dialog.isAdded()) {
            dialog.show(activity.getSupportFragmentManager(), EDIT_COOLDOWN_DIALOG);

            activity.getSupportFragmentManager().executePendingTransactions();
        }

        return dialog;
    }

    public static EditCoolDownDialog getInstance(FragmentActivity activity) {
        EditCoolDownDialog dialog = (EditCoolDownDialog) activity.getSupportFragmentManager().findFragmentByTag(EDIT_COOLDOWN_DIALOG);
        if (dialog == null) {
            if (instance == null) {
                instance = new EditCoolDownDialog();
            }
            dialog = instance;
        }

        return dialog;

    }

    public EditCoolDownDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        editDuration = new EditDuration(this, true);
        coolDownInSec.observe(this, durationInSec -> {
            this.model.getCoolDownInSec$().setValue(durationInSec);
        });
        this.model.getCoolDownInSec$().observe(this, durationInSec -> {
            this.editDuration.durationInSec.setValue(durationInSec);
        });
        setCallBackListener();
        return dialog;
    }

    private void setCallBackListener() {
        if (getContext() instanceof UpdateCollDownInSecListener) {
            callbackCoolDownInSec = (UpdateCollDownInSecListener) getContext();
        } else {
            throw new RuntimeException(getContext().toString()
                    + " must implement UpdateCollDownInSecListener");
        }
    }

    public interface UpdateCollDownInSecListener {
        void updateCoolDownInSec(int durationInSec);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editDuration.setCallbackToNull();
        model.getCoolDownInSec$().setValue(editDuration.getDurationInSec());
    }


    @Override
    protected void setClassSpecificObjects() {

    }

    public void setCoolDownInSec(int durationInSec) {
        this.coolDownInSec.setValue(durationInSec);
    }

    @Override
    protected AppViewObject setViewObject() {
        return null;
    }

    @Override
    protected Class<EditCoolDownDialogViewModel> returnModelClass() {
        return EditCoolDownDialogViewModel.class;
    }

    @Override
    protected ViewDataBinding setBinding() {
        EditDurationDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.edit_duration_dialog, null, false);
        return binding;
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
        callbackCoolDownInSec.updateCoolDownInSec(durationInSec);
    }
}

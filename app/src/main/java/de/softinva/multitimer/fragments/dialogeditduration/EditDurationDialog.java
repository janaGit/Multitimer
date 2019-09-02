package de.softinva.multitimer.fragments.dialogeditduration;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

import de.softinva.multitimer.utility.EditDuration;
import de.softinva.multitimer.utility.UtilityMethods;

public class EditDurationDialog extends AppDialogFragmentDataBinding<EditDurationDialogViewModel> implements EditDuration.EditDurationActionsListener, EditDuration.UpdateDurationInSecListener {
    EditDuration editDuration;
    public static final String EDIT_DURATION_DIALOG = "editDurationDialog";
    private UpdateDurationInSecListener callbackDurationInSec;
    private static EditDurationDialog instance;
    public MutableLiveData<Integer> durationInSec = new MutableLiveData<>();

    public static EditDurationDialog showDialog(FragmentActivity activity) {
        EditDurationDialog dialog = EditDurationDialog.getInstance(activity);
        if (!dialog.isAdded()) {
            dialog.show(activity.getSupportFragmentManager(), EDIT_DURATION_DIALOG);
            activity.getSupportFragmentManager().executePendingTransactions();
        }
        return dialog;
    }

    public static EditDurationDialog getInstance(FragmentActivity activity) {
        EditDurationDialog dialog = (EditDurationDialog) activity.getSupportFragmentManager().findFragmentByTag(EDIT_DURATION_DIALOG);
        if (dialog == null) {
            if (instance == null) {
                instance = new EditDurationDialog();
            }
            dialog = instance;
        }

        return dialog;
    }

    public EditDurationDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        editDuration = new EditDuration(this, true);
        durationInSec.observe(this, sec -> {
            this.model.getDurationInSec$().setValue(sec);
        });

        model.getDurationInSec$().observe(this, sec -> {
            this.editDuration.durationInSec.setValue(sec);
        });
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
        callbackDurationInSec = null;
        model.getDurationInSec$().setValue(editDuration.getDurationInSec());
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

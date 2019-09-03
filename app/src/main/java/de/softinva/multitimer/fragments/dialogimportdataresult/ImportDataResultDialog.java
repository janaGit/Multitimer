package de.softinva.multitimer.fragments.dialogimportdataresult;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

public class ImportDataResultDialog extends AppDialogFragmentDataBinding<ImportDataResultDialogViewModel> {
    MutableLiveData<ImportDataMessages> messages$;

    public ImportDataResultDialog() {

    }

    public MutableLiveData<ImportDataMessages> getMessages() {
        return messages$;
    }

    public void setMessages(MutableLiveData<ImportDataMessages> messages) {
        this.messages$ = messages;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = (AlertDialog) super.onCreateDialog(savedInstanceState);

        dialog.setTitle(R.string.dialog_import_data_result_title);

        DialogInterface.OnClickListener buttonListener = (dialogInterface, which) -> {
            if (which == AlertDialog.BUTTON_POSITIVE) {
                dialog.hide();
                return;
            }
            if (which == AlertDialog.BUTTON_NEGATIVE) {
                dialog.hide();
                getActivity().finish();
                return;
            }

        };
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.dialog_import_data_result_button_positive), buttonListener);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.dialog_import_data_result_button_negative), buttonListener);

        return dialog;
    }

    @Override
    protected AppViewObject setViewObject() {
        return new ImportDataResultViewObject(messages$, (AppCompatActivity) getActivity(), this);
    }

    @Override
    protected Class<ImportDataResultDialogViewModel> returnModelClass() {
        return ImportDataResultDialogViewModel.class;
    }

    @Override
    protected ViewDataBinding setBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_import_data_result, null, false);

    }

    @Override
    protected void setClassSpecificObjects() {

    }
}

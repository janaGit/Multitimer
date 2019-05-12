package de.softinva.multitimer.fragments.editdurationdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.databinding.EditDurationDialogBinding;
import de.softinva.multitimer.viewcomponents.EditDurationFields;

public class EditDurationDialog extends AppDialogFragmentDataBinding<EditDurationDialogViewModel> implements EditDurationFields.EditDurationFieldsFocusChangeListener {
    protected MutableLiveData<Integer> durationInSec = new MutableLiveData<>();
    EditDurationFields editDurationFields;
    int indexOfFieldHasFocus = 0;
    UpdateDurationInSecListener callback;
    boolean areKeysDisabled = false;
    Keyboard keyboard;
    KeyboardView keyboardView;

    @Override
    protected void setKeyboard() {
        keyboard = new Keyboard(getContext(), R.xml.keyboard_09);
        keyboardView = ((EditDurationDialogBinding) binding).keyboardView;
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setEnabled(true);
    }

    protected void disableKeys() {
        keyboard.getKeys().get(5).label = "";
        keyboard.getKeys().get(6).label = "";
        keyboard.getKeys().get(7).label = "";
        keyboard.getKeys().get(8).label = "";
        keyboardView.invalidateAllKeys();
        areKeysDisabled = true;
    }

    protected boolean deactivatableKey(int primaryCode) {
        if (primaryCode >= 6 && primaryCode <= 9) {
            return true;
        }
        return false;
    }

    protected void enableKeys() {
        keyboard.getKeys().get(5).label = "6";
        keyboard.getKeys().get(6).label = "7";
        keyboard.getKeys().get(7).label = "8";
        keyboard.getKeys().get(8).label = "9";
        keyboardView.invalidateAllKeys();
        areKeysDisabled = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);


        editDurationFields = new EditDurationFields(this, getContext(), null);
        ((EditDurationDialogBinding) binding).fragmentContainer.addView(editDurationFields);

        setFocus(0);
        durationInSec.observe(this, durationInSec -> updateDurationFields(durationInSec));

        setCallBackListener();
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unbind();
        editDurationFields = null;
        callback = null;
    }

    protected void setCallBackListener() {
        Context context = getContext();
        if (context instanceof UpdateDurationInSecListener) {
            callback = (UpdateDurationInSecListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement UpdateDurationInSecListener");
        }
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


    void updateDurationFields(int durationInSec) {
        int hours = durationInSec / 3600;
        int minutes = (durationInSec - (hours * 3600)) / 60;
        int seconds = (durationInSec - (hours * 3600) - (minutes * 60));

        editDurationFields.setNumber(0, hours / 10);
        editDurationFields.setNumber(1, hours % 10);

        editDurationFields.setNumber(2, minutes / 10);
        editDurationFields.setNumber(3, minutes % 10);

        editDurationFields.setNumber(4, seconds / 10);
        editDurationFields.setNumber(5, seconds % 10);
    }

    protected void moveFocusRight() {
        if (indexOfFieldHasFocus < 5) {
            editDurationFields.setFocus(indexOfFieldHasFocus + 1);
        }

    }

    protected boolean isFocusOndeactivateableField() {
        if (indexOfFieldHasFocus == 2 || indexOfFieldHasFocus == 4) {
            return true;
        }
        return false;
    }

    protected void moveFocusLeft() {
        if (indexOfFieldHasFocus > 0) {
            editDurationFields.setFocus(indexOfFieldHasFocus - 1);
        }
    }

    protected void setFocus(int indexFocus) {
        editDurationFields.setFocus(indexFocus);
    }

    protected void saveDuration() {
        int durationInSec;
        int hours10 = editDurationFields.getNumber(0);
        durationInSec = hours10 * 36000;
        int hours = editDurationFields.getNumber(1);
        durationInSec += hours * 3600;
        int minutes10 = editDurationFields.getNumber(2);
        durationInSec += minutes10 * 600;
        int minutes = editDurationFields.getNumber(3);
        durationInSec += minutes * 60;
        int seconds10 = editDurationFields.getNumber(4);
        durationInSec += seconds10 * 10;
        int seconds = editDurationFields.getNumber(5);
        durationInSec += seconds;

        callback.updateDurationInSec(durationInSec);
    }

    @Override
    public void onHasFocus(int indexField) {
        this.indexOfFieldHasFocus = indexField;
        if (indexOfFieldHasFocus == 2 || indexOfFieldHasFocus == 4) {
            disableKeys();
        } else {
            enableKeys();
        }
    }

    public interface UpdateDurationInSecListener {
        void updateDurationInSec(int durationInSec);
    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode >= 0) {
                if (isFocusOndeactivateableField() && areKeysDisabled && deactivatableKey(primaryCode)) {
                    return;
                }
                editDurationFields.setNumber(indexOfFieldHasFocus, primaryCode);
                moveFocusRight();
                return;
            }
            switch (primaryCode) {
                case -1:
                    moveFocusLeft();
                    return;
                case -2:
                    moveFocusRight();
                    return;
                case -3:
                    dismiss();
                    return;
                case -4:
                    saveDuration();
                    dismiss();
                    return;
            }
        }

        @Override
        public void onPress(int arg0) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    };

}

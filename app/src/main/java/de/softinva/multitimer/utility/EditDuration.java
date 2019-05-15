package de.softinva.multitimer.utility;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.widget.FrameLayout;

import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.IAppModelBinding;
import de.softinva.multitimer.viewcomponents.EditDurationFields;

public class EditDuration implements EditDurationFields.EditDurationFieldsFocusChangeListener {
    private boolean areKeysDisabled = false;
    private Keyboard keyboard;
    private KeyboardView keyboardView;
    private EditDurationFields editDurationFields;
    private int indexOfFieldHasFocus = 0;
    public MutableLiveData<Integer> durationInSec = new MutableLiveData<>();
    private IAppModelBinding appModelBinding;
    private UpdateDurationInSecListener callbackDurationInSec;
    private EditDurationActionsListener callbackActions;
    private boolean isWithActionButtons;

    public EditDuration(IAppModelBinding appModelBinding, boolean isWithActionButtons) {
        this.appModelBinding = appModelBinding;
        this.isWithActionButtons = isWithActionButtons;
        editDurationFields = new EditDurationFields(this, appModelBinding.getContext(), null);

        FrameLayout view = appModelBinding.getBinding().getRoot().findViewById(R.id.edit_duration_fields_container);
        if (view != null) {
            view.addView(editDurationFields);
        } else {
            throw new Error("view not found!");
        }
        setKeyboard();
        setCallBackListener();
        setFocusToStart();
        durationInSec.observe(appModelBinding.getLifecycleOwner(), this::updateDurationFields);

    }

    public void setCallbackToNull() {
        callbackDurationInSec = null;
    }

    private void setCallBackListener() {

        if (isWithActionButtons) {
            if (appModelBinding instanceof UpdateDurationInSecListener) {
                callbackDurationInSec = (UpdateDurationInSecListener) appModelBinding;
            } else {
                Context context = this.appModelBinding.getContext();
                if (context instanceof UpdateDurationInSecListener) {
                    callbackDurationInSec = (UpdateDurationInSecListener) context;
                } else {
                    throw new RuntimeException(context.toString() + " or " + appModelBinding.toString()
                            + " must implement UpdateDurationInSecListener");
                }
            }
            if (this.appModelBinding instanceof EditDurationActionsListener) {
                callbackActions = (EditDurationActionsListener) this.appModelBinding;
            } else {
                throw new RuntimeException(appModelBinding.toString()
                        + " must implement EditDurationActionsListener");
            }
        }

    }

    private void setKeyboard() {
        if (isWithActionButtons) {
            keyboard = new Keyboard(appModelBinding.getContext(), R.xml.keyboard_action_buttons);
        } else {
            keyboard = new Keyboard(appModelBinding.getContext(), R.xml.keyboard);
        }
        keyboardView = appModelBinding.getBinding().getRoot().findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setEnabled(true);
    }

    private void disableKeys() {
        keyboard.getKeys().get(5).label = "";
        keyboard.getKeys().get(6).label = "";
        keyboard.getKeys().get(7).label = "";
        keyboard.getKeys().get(8).label = "";
        keyboardView.invalidateAllKeys();
        areKeysDisabled = true;
    }

    private boolean deactivatableKey(int primaryCode) {
        return primaryCode >= 6 && primaryCode <= 9;
    }

    public void setDurationInSec(int durationInSec) {
        this.durationInSec.setValue(durationInSec);
    }

    public void saveDuration() {
        int durationInSec = calculateDurationInSec();
        callbackDurationInSec.updateDurationInSec(durationInSec);
    }

    public int getDurationInSec() {
        return calculateDurationInSec();
    }

    private int calculateDurationInSec() {
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
        return durationInSec;
    }

    public interface UpdateDurationInSecListener {
        void updateDurationInSec(int durationInSec);
    }

    public interface EditDurationActionsListener {
        void onCancel();

        void onSave();
    }

    private void enableKeys() {
        keyboard.getKeys().get(5).label = "6";
        keyboard.getKeys().get(6).label = "7";
        keyboard.getKeys().get(7).label = "8";
        keyboard.getKeys().get(8).label = "9";
        keyboardView.invalidateAllKeys();
        areKeysDisabled = false;
    }

    private void moveFocusRight() {
        if (indexOfFieldHasFocus < 5) {
            editDurationFields.setFocus(indexOfFieldHasFocus + 1);
        }

    }

    private boolean isFocusOndeactivateableField() {
        return indexOfFieldHasFocus == 2 || indexOfFieldHasFocus == 4;
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

    private void moveFocusLeft() {
        if (indexOfFieldHasFocus > 0) {
            editDurationFields.setFocus(indexOfFieldHasFocus - 1);
        }
    }

    private void updateDurationFields(int durationInSec) {
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

    private void setFocusToStart() {
        editDurationFields.setFocus(0);
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
                    callbackActions.onCancel();
                    return;
                case -4:
                    saveDuration();
                    callbackActions.onSave();
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

package de.softinva.multitimer.fragments.editdurationdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.databinding.EditDurationDialogBinding;

public class EditDurationDialog extends AppDialogFragmentDataBinding<EditDurationDialogViewModel> {


    @Override
    protected void setKeyboard() {
        Keyboard keyboard = new Keyboard(getContext(), R.xml.keyboard_09);
        KeyboardView keyboardView = ((EditDurationDialogBinding) binding).keyboardView;
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setEnabled(true);
        // ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);*/
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
        return DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.edit_duration_dialog, null, false);

    }

    @Override
    protected void setClassSpecificObjects() {

    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            //Here check the primaryCode to see which key is pressed
            //based on the android:codes property
            if (primaryCode == 1) {
                //    logger.info("Key", "You just pressed 1 button");
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

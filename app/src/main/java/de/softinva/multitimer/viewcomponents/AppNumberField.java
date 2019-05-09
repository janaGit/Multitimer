package de.softinva.multitimer.viewcomponents;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AppNumberField extends TextInputLayout {
    TextInputEditText textField;

    public AppNumberField(Context context) {
        super(context);
        textField = new TextInputEditText(context);
        textField.setEnabled(false);
        addView(textField);

    }

    public TextInputEditText getTextField() {
        return textField;
    }

    public TextInputLayout getLayout() {
        return this;
    }
}

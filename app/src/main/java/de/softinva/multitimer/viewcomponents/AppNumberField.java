package de.softinva.multitimer.viewcomponents;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static android.text.InputType.TYPE_NULL;


public class AppNumberField extends TextInputLayout {
    TextInputEditText textField;

    public AppNumberField(Context context) {
        super(context);
        textField = new TextInputEditText(context);
        textField.setInputType(TYPE_NULL);
        addView(textField);

    }

    public TextInputEditText getTextField() {
        return textField;
    }

    public void setTextHint(String hint) {
        setHint(hint);
        getTextField().setHint(hint);
    }


}

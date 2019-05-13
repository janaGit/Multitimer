package de.softinva.multitimer.viewcomponents;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class EditDurationFields extends LinearLayout {
    AppNumberField[] appNumberFields = new AppNumberField[6];
    EditDurationFieldsFocusChangeListener callback;


    public EditDurationFields(Object parent, Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFields(context);
        setCallBackListener(parent);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        callback = null;
    }

    protected void setCallBackListener(Object parent) {
        if (parent instanceof EditDurationFieldsFocusChangeListener) {
            callback = (EditDurationFieldsFocusChangeListener) parent;
        } else {
            throw new RuntimeException(parent.toString()
                    + " must implement EditDurationFieldsFocusChangeListener");
        }
    }

    public void setNumber(int indexField, int number) {
        appNumberFields[indexField].getTextField().setText(number + "");
    }

    public int getNumber(int indexField) {
        return Integer.parseInt(appNumberFields[indexField].getTextField().getText().toString());
    }

    public void setFocus(int indexField) {
        appNumberFields[indexField].requestFocus();
    }

    protected void setFields(Context context) {
        appNumberFields[0] = new AppNumberField(context);
        appNumberFields[1] = new AppNumberField(context);
        TextView textView1 = new TextView(context);
        appNumberFields[2] = new AppNumberField(context);
        appNumberFields[3] = new AppNumberField(context);
        TextView textView2 = new TextView(context);
        appNumberFields[4] = new AppNumberField(context);
        appNumberFields[5] = new AppNumberField(context);

        appNumberFields[0].setTextHint("h");
        appNumberFields[0].getTextField().addTextChangedListener(new EditDurationTextListener());
        appNumberFields[0].getTextField().setOnFocusChangeListener(new EditDurationFocusChangeListener(0));
        addView(appNumberFields[0]);


        appNumberFields[1].setTextHint("h");
        appNumberFields[1].getTextField().addTextChangedListener(new EditDurationTextListener());
        appNumberFields[1].getTextField().setOnFocusChangeListener(new EditDurationFocusChangeListener(1));
        addView(appNumberFields[1]);

        textView1.setText(":");
        addView(textView1);

        appNumberFields[2].setTextHint("m");
        appNumberFields[2].getTextField().addTextChangedListener(new EditDurationTextListener());
        appNumberFields[2].getTextField().setOnFocusChangeListener(new EditDurationFocusChangeListener(2));
        addView(appNumberFields[2]);

        appNumberFields[3].setTextHint("m");
        appNumberFields[3].getTextField().addTextChangedListener(new EditDurationTextListener());
        appNumberFields[3].getTextField().setOnFocusChangeListener(new EditDurationFocusChangeListener(3));
        addView(appNumberFields[3]);

        textView2.setText(":");
        addView(textView2);

        appNumberFields[4].setTextHint("s");
        appNumberFields[4].getTextField().addTextChangedListener(new EditDurationTextListener());
        appNumberFields[4].getTextField().setOnFocusChangeListener(new EditDurationFocusChangeListener(4));
        addView(appNumberFields[4]);

        appNumberFields[5].setTextHint("s");
        appNumberFields[5].getTextField().addTextChangedListener(new EditDurationTextListener());
        appNumberFields[5].getTextField().setOnFocusChangeListener(new EditDurationFocusChangeListener(5));
        addView(appNumberFields[5]);
    }

    public interface EditDurationFieldsFocusChangeListener {
        void onHasFocus(int indexField);
    }

    class EditDurationFocusChangeListener implements OnFocusChangeListener {
        int indexField;

        EditDurationFocusChangeListener(int indexField) {
            this.indexField = indexField;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            callback.onHasFocus(indexField);
        }
    }

    class EditDurationTextListener implements TextWatcher {
        int start;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.start = start;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 1) {
                if (start == 1) {
                    s.delete(0, 1);
                } else {
                    s.delete(1, 2);
                }

            }
        }
    }
}




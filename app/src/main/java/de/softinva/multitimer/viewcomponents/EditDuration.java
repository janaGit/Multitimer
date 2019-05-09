package de.softinva.multitimer.viewcomponents;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class EditDuration extends LinearLayout {


    public EditDuration(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.BOTTOM);
        setFields(context);
    }

    public void updateDuration(int durationInSec) {

    }

    protected void setFields(Context context) {
        AppNumberField field1 = new AppNumberField(context);
        AppNumberField field2 = new AppNumberField(context);
        TextView textView1 = new TextView(context);
        AppNumberField field3 = new AppNumberField(context);
        AppNumberField field4 = new AppNumberField(context);
        TextView textView2 = new TextView(context);
        AppNumberField field5 = new AppNumberField(context);
        AppNumberField field6 = new AppNumberField(context);

        field1.getLayout().setHint("h");
        field1.getTextField().setHint("h");
        field1.getTextField().addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    field2.requestFocus();
                }

            }
        });
        addView(field1);


        field2.getLayout().setHint("h");
        field2.getTextField().setHint("h");
        field2.getTextField().addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    field3.requestFocus();
                }
            }
        });
        addView(field2);

        textView1.setText(":");
        addView(textView1);

        field3.getLayout().setHint("m");
        field3.getTextField().setHint("m");
        field3.getTextField().addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    field4.requestFocus();
                }
            }
        });
        addView(field3);

        field4.getLayout().setHint("m");
        field4.getTextField().setHint("m");
        field4.getTextField().addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    field5.requestFocus();
                }
            }
        });
        addView(field4);

        textView2.setText(":");
        addView(textView2);

        field5.getLayout().setHint("s");
        field5.getTextField().setHint("s");
        field5.getTextField().addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    field6.requestFocus();
                }

            }
        });
        addView(field5);

        field6.getLayout().setHint("s");
        field6.getTextField().setHint("s");
        field6.getTextField().addTextChangedListener(new EditDurationTextListener());
        addView(field6);
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




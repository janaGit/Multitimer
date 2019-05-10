package de.softinva.multitimer.viewcomponents;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class EditDurationFields extends LinearLayout {
    AppNumberField field1;
    AppNumberField field2;
    AppNumberField field3;
    AppNumberField field4;
    AppNumberField field5;
    AppNumberField field6;

    public EditDurationFields(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFields(context);
    }

    protected void setFields(Context context) {
        field1 = new AppNumberField(context);
        field2 = new AppNumberField(context);
        TextView textView1 = new TextView(context);
        field3 = new AppNumberField(context);
        field4 = new AppNumberField(context);
        TextView textView2 = new TextView(context);
        field5 = new AppNumberField(context);
        field6 = new AppNumberField(context);

        field1.setTextHint("h");
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


        field2.setTextHint("h");
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

        field3.setTextHint("m");
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

        field4.setTextHint("m");
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

        field5.setTextHint("s");
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

        field6.setTextHint("s");
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




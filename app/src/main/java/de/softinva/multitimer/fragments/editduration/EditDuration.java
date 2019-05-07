package de.softinva.multitimer.fragments.editduration;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.SingleLineTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AppFragmentDataBinding;
import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.databinding.EditDurationBinding;


public class EditDuration extends AppFragmentDataBinding<EditDurationViewModel> {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditDurationBinding editDurationBinding = (EditDurationBinding) binding;
        editDurationBinding.hours1.layout.setHint("h");
        editDurationBinding.hours1.field.setHint("h");
        editDurationBinding.hours1.field.addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    editDurationBinding.hours2.field.requestFocus();
                }

            }
        });

        editDurationBinding.hours2.layout.setHint("h");
        editDurationBinding.hours2.field.setHint("h");
        editDurationBinding.hours2.field.addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    editDurationBinding.minutes1.field.requestFocus();
                }
            }
        });

        editDurationBinding.minutes1.layout.setHint("m");
        editDurationBinding.minutes1.field.setHint("m");
        editDurationBinding.minutes1.field.addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    editDurationBinding.minutes2.field.requestFocus();
                }
            }
        });

        editDurationBinding.minutes2.layout.setHint("m");
        editDurationBinding.minutes2.field.setHint("m");
        editDurationBinding.minutes2.field.addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    editDurationBinding.seconds1.field.requestFocus();
                }
            }
        });

        editDurationBinding.seconds1.layout.setHint("s");
        editDurationBinding.seconds1.field.setHint("s");
        editDurationBinding.seconds1.field.addTextChangedListener(new EditDurationTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!s.toString().equals("")) {
                    editDurationBinding.seconds2.field.requestFocus();
                }

            }
        });

        editDurationBinding.seconds2.layout.setHint("s");
        editDurationBinding.seconds2.field.setHint("s");
        editDurationBinding.seconds2.field.addTextChangedListener(new EditDurationTextListener());

    }


    @Override
    protected AppViewObject setViewObject() {
        return null;
    }

    @Override
    protected EditDurationViewModel setModel() {
        return new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(EditDurationViewModel.class);
    }

    @Override
    protected ViewDataBinding setBinding(ViewGroup container) {
        return DataBindingUtil.inflate(getLayoutInflater(), R.layout.edit_duration, container, false);

    }


    @Override
    protected void setClassSpecificObjects() {

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

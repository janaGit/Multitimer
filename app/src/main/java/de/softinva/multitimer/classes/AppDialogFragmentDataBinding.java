package de.softinva.multitimer.classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppDialogFragmentDataBinding<T> extends DialogFragment {
    protected AppLogger logger = new AppLogger(this);
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    public AppDialogFragmentDataBinding() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        model = setModel();
        setClassSpecificObjects();
        viewObject = setViewObject();
        setContextForViewObject();
        binding = setBinding();
        bindModel();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        return builder.create();
    }

    protected abstract AppViewObject setViewObject();

    protected void setContextForViewObject() {
        if (viewObject != null) {
            viewObject.setContext(this);
        }

    }


    protected void bindModel() {
        if (viewObject != null) {
            binding.setVariable(BR.viewObject, viewObject);
            binding.setLifecycleOwner(this);
        }

    }

    public ViewDataBinding getBinding() {
        return binding;
    }
    protected abstract T setModel();

    protected abstract ViewDataBinding setBinding();


    protected abstract void setClassSpecificObjects();
}

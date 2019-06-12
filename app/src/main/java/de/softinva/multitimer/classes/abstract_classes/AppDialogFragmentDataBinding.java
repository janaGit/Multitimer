package de.softinva.multitimer.classes.abstract_classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.classes.interfaces.IAppModelBinding;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppDialogFragmentDataBinding<T> extends DialogFragment implements IAppModelBinding<T> {
    protected AppLogger logger = new AppLogger(this);
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    public AppDialogFragmentDataBinding() {
        super();
    }

    @Override
    public T getModel() {
        return model;
    }


    @Override
    public ViewDataBinding getBinding() {
        return binding;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
        }

    }

    protected void bindModel() {
        if (viewObject != null) {
            binding.setVariable(BR.viewObject, viewObject);
            binding.setLifecycleOwner(this);
        }

    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    protected abstract T setModel();

    protected abstract ViewDataBinding setBinding();


    protected abstract void setClassSpecificObjects();
}

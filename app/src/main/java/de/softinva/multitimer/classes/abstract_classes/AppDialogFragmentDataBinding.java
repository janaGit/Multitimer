package de.softinva.multitimer.classes.abstract_classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.classes.interfaces.IAppModelBinding;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppDialogFragmentDataBinding<T extends ViewModel> extends DialogFragment implements IAppModelBinding<T> {
    protected AppLogger logger = new AppLogger(this);
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    public AppDialogFragmentDataBinding() {
        super();
    }

    public void showDialog(String tag) {
        show(getActivity().getSupportFragmentManager(), tag);
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

        model = new ViewModelProvider(this, new SavedStateViewModelFactory(this))
                .get(returnModelClass());
        setClassSpecificObjects();
        viewObject = setViewObject();
        binding = setBinding();
        bindModel();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        return builder.create();
    }

    protected abstract Class<T> returnModelClass();

    protected abstract AppViewObject setViewObject();


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

    protected abstract ViewDataBinding setBinding();


    protected abstract void setClassSpecificObjects();
}

package de.softinva.multitimer.classes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppFragmentDataBinding<T> extends AppFragment implements IAppModelBinding<T> {
    protected AppLogger logger = new AppLogger(this);
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    public AppFragmentDataBinding() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (binding != null) {
            ViewGroup view = (ViewGroup) binding.getRoot();
            if (view.getParent() != null) {
                ((ViewGroup) binding.getRoot().getParent()).removeView(view);
            }
        }
        if (model == null) {
            model = setModel();
            setClassSpecificObjects();
            viewObject = setViewObject();
            setContextForViewObject();
            binding = setBinding();
            bindModel();
        }


        return binding.getRoot();
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

    protected abstract T setModel();

    protected abstract ViewDataBinding setBinding();

    protected abstract void setClassSpecificObjects();
}

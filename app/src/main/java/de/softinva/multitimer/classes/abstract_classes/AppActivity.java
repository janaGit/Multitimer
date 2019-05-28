package de.softinva.multitimer.classes.abstract_classes;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.classes.interfaces.IAppModelBinding;


public abstract class AppActivity<T> extends AppCompatActivity implements IAppModelBinding<T> {
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel();
        setClassSpecificObjects();
        setViewObject();
        setContextForViewObject();
        setBinding();
        bindModel();

        setActionBar();
        setHomeUpButton();
        setTitle();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public T getModel() {
        return model;
    }

    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public ViewDataBinding getBinding() {
        return binding;
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    protected abstract void setViewObject();

    protected void setContextForViewObject() {
        viewObject.setContext(this);
    }

    protected void setHomeUpButton() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    protected void bindModel() {
        if (viewObject != null) {
            binding.setVariable(BR.viewObject, viewObject);
            binding.setLifecycleOwner(this);
        }

    }

    protected abstract void setModel();

    protected abstract void setActionBar();

    protected abstract void setBinding();

    protected abstract void setTitle();

    protected abstract void setClassSpecificObjects();
}

package de.softinva.multitimer.classes.abstract_classes;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.classes.interfaces.IAppModelBinding;


public abstract class AppActivity<T extends ViewModel> extends AppCompatActivity implements IAppModelBinding<T> {
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, new SavedStateViewModelFactory(this))
                .get(returnModelClass());
        setClassSpecificObjects();
        viewObject = returnViewObject();
        binding = returnBinding();
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
        return getApplicationContext();
    }

    @Override
    public ViewDataBinding getBinding() {
        return binding;
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    protected abstract AppViewObject returnViewObject();


    protected void setHomeUpButton() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void bindModel() {
        if (viewObject != null) {
            binding.setVariable(BR.viewObject, viewObject);
            binding.setLifecycleOwner(this);
        }

    }

    protected abstract Class<T> returnModelClass();

    protected abstract void setActionBar();

    protected abstract ViewDataBinding returnBinding();

    protected abstract void setTitle();

    protected abstract void setClassSpecificObjects();
}

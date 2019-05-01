package de.softinva.multitimer.classes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import de.softinva.multitimer.BR;


public abstract class AppActivity<T> extends AppCompatActivity {
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel();
        setClassSpecificObjects();
        setViewObject();
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

    protected void setViewObject() {
        ((AppViewModel) model).setViewObject();
        viewObject = ((AppViewModel) model).getViewObject();
        viewObject.setContext(this);
    }

    protected void setHomeUpButton() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    protected void bindModel() {
        if (viewObject != null) {
            binding.setVariable(BR.viewObject, viewObject);
        }

    }

    protected abstract void setModel();

    protected abstract void setActionBar();

    protected abstract void setBinding();

    protected abstract void setTitle();

    protected abstract void setClassSpecificObjects();
}

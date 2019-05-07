package de.softinva.multitimer.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppFragmentDataBinding<T> extends Fragment {
    protected AppLogger logger = new AppLogger(this);
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    public AppFragmentDataBinding() {
        super();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = setModel();
        setClassSpecificObjects();
        viewObject = setViewObject();
        setContextForViewObject();
        binding = setBinding(container);
        bindModel();
        return binding.getRoot();

    }


    protected abstract AppViewObject setViewObject();

    protected void setContextForViewObject() {
        if(viewObject!=null){
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

    protected abstract ViewDataBinding setBinding(ViewGroup container);

    protected abstract void setClassSpecificObjects();
}

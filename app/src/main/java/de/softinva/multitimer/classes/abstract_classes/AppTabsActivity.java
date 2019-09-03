package de.softinva.multitimer.classes.abstract_classes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;

public abstract class AppTabsActivity<T extends ViewModel> extends AppActivity<T> {
    public static final String ACTIVE_TAB = "de.softinva.multitimer.activeTab";
    protected AppLogger logger = new AppLogger(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewIfOrientationLandscape();
    }

    @Override
    protected void setClassSpecificObjects() {
        setActiveTab();
    }

    protected void setViewIfOrientationLandscape() {
        Fragment fragment;
        Context context = getApplicationContext();
        if (UtilityMethods.isOriantationLandscape(context)) {
            if (findViewById(R.id.fragment_container) != null) {
                fragment = selectFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit();
            }

        }
    }

    protected abstract Fragment selectFragment();

    protected abstract void setActiveTab();
}


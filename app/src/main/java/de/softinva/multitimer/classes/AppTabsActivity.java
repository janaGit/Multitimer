package de.softinva.multitimer.classes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppTabsActivity<T> extends AppActivity<T> {
    public static final String  ACTIVE_TAB= "de.softinva.multitimer.activeTab";
    protected AppLogger logger = new AppLogger(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void setClassSpecificObjects() {
        this.intent = getIntent();
        setActiveTab();
        setViewIfOrientationLandscape();
    }
    protected void setViewIfOrientationLandscape() {
        Fragment fragment;
        Context context = getApplicationContext();
        Integer screenOrientation = context.getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
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


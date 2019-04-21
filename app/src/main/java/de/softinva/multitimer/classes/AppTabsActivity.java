package de.softinva.multitimer.classes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppTabsActivity extends AppCompatActivity {
    public static final String  ACTIVE_TAB= "de.softinva.multitimer.activeTab";
    protected AppLogger logger = new AppLogger(this);
    protected ActivityViewModel model;
    protected Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.intent = getIntent();
        setModel();
        setActiveTab();

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
    protected abstract void setModel();
    protected abstract void setActiveTab();

}


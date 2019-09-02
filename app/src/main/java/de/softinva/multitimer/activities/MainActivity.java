package de.softinva.multitimer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.timergroup.add.AddTimerGroupActivity;
import de.softinva.multitimer.classes.abstract_classes.AppTabsActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityMainBinding;
import de.softinva.multitimer.fragments.dialogaddtemptimer.AddTempTimerDialog;
import de.softinva.multitimer.fragments.list.running.RunningTimerList;
import de.softinva.multitimer.fragments.list.temp.TempTimerList;
import de.softinva.multitimer.fragments.list.timergroup.TimerGroupList;
import de.softinva.multitimer.model.MAIN_ACTIVITY_TABS;
import de.softinva.multitimer.utility.OnInstallationManager;


public class MainActivity extends AppTabsActivity<MainActivityViewModel> {
    private AddTempTimerDialog addtempTimerDialog;

    public static void startNewActivity(Context context, boolean clearBackStack) {
        Intent intent = new Intent(context, MainActivity.class);
        if (clearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    public void showAddTempTimerDialog() {
        addtempTimerDialog.show(getSupportFragmentManager(), "addTempTimer");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addtempTimerDialog = new AddTempTimerDialog();
        manageFloatingAddButton();
        new OnInstallationManager(this).executeCodeOnFirstRun();
    }

    @Override
    protected AppViewObject returnViewObject() {
        return new MainActivityViewObject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void setActiveTab() {
        if (model.getActiveTab().getValue() == null) {
            model.getActiveTab().setValue(MAIN_ACTIVITY_TABS.List);
        }
    }

    @Override
    protected void setHomeUpButton() {

    }

    protected void manageFloatingAddButton() {
        model.getActiveTab().observe(this, activeTab -> {
            if (activeTab == MAIN_ACTIVITY_TABS.Temp) {
                ((ActivityMainBinding) binding).floatingActionButton.setVisibility(View.VISIBLE);
            } else {
                ((ActivityMainBinding) binding).floatingActionButton.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    protected Fragment selectFragment() {
        Fragment fragment;

        MAIN_ACTIVITY_TABS tab = (MAIN_ACTIVITY_TABS) model.getActiveTab().getValue();
        switch (tab) {
            case List:
                fragment = new TimerGroupList();
                break;
            case Temp:
                fragment = new TempTimerList();
                break;
            case Running:
                fragment = new RunningTimerList();
                break;
            default:
                throw new Error("active tab is not supported!");
        }
        return fragment;
    }

    @Override
    protected Class<MainActivityViewModel> returnModelClass() {
        return MainActivityViewModel.class;
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityMainBinding) binding).appBar);
    }

    @Override
    protected ViewDataBinding returnBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void setTitle() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_timer_group:
                AddTimerGroupActivity.startNewActivity(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}

package de.softinva.multitimer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.softinva.multitimer.classes.AppTabsActivity;
import de.softinva.multitimer.databinding.ActivityMainBinding;
import de.softinva.multitimer.fragments.list.running.RunningTimerList;
import de.softinva.multitimer.fragments.list.temp.TempTimerList;
import de.softinva.multitimer.fragments.list.timergroup.TimerGroupList;
import de.softinva.multitimer.model.MAIN_ACTIVITY_TABS;

import static androidx.transition.Visibility.MODE_IN;


public class MainActivity extends AppTabsActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.appBar);

        setViewIfOrientationLandscape();
        manageFloatingAddButton();
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

    protected void manageFloatingAddButton() {
      model.getActiveTab().observe( this,activeTab -> {
          if(activeTab == MAIN_ACTIVITY_TABS.Temp ){
              binding.floatingActionButton.setVisibility(View.VISIBLE);
          }else{
              binding.floatingActionButton.setVisibility(View.INVISIBLE);
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
    protected void setModel() {
        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }
}

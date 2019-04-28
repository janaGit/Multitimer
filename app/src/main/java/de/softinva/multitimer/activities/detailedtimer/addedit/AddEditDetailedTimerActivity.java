package de.softinva.multitimer.activities.detailedtimer.addedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AbstractDetailedTimerActivity;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.model.RunningTimer;

public class AddEditDetailedTimerActivity extends AbstractDetailedTimerActivity<AddEditDetailedTimerViewModel> {
    static final String ACTION_EDIT = "de.softinva.multitimer.AddEditDetailedTimerActivity.StartActivityEdit";
    static final String ACTION_ADD = "de.softinva.multitimer.AddEditDetailedTimerActivity.StartActivityAdd";
    protected String action;
    public static void startNewActivityEdit(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, AddEditDetailedTimerActivity.class);
        intent.setAction(AddEditDetailedTimerActivity.ACTION_EDIT);
        intent.putExtra(AddEditDetailedTimerActivity.GROUP_ID, groupId);
        intent.putExtra(AddEditDetailedTimerActivity.TIMER_ID, timerId);
        context.startActivity(intent);
    }
    public static void startNewActivityAdd(String groupId,Context context) {
        Intent intent = new Intent(context, AddEditDetailedTimerActivity.class);
        intent.setAction(AddEditDetailedTimerActivity.ACTION_ADD);
        intent.putExtra(AddEditDetailedTimerActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditDetailedTimerBinding) binding).appBar);
    }
    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddEditDetailedTimerViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }
    @Override
    protected void setClassSpecificObjects() {
        action = getIntent().getAction();
        switch (action) {
            case ACTION_EDIT:
                super.setClassSpecificObjects();
                break;
            case ACTION_ADD:
                String groupId=getIntent().getStringExtra(GROUP_ID);
                if(groupId == null){
                    throw new Error("groupId is null !");
                }
                MutableLiveData<RunningTimer> timer = model.createNewRunningTimer(groupId);
                timer.getValue().getTimer().setTitle(getString(R.string.new_detailed_timer));
                break;
            default:
                throw new Error("Intent with action " + action + " not supported!");
        }
     super.setClassSpecificObjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action == ACTION_EDIT) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.detailed_timer_addedit_menu, menu);
        }
        return true;
    }


}

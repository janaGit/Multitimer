package de.softinva.multitimer.activities.timergroup.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.timergroup.edit.EditTimerGroupActivity;
import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupActivity;
import de.softinva.multitimer.databinding.ActivityTimerGroupInfoBinding;

public class TimerGroupInfoActivity extends AbstractTimerGroupActivity<TimerGroupInfoViewModel> {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, TimerGroupInfoActivity.class);
        intent.putExtra(TimerGroupInfoActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setViewObject() {
        viewObject = new TimerGroupInfoViewObject(timerGroup$);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timer_group_info);
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityTimerGroupInfoBinding) binding).appBar);
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(TimerGroupInfoViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timer_group_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_timer_group:
                EditTimerGroupActivity.startNewActivityEdit(timerGroup$.getValue().getId(), this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package de.softinva.multitimer.activities.detailedtimer.info;

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
import de.softinva.multitimer.activities.detailedtimer.edit.EditDetailedTimerActivity;
import de.softinva.multitimer.classes.AbstractDetailedTimerActivity;
import de.softinva.multitimer.databinding.ActivityDetailedTimerInfoBinding;

public class DetailedTimerInfoActivity extends AbstractDetailedTimerActivity<DetailedTimerInfoViewModel> {
    public static void startNewActivity(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, DetailedTimerInfoActivity.class);
        intent.putExtra(DetailedTimerInfoActivity.GROUP_ID, groupId);
        intent.putExtra(DetailedTimerInfoActivity.TIMER_ID, timerId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_timer_info);
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityDetailedTimerInfoBinding) binding).appBar);
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(DetailedTimerInfoViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_timer_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_detailed_timer:
                EditDetailedTimerActivity.startNewActivity(runningTimer$.getValue().getTimer().getGroupId(), runningTimer$.getValue().getTimer().getId(), this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

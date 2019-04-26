package de.softinva.multitimer.activities.detailedtimer.addedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AbstractDetailedTimerActivity;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;

public class AddEditDetailedTimerActivity extends AbstractDetailedTimerActivity<AddEditDetailedTimerViewModel> {

    public static void startNewActivityEdit(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, AddEditDetailedTimerActivity.class);
        intent.putExtra(AddEditDetailedTimerActivity.GROUP_ID, groupId);
        intent.putExtra(AddEditDetailedTimerActivity.TIMER_ID, timerId);
        context.startActivity(intent);
    }
    public static void startNewActivityAdd(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, AddEditDetailedTimerActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_timer_info_menu, menu);
        return true;
    }


}

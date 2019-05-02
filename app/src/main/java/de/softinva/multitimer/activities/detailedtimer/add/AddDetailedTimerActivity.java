package de.softinva.multitimer.activities.detailedtimer.add;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.classes.AppActivity;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.model.DetailedTimer;

public class AddDetailedTimerActivity extends AppActivity<AddDetailedTimerViewModel> {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, AddDetailedTimerActivity.class);
        intent.putExtra(AddDetailedTimerActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditDetailedTimerBinding) binding).appBar);
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditDetailedTimerViewObject(model.getDetailedTimer());
    }

    @Override
    protected void setHomeUpButton() {

    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddDetailedTimerViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }

    @Override
    protected void setTitle() {
        setTitle(model.detailedTimer.getTitle());
    }

    @Override
    protected void setClassSpecificObjects() {
        createNewDetailedTimer();
    }

    protected DetailedTimer createNewDetailedTimer() {
        DetailedTimer timer = model.createNewDetailedTimer(getIntent().getStringExtra(GROUP_ID));
        timer.setTitle(getString(R.string.new_detailed_timer));
        timer.setDescription(getString(R.string.description));
        return timer;
    }

}

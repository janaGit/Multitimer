package de.softinva.multitimer.activities.detailedtimer.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.TimerGroupActivity;
import de.softinva.multitimer.activities.detailedtimer.AbstractDetailedTimerActivity;
import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.fragments.editcooldowndialog.EditCoolDownDialog;
import de.softinva.multitimer.fragments.editdurationdialog.EditDurationDialog;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;

import static android.text.InputType.TYPE_NULL;

public class EditDetailedTimerActivity extends AbstractDetailedTimerActivity<EditDetailedTimerViewModel> implements EditDurationDialog.UpdateDurationInSecListener, EditCoolDownDialog.UpdateCollDownInSecListener {
    EditDurationDialog editDurationDialog;
    EditCoolDownDialog editCoolDownDialog;

    public static void startNewActivity(String groupId, String timerId, Context context) {
        Intent intent = new Intent(context, EditDetailedTimerActivity.class);
        intent.putExtra(EditDetailedTimerActivity.GROUP_ID, groupId);
        intent.putExtra(EditDetailedTimerActivity.TIMER_ID, timerId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupEditDurationFields();
    }

    protected void setupEditDurationFields() {
        EditText durationField = ((ActivityAddeditDetailedTimerBinding) binding).editDuration;
        durationField.setInputType(TYPE_NULL);
        durationField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((AddEditDetailedTimerViewObject) viewObject).onClickDuration(v);
                }

            }
        });
        EditText coolDownField = ((ActivityAddeditDetailedTimerBinding) binding).editCoolDown;
        coolDownField.setInputType(TYPE_NULL);
        coolDownField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((AddEditDetailedTimerViewObject) viewObject).onClickCoolDown(v);
                }

            }
        });
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditDetailedTimerBinding) binding).appBar);
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditDetailedTimerViewObject(true, model.getDetailedTimer(), editDurationDialog, editCoolDownDialog);
    }

    @Override
    protected void setHomeUpButton() {

    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(EditDetailedTimerViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }

    @Override
    protected void setClassSpecificObjects() {
        super.setClassSpecificObjects();
        runningTimer$.observe(this, runningTimer -> {
            if (model.getDetailedTimer().getId() == null) {
                setDetailedTimer(runningTimer);

            }
        });
        editDurationDialog = new EditDurationDialog();
        editCoolDownDialog = new EditCoolDownDialog();

    }

    protected void setDetailedTimer(RunningTimer runningTimer) {
        Timer timerFromRunning = runningTimer.getTimer();
        if (!(timerFromRunning instanceof DetailedTimer)) {
            throw new Error("Timer should be of instance DetailedTimer but is not!");
        }
        ((DetailedTimer) timerFromRunning).toCopy(model.getDetailedTimer());
        editDurationDialog.setDurationInSec(model.getDetailedTimer().getDurationInSec());
        editCoolDownDialog.setCoolDownInSec(model.getDetailedTimer().getCoolDownInSec());
        binding.invalidateAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_timer_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_detailed_timer:
                TimerRepository repository = new TimerRepository(this.getApplication());
                repository.deleteDetailedTimer(model.detailedTimer);
                TimerGroupActivity.startNewActivity(model.detailedTimer.getGroupId(), this, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void updateDurationInSec(int durationInSec) {
        model.detailedTimer.setDurationInSec(durationInSec);
        editDurationDialog.setDurationInSec(durationInSec);
    }

    @Override
    public void updateCoolDownInSec(int coolDownInSec) {
        model.detailedTimer.setCoolDownInSec(coolDownInSec);
        editCoolDownDialog.setCoolDownInSec(coolDownInSec);
    }
}

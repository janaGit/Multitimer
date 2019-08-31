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
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;


import de.softinva.multitimer.AppBroadcastReceiverImageNameUpdated;
import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.main.timergroup.TimerGroupActivity;
import de.softinva.multitimer.activities.detailedtimer.AbstractDetailedTimerActivity;
import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.activities.selectimage.SelectImageActivity;
import de.softinva.multitimer.activities.takephoto.TakePhotoActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.fragments.dialogeditcooldown.EditCoolDownDialog;
import de.softinva.multitimer.fragments.dialogeditduration.EditDurationDialog;
import de.softinva.multitimer.fragments.dialogimageselection.ACTION_TYPE;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.UtilityMethods;

import static android.text.InputType.TYPE_NULL;
import static de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject.REQUESTCODE_SELECT_IMAGE_ACTIVITY;

public class EditDetailedTimerActivity extends AbstractDetailedTimerActivity<EditDetailedTimerViewModel> implements EditDurationDialog.UpdateDurationInSecListener, EditCoolDownDialog.UpdateCollDownInSecListener, AppBroadcastReceiverImageNameUpdated.UpdateImageName, ImageSelectionDialog.OnClickImageSelectionItem {
    EditDurationDialog editDurationDialog;
    EditCoolDownDialog editCoolDownDialog;
    AppBroadcastReceiverImageNameUpdated broadcastReceiver;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppBroadcastReceiverImageNameUpdated.unregisterReceiver(this, broadcastReceiver);
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
    protected AppViewObject returnViewObject() {
        return new AddEditDetailedTimerViewObject(true, model.getDetailedTimer(), editDurationDialog, editCoolDownDialog);
    }

    @Override
    protected void setHomeUpButton() {

    }

    @Override
    protected EditDetailedTimerViewModel returnModel() {
        return new ViewModelProvider(this, new SavedStateViewModelFactory(this))
                .get(EditDetailedTimerViewModel.class);
    }

    @Override
    protected ViewDataBinding returnBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }

    @Override
    protected void setClassSpecificObjects() {
        super.setClassSpecificObjects();
        runningTimer$.observe(this, runningTimer -> {
            if (model.getDetailedTimer().getId() == null) {
                setDetailedTimer(runningTimer);

            }
        });
        editDurationDialog = new EditDurationDialog(this);
        editCoolDownDialog = new EditCoolDownDialog(this);
        broadcastReceiver = AppBroadcastReceiverImageNameUpdated.registerReceiverForImageNameUpdates(this);

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
                UtilityMethods.deleteImageInAllSizesInInternalFolder(model.detailedTimer.getImageName(), this.getApplicationContext());
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

    @Override
    public void updateImageName(String imageName) {
        model.getDetailedTimer().setImageName(imageName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_SELECT_IMAGE_ACTIVITY) {
            ((AddEditDetailedTimerViewObject) viewObject).setSelectImageActivityIsOpen(false);
        }
    }

    @Override
    public void onClickImageSelectionItem(ACTION_TYPE actionType) {
        switch (actionType) {
            case GALLERY:
                SelectImageActivity.startNewActivityForResult(this, REQUESTCODE_SELECT_IMAGE_ACTIVITY, model.getDetailedTimer().getGroupId(), model.detailedTimer.getId());
                break;
            case CAMERA:
                TakePhotoActivity.startNewActivityForResult(this, REQUESTCODE_SELECT_IMAGE_ACTIVITY, model.getDetailedTimer().getGroupId(), model.detailedTimer.getId());
                ((AddEditDetailedTimerViewObject) viewObject).setSelectImageActivityIsOpen(false);
                break;
            case DEFAULT:
                model.getDetailedTimer().setImageName("");
                ((AddEditDetailedTimerViewObject) viewObject).setSelectImageActivityIsOpen(false);
                break;
            case CANCELED:
                ((AddEditDetailedTimerViewObject) viewObject).setSelectImageActivityIsOpen(false);
                break;
            default:
                throw new Error("ActionType not supported: " + actionType);
        }
    }
}

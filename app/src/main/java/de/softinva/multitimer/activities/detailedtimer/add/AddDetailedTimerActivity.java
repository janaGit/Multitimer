package de.softinva.multitimer.activities.detailedtimer.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.AppBroadcastReceiverImageNameUpdated;
import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.activities.selectimage.SelectImageActivity;
import de.softinva.multitimer.activities.takephoto.TakePhotoActivity;
import de.softinva.multitimer.classes.abstract_classes.AppActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.fragments.dialogeditcooldown.EditCoolDownDialog;
import de.softinva.multitimer.fragments.dialogeditduration.EditDurationDialog;
import de.softinva.multitimer.fragments.dialogimageselection.ACTION_TYPE;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.DetailedTimer;

import static android.text.InputType.TYPE_NULL;
import static de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject.REQUESTCODE_SELECT_IMAGE_ACTIVITY;

public class AddDetailedTimerActivity extends AppActivity<AddDetailedTimerViewModel> implements EditDurationDialog.UpdateDurationInSecListener, EditCoolDownDialog.UpdateCollDownInSecListener, AppBroadcastReceiverImageNameUpdated.UpdateImageName, ImageSelectionDialog.OnClickImageSelectionItem {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";
    EditDurationDialog editDurationDialog;
    EditCoolDownDialog editCoolDownDialog;
    AppBroadcastReceiverImageNameUpdated broadcastReceiver;

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, AddDetailedTimerActivity.class);
        intent.putExtra(AddDetailedTimerActivity.GROUP_ID, groupId);
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
        return new AddEditDetailedTimerViewObject(false, model.getDetailedTimer(), editDurationDialog, editCoolDownDialog, this);
    }

    @Override
    protected void setHomeUpButton() {

    }

    @Override
    protected Class<AddDetailedTimerViewModel> returnModelClass() {
        return AddDetailedTimerViewModel.class;
    }

    @Override
    protected ViewDataBinding returnBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }

    @Override
    protected void setTitle() {
        setTitle(model.detailedTimer.getTitle());
    }

    @Override
    protected void setClassSpecificObjects() {
        createNewDetailedTimer();
        editDurationDialog = new EditDurationDialog(this);
        editCoolDownDialog = new EditCoolDownDialog(this);
        editDurationDialog.setDurationInSec(model.detailedTimer.getDurationInSec());
        editCoolDownDialog.setCoolDownInSec(model.detailedTimer.getCoolDownInSec());
        broadcastReceiver = AppBroadcastReceiverImageNameUpdated.registerReceiverForImageNameUpdates(this);
    }

    protected void createNewDetailedTimer() {
        DetailedTimer timer = model.createNewDetailedTimer(getIntent().getStringExtra(GROUP_ID));
        timer.setTitle(getString(R.string.new_detailed_timer));
        timer.setDescription(getString(R.string.description));
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

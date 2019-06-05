package de.softinva.multitimer.activities.timergroup.add;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.AppBroadcastReceiver;
import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.activities.selectimage.SelectImageActivity;
import de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject;
import de.softinva.multitimer.classes.abstract_classes.AppActivity;
import de.softinva.multitimer.databinding.ActivityAddeditTimerGroupBinding;
import de.softinva.multitimer.fragments.dialogimageselection.ACTION_TYPE;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.TimerGroup;

import static de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject.REQUESTCODE_SELECT_IMAGE_ACTIVITY;

public class AddTimerGroupActivity extends AppActivity<AddTimerGroupViewModel> implements AppBroadcastReceiver.UpdateImageName, ImageSelectionDialog.OnClickImageSelectionItem {
    AppBroadcastReceiver broadcastReceiver;

    public static void startNewActivity(Context context) {
        Intent intent = new Intent(context, AddTimerGroupActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setClassSpecificObjects() {
        TimerGroup timerGroup = model.createNewTimerGroup();
        timerGroup.setTitle(getString(R.string.new_timer_group));
        timerGroup.setDescription(getString(R.string.description));
        broadcastReceiver = AppBroadcastReceiver.registerReceiverForImageNameUpdates(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppBroadcastReceiver.unregisterReceiver(this, broadcastReceiver);
    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddTimerGroupViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_timer_group);
    }

    @Override
    protected void setTitle() {
        setTitle(model.timerGroup.getTitle());
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditTimerGroupBinding) binding).appBar);
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditTimerGroupViewObject(false, model.getTimerGroup());
    }

    @Override
    protected void setHomeUpButton() {

    }


    @Override
    public void updateImageName(String imageName) {
        model.getTimerGroup().setImageName(imageName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_SELECT_IMAGE_ACTIVITY) {
            ((AddEditTimerGroupViewObject) viewObject).setSelectImageActivityIsOpen(false);
        }
    }

    @Override
    public void onClickImageSelectionItem(ACTION_TYPE actionType) {
        switch (actionType) {
            case GALLERY:
                SelectImageActivity.startNewActivityForResult(this, REQUESTCODE_SELECT_IMAGE_ACTIVITY, model.getTimerGroup().getId());
                break;
            case CAMERA:
                ((AddEditTimerGroupViewObject) viewObject).setSelectImageActivityIsOpen(false);
                break;
            case DEFAULT:
                model.getTimerGroup().setImageName("");
                ((AddEditTimerGroupViewObject) viewObject).setSelectImageActivityIsOpen(false);
                break;
            case CANCELED:
                ((AddEditTimerGroupViewObject) viewObject).setSelectImageActivityIsOpen(false);
                break;
            default:
                throw new Error("ActionType not supported: " + actionType);
        }
    }
}

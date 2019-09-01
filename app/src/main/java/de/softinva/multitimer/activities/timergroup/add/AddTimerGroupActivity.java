package de.softinva.multitimer.activities.timergroup.add;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import de.softinva.multitimer.AppBroadcastReceiverImageNameUpdated;
import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.selectimage.SelectImageActivity;
import de.softinva.multitimer.activities.takephoto.TakePhotoActivity;
import de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject;
import de.softinva.multitimer.classes.abstract_classes.AppActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityAddeditTimerGroupBinding;
import de.softinva.multitimer.fragments.dialogimageselection.ACTION_TYPE;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.TimerGroup;

import static de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject.REQUESTCODE_SELECT_IMAGE_ACTIVITY;

public class AddTimerGroupActivity extends AppActivity<AddTimerGroupViewModel> implements AppBroadcastReceiverImageNameUpdated.UpdateImageName, ImageSelectionDialog.OnClickImageSelectionItem {
    AppBroadcastReceiverImageNameUpdated broadcastReceiver;

    public static void startNewActivity(Context context) {
        Intent intent = new Intent(context, AddTimerGroupActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setClassSpecificObjects() {
        TimerGroup timerGroup = model.createNewTimerGroup();
        timerGroup.setTitle(getString(R.string.new_timer_group));
        timerGroup.setDescription(getString(R.string.description));
        broadcastReceiver = AppBroadcastReceiverImageNameUpdated.registerReceiverForImageNameUpdates(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppBroadcastReceiverImageNameUpdated.unregisterReceiver(this, broadcastReceiver);
    }

    @Override
    protected Class<AddTimerGroupViewModel> returnModelClass() {
        return AddTimerGroupViewModel.class;
    }

    @Override
    protected ViewDataBinding returnBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_addedit_timer_group);
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
    protected AppViewObject returnViewObject() {
        return new AddEditTimerGroupViewObject(false, model.getTimerGroup(), this);
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
                TakePhotoActivity.startNewActivityForResult(this, REQUESTCODE_SELECT_IMAGE_ACTIVITY, model.getTimerGroup().getId());
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

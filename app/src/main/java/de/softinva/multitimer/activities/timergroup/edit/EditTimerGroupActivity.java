package de.softinva.multitimer.activities.timergroup.edit;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;


import de.softinva.multitimer.AppBroadcastReceiverImageNameUpdated;
import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.MainActivity;
import de.softinva.multitimer.activities.selectimage.SelectImageActivity;
import de.softinva.multitimer.activities.takephoto.TakePhotoActivity;
import de.softinva.multitimer.activities.timergroup.AbstractTimerGroupActivity;
import de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityAddeditTimerGroupBinding;
import de.softinva.multitimer.fragments.dialogimageselection.ACTION_TYPE;
import de.softinva.multitimer.fragments.dialogimageselection.ImageSelectionDialog;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

import static de.softinva.multitimer.activities.timergroup.AddEditTimerGroupViewObject.REQUESTCODE_SELECT_IMAGE_ACTIVITY;

public class EditTimerGroupActivity extends AbstractTimerGroupActivity<EditTimerGroupViewModel> implements AppBroadcastReceiverImageNameUpdated.UpdateImageName, ImageSelectionDialog.OnClickImageSelectionItem {
    AppBroadcastReceiverImageNameUpdated broadcastReceiver;

    public static void startNewActivityEdit(String groupId, Context context) {
        Intent intent = new Intent(context, EditTimerGroupActivity.class);
        intent.putExtra(EditTimerGroupActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppBroadcastReceiverImageNameUpdated.unregisterReceiver(this, broadcastReceiver);
    }

    @Override
    protected EditTimerGroupViewModel returnModel() {
        return new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(EditTimerGroupViewModel.class);
    }

    @Override
    protected ViewDataBinding returnBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_addedit_timer_group);
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditTimerGroupBinding) binding).appBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timer_group_edit_menu, menu);
        return true;
    }

    @Override
    protected void setClassSpecificObjects() {
        super.setClassSpecificObjects();
        timerGroup$.observe(this, timerGroup -> {
            if (model.getTimerGroup().getId() == null) {
                setTimerGroup(timerGroup);
            }
        });
        broadcastReceiver = AppBroadcastReceiverImageNameUpdated.registerReceiverForImageNameUpdates(this);
    }

    protected void setTimerGroup(TimerGroup timerGroup) {
        timerGroup.toCopy(model.getTimerGroup());
    }

    @Override
    protected AppViewObject returnViewObject() {
        return new AddEditTimerGroupViewObject(true, model.getTimerGroup());
    }

    @Override
    protected void setHomeUpButton() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_timer_group:
                new TimerRepository(this.getApplication()).deleteTimerGroup(model.timerGroup);
                MainActivity.startNewActivity(this, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

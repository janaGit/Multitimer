package de.softinva.multitimer.activities.selectimage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.classes.abstract_classes.AppActivity;


public class SelectImageActivity extends AppActivity<SelectImageViewModel> {
    public static final String TIMER_GROUP_ID = "SelectImageActivity.TimerGroupId";
    public static final String TIMER_ID = "SelectImageActivity.TimerId";

    public static final String ACTION_SELECT_IMAGE_FOR_DETAILED_TIMER = "SelectImageActivity.TimerId.ActionSelectIamgeForDetailedTimer";
    public static final String ACTION_SELECT_IMAGE_FOR_TIMER_GROUP = "SelectImageActivity.TimerId.ActionSelectImageForTimerGroup";

    static final int REQUEST_IMAGE_OPEN = 1;

    public static void startNewActivity(Context context, String timerGroupId, String timerId) {
        Intent intent = new Intent(context, SelectImageActivity.class);
        intent.putExtra(TIMER_GROUP_ID, timerGroupId);
        intent.putExtra(TIMER_ID, timerId);
        intent.setAction(ACTION_SELECT_IMAGE_FOR_DETAILED_TIMER);
        context.startActivity(intent);
    }

    public static void startNewActivity(Context context, String timerGroupId) {
        Intent intent = new Intent(context, SelectImageActivity.class);
        intent.putExtra(TIMER_GROUP_ID, timerGroupId);
        intent.setAction(ACTION_SELECT_IMAGE_FOR_TIMER_GROUP);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectImage();
    }


    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(SelectImageViewModel.class);
    }


    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();


            finish();
        }
    }

    @Override
    protected void setClassSpecificObjects() {
        setGroupId();
        setTimerId();
    }

    protected void setGroupId() {
        String groupId = model.getTimerGroupId().getValue();

        if (groupId == null) {

            groupId = getIntent().getStringExtra(TIMER_GROUP_ID);

            if (groupId != null) {
                model.getTimerGroupId().setValue(groupId);
            } else {
                throw new Error("groupId is null !");
            }
        }
    }

    protected void setTimerId() {
        String timerId = model.getTimerId().getValue();

        if (timerId == null) {

            timerId = getIntent().getStringExtra(TIMER_ID);

            if (timerId != null) {
                model.getTimerId().setValue(timerId);
            } else {
                throw new Error("timerId is null !");
            }
        }
    }

    @Override
    protected void setViewObject() {

    }

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setBinding() {

    }

    @Override
    protected void setTitle() {

    }


}

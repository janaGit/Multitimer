package de.softinva.multitimer.activities.selectimage;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;

import android.os.Build;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;



import de.softinva.multitimer.services.CopyBitmapService;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;


public class SelectImageActivity extends AppCompatActivity {
    public static final String TIMER_GROUP_ID = "SelectImageActivity.TimerGroupId";
    public static final String TIMER_ID = "SelectImageActivity.TimerId";

    public static final String ACTION_SELECT_IMAGE_FOR_DETAILED_TIMER = "SelectImageActivity.ActionSelectIamgeForDetailedTimer";
    public static final String ACTION_SELECT_IMAGE_FOR_TIMER_GROUP = "SelectImageActivity.ActionSelectImageForTimerGroup";
    private SelectImageViewModel model;
    private AppLogger logger = new AppLogger(this);
    static final int REQUEST_SELECT_IMAGE = 1;

    public static void startNewActivityForResult(Activity activity, Integer requestCode, String timerGroupId, String timerId) {
        Intent intent = new Intent(activity, SelectImageActivity.class);
        intent.putExtra(TIMER_GROUP_ID, timerGroupId);
        intent.putExtra(TIMER_ID, timerId);
        intent.setAction(ACTION_SELECT_IMAGE_FOR_DETAILED_TIMER);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startNewActivityForResult(Activity activity, Integer requestCode, String timerGroupId) {
        Intent intent = new Intent(activity, SelectImageActivity.class);
        intent.putExtra(TIMER_GROUP_ID, timerGroupId);
        intent.setAction(ACTION_SELECT_IMAGE_FOR_TIMER_GROUP);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel();
        setClassSpecificObjects();
        selectImage();
    }

    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateViewModelFactory(this))
                .get(SelectImageViewModel.class);
    }


    public void selectImage() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);

        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            String imageFileName;
            if (getIntent().getAction().equals(ACTION_SELECT_IMAGE_FOR_DETAILED_TIMER)) {
                imageFileName = UtilityMethods.createNameForImage(model.getTimerGroupId().getValue(), model.getTimerId().getValue());
            } else {
                imageFileName = UtilityMethods.createNameForImage(model.getTimerGroupId().getValue());
            }

            CopyBitmapService.startImageBitmapService(fullPhotoUri, imageFileName, this);
        }

        finish();
    }


    protected void setClassSpecificObjects() {
        setGroupId();
        if (getIntent().getAction().equals(ACTION_SELECT_IMAGE_FOR_DETAILED_TIMER)) {
            setTimerId();
        }

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

}

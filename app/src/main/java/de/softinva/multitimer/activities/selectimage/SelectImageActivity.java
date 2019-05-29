package de.softinva.multitimer.activities.selectimage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;


import de.softinva.multitimer.services.imagecreator.ImageCreatorService;


public class SelectImageActivity extends AppCompatActivity {
    public static final String TIMER_GROUP_ID = "SelectImageActivity.TimerGroupId";
    public static final String TIMER_ID = "SelectImageActivity.TimerId";
    public static final String RESULT_IMAGE_NAME = "SelectImageActivity.ImageName";

    public static final String ACTION_SELECT_IMAGE_FOR_DETAILED_TIMER = "SelectImageActivity.TimerId.ActionSelectIamgeForDetailedTimer";
    public static final String ACTION_SELECT_IMAGE_FOR_TIMER_GROUP = "SelectImageActivity.TimerId.ActionSelectImageForTimerGroup";
    private SelectImageViewModel model;
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
        setModel();
        setClassSpecificObjects();
        selectImage();
    }

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

            String imageName = createNameForImage();

            Intent intentImageCreator = new Intent(this, ImageCreatorService.class);
            intentImageCreator.putExtra(ImageCreatorService.INTENT_EXTRA_IMAGE_PATH, fullPhotoUri);
            intentImageCreator.putExtra(ImageCreatorService.INTENT_EXTRA_NEW_IMAGE_NAME, imageName);
            startService(intentImageCreator);

            Intent intent = new Intent();
            intent.putExtra(RESULT_IMAGE_NAME, imageName);
            setResult(RESULT_OK, intent);

        }
        setResult(RESULT_CANCELED);
        finish();
    }

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

    private String createNameForImage() {
        return model.getTimerGroupId().getValue() + "_" + model.getTimerId().getValue();
    }

}

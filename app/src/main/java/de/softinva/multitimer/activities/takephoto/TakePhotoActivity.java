package de.softinva.multitimer.activities.takephoto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.IOException;

import de.softinva.multitimer.BuildConfig;
import de.softinva.multitimer.activities.selectimage.SelectImageViewModel;
import de.softinva.multitimer.services.CopyBitmapService;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;

public class TakePhotoActivity extends AppCompatActivity {
    public static final String TIMER_GROUP_ID = "TakePhotoActivity.TimerGroupId";
    public static final String TIMER_ID = "TakePhotoActivity.TimerId";

    public static final String ACTION_TAKE_PHOTO_FOR_DETAILED_TIMER = "TakePhotoActivity.ActionSelectIamgeForDetailedTimer";
    public static final String ACTION_TAKE_PHOTO_FOR_TIMER_GROUP = "TakePhotoActivity.ActionSelectImageForTimerGroup";
    private AppLogger logger = new AppLogger(this);
    private SelectImageViewModel model;
    private String imageFileName;
    private Uri photoURI;
    private File file;
    static final int REQUEST_TAKE_PHOTO = 1;

    public static void startNewActivityForResult(Activity activity, Integer requestCode, String timerGroupId, String timerId) {
        Intent intent = new Intent(activity, TakePhotoActivity.class);
        intent.putExtra(TIMER_GROUP_ID, timerGroupId);
        intent.putExtra(TIMER_ID, timerId);
        intent.setAction(ACTION_TAKE_PHOTO_FOR_DETAILED_TIMER);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startNewActivityForResult(Activity activity, Integer requestCode, String timerGroupId) {
        Intent intent = new Intent(activity, TakePhotoActivity.class);
        intent.putExtra(TIMER_GROUP_ID, timerGroupId);
        intent.setAction(ACTION_TAKE_PHOTO_FOR_TIMER_GROUP);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel();
        setClassSpecificObjects();
        takePhoto();
    }

    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(SelectImageViewModel.class);
    }


    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            try {
                file = File.createTempFile(
                        imageFileName,
                        ".jpg",
                        storageDir
                );

                photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.FILES_AUTHORITY,
                        file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new Error("No Camera application available!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            CopyBitmapService.startImageBitMapService(photoURI, imageFileName, this);
        }

        finish();
    }


    protected void setClassSpecificObjects() {
        setGroupId();
        if (getIntent().getAction().equals(ACTION_TAKE_PHOTO_FOR_DETAILED_TIMER)) {
            setTimerId();
            imageFileName = UtilityMethods.createNameForImage(model.getTimerGroupId().getValue(), model.getTimerId().getValue());
        } else {
            imageFileName = UtilityMethods.createNameForImage(model.getTimerGroupId().getValue());
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

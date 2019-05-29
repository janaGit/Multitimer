package de.softinva.multitimer.services.imagecreator;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

public class ImageCreatorService extends IntentService {
    public static final String INTENT_EXTRA_IMAGE_PATH = "ImageCreatorService.ImagePath";
    public static final String INTENT_EXTRA_NEW_IMAGE_NAME = "ImageCreatorService.NewImageName";
    Uri imagePath;
    String newImageName;

    public ImageCreatorService() {
        super("ImageCreatorService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        imagePath = intent.getParcelableExtra(INTENT_EXTRA_IMAGE_PATH);
        newImageName = intent.getStringExtra(INTENT_EXTRA_NEW_IMAGE_NAME);


    }
}

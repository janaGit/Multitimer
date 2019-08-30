package de.softinva.multitimer.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;

import de.softinva.multitimer.R;
import de.softinva.multitimer.services.CopyBitmapService;

public class OnInstallationManager {
    Context context;

    public OnInstallationManager(Context context) {
        this.context = context;
    }

    public void executeCodeOnFirstRun() {
        //from https://stackoverflow.com/questions/7144265/execute-android-code-after-installation
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            copyDrawableImagesIntoExternalImageFolder();

            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }
    }

    private void copyDrawableImagesIntoExternalImageFolder() {
        copyDrawableImageIntoExternalImageFolder(R.drawable.nudelsalat, "nudelsalat");
        copyDrawableImageIntoExternalImageFolder(R.drawable.nudeln, "nudeln");
        copyDrawableImageIntoExternalImageFolder(R.drawable.gemuese, "gemuese");
        copyDrawableImageIntoExternalImageFolder(R.drawable.pizza, "pizza");

    }

    private void copyDrawableImageIntoExternalImageFolder(int resId, String imageName) {
        Uri photoUri = createFileToCopy(resId, imageName);
        CopyBitmapService.startImageBitmapService(photoUri, imageName, context);

    }

    private Uri createFileToCopy(int resId, String imageName) {
        //from: https://stackoverflow.com/questions/19566840/get-uri-from-drawable-image?rq=1
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);

        return UtilityMethods.copyJPGToExternalFolder(bm, imageName, context);
    }
}

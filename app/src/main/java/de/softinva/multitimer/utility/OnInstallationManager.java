package de.softinva.multitimer.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.util.HashMap;

import de.softinva.multitimer.R;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.dummy.DummyNudelGericht;
import de.softinva.multitimer.repository.dummy.DummyPizza;
import de.softinva.multitimer.services.CopyBitmapService;

public class OnInstallationManager {
    Context context;
    HashMap<String, Uri> imageNameToUri = new HashMap<>();

    String IMAGE_NUDELSALAT = "nudelsalat";
    String IMAGE_NUDELN = "nudeln";
    String IMAGE_GEMUESE = "gemuese";
    String IMAGE_PIZZA = "pizza";

    public OnInstallationManager(Context context) {
        this.context = context;
    }

    public void executeCodeOnFirstRun() {
        //from https://stackoverflow.com/questions/7144265/execute-android-code-after-installation
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            copyDrawableImagesIntoExternalImageFolder();
            copyImagesForDummyDataIntoInternalFolder();

            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }
    }

    private void copyDrawableImagesIntoExternalImageFolder() {
        copyDrawableImagesIntoExternalImageFolder(R.drawable.nudelsalat, IMAGE_NUDELSALAT);
        copyDrawableImagesIntoExternalImageFolder(R.drawable.nudeln, IMAGE_NUDELN);
        copyDrawableImagesIntoExternalImageFolder(R.drawable.gemuese, IMAGE_GEMUESE);
        copyDrawableImagesIntoExternalImageFolder(R.drawable.pizza, IMAGE_PIZZA);

    }

    private void copyDrawableImagesIntoExternalImageFolder(int resId, String imageName) {
        Uri uri;
        uri = copyImageResourceToExternalFolder(resId, imageName);
        imageNameToUri.put(imageName, uri);
    }

    private void copyImagesForDummyDataIntoInternalFolder() {
        copyImagesForTimerGroupIntoInternalFolder(DummyNudelGericht.TIMER_GROUP, IMAGE_NUDELSALAT);
        copyImagesForDetailedTimerDataIntoInternalFolder(DummyNudelGericht.TIMER_NUDELN, IMAGE_NUDELN);
        copyImagesForDetailedTimerDataIntoInternalFolder(DummyNudelGericht.TIMER_Tomatensoße, IMAGE_GEMUESE);
        copyImagesForTimerGroupIntoInternalFolder(DummyPizza.TIMER_GROUP, IMAGE_PIZZA);
        copyImagesForDetailedTimerDataIntoInternalFolder(DummyPizza.TIMER_PIZZA, IMAGE_PIZZA);
    }

    private void copyImagesForTimerGroupIntoInternalFolder(TimerGroup timerGroup, String imageName) {
        String imageNameForTimerGroup = UtilityMethods.createNameForImage(timerGroup.getId());
        CopyBitmapService.startImageBitmapService(imageNameToUri.get(imageName), imageNameForTimerGroup, context);
    }

    private void copyImagesForDetailedTimerDataIntoInternalFolder(DetailedTimer timer, String imageName) {
        String imageNameForDetailedTimer = UtilityMethods.createNameForImage(timer.getGroupId(), timer.getId());
        CopyBitmapService.startImageBitmapService(imageNameToUri.get(imageName), imageNameForDetailedTimer, context);
    }

    private Uri copyImageResourceToExternalFolder(int resId, String imageName) {
        //from: https://stackoverflow.com/questions/19566840/get-uri-from-drawable-image?rq=1
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);

        return UtilityMethods.copyJPGToExternalFolder(bm, imageName, context);
    }
}

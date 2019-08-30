package de.softinva.multitimer.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.annotation.Nullable;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.softinva.multitimer.AppBroadcastReceiverImageNameUpdated;
import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.BitmapUtilities;
import de.softinva.multitimer.utility.ImageSize;
import de.softinva.multitimer.utility.UtilityMethods;

public class CopyBitmapService extends IntentService {
    public static final String URI = "de.softinva.multitimer.services.CopyBitmapService.uri";
    public static final String BITMAP = "de.softinva.multitimer.services.CopyBitmapService.bitmap";
    public static final String IMAGE_NAME = "de.softinva.multitimer.services.CopyBitmapService.imageName";
    public static final HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
    Bitmap bitmap;
    String imageName;
    AppLogger logger = new AppLogger(this);

    public static void startImageBitmapService(Uri uri, String imageName, Context context) {
        Intent intent = new Intent(context, CopyBitmapService.class);
        intent.putExtra(CopyBitmapService.URI, uri);
        intent.putExtra(CopyBitmapService.IMAGE_NAME, imageName);
        context.startService(intent);
    }

    public static void startImageBitmapService(Bitmap bm, String imageName, Context context) {
        Intent intent = new Intent(context, CopyBitmapService.class);
        CopyBitmapService.bitmapHashMap.put(imageName, bm);
        intent.putExtra(CopyBitmapService.IMAGE_NAME, imageName);
        context.startService(intent);
    }

    public CopyBitmapService() {
        super("CopyBitmap");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        this.imageName = intent.getStringExtra(IMAGE_NAME);

        try {
            setBitmap(intent);
            copyImage();
            createThumbnail();
            createImageNormalSize();
            AppBroadcastReceiverImageNameUpdated.sendImageName(imageName, this);
        } catch (IOException e) {
            logger.error("IO Exception " + e.getMessage(), e);
        }
    }

    private void setBitmap(Intent intent) throws IOException {
        if (intent.getParcelableExtra(URI) == null) {
            bitmap = bitmapHashMap.get(imageName);
            bitmapHashMap.remove(imageName);
        } else {
            Uri uri = intent.getParcelableExtra(URI);
            bitmap = BitmapUtilities.getBitmapFromUri(uri, this.getApplicationContext());
        }
    }

    private void copyImage() throws IOException {
        writeFile(ImageSize.original, null);
    }

    private void createThumbnail() throws IOException {
        int targetW = getResources().getDimensionPixelSize(R.dimen.image_size_thumbnail_width);
        int targetH = getResources().getDimensionPixelSize(R.dimen.image_size_thumbnail_height);

        BitmapFactory.Options bmOptions = adaptBitmapOptions(targetW, targetH);
        writeFile(ImageSize.thumbnail, bmOptions);
    }

    private void createImageNormalSize() throws IOException {
        int targetW = getResources().getDimensionPixelSize(R.dimen.image_size_normal_width);
        int targetH = getResources().getDimensionPixelSize(R.dimen.image_size_normal_height);

        BitmapFactory.Options bmOptions = adaptBitmapOptions(targetW, targetH);
        writeFile(ImageSize.normal, bmOptions);

    }

    private void writeFile(ImageSize imageSize, BitmapFactory.Options bmOptions) throws IOException {
        String fileName = UtilityMethods.returnImageFileName(imageName, imageSize);
        FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

        byte[] bytes = BitmapUtilities.getByteArrayFromBitmap(bitmap);

        Bitmap bitmap;
        if (bmOptions == null) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions);
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);


        outputStream.flush();
        outputStream.close();


    }

    private BitmapFactory.Options adaptBitmapOptions(int targetW, int targetH) throws IOException {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        addBitmapOptionsFromBitmap(bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;


        return bmOptions;
    }

    private void addBitmapOptionsFromBitmap(BitmapFactory.Options bmOptions) {
        byte[] bytes = BitmapUtilities.getByteArrayFromBitmap(bitmap);

        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions);

    }


}

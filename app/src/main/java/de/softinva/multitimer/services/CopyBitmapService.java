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
import java.io.FileOutputStream;
import java.io.IOException;

import de.softinva.multitimer.AppBroadcastReceiverImageNameUpdated;
import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.ImageSize;
import de.softinva.multitimer.utility.UtilityMethods;

public class CopyBitmapService extends IntentService {
    public static final String URI = "de.softinva.multitimer.services.CopyBitmapService.uri";
    public static final String IMAGE_NAME = "de.softinva.multitimer.services.CopyBitmapService.imageName";
    Uri uri;
    String imageName;
    AppLogger logger = new AppLogger(this);

    public static void startImageBitMapService(Uri uri, String imageName, Context context) {
        Intent intent = new Intent(context, CopyBitmapService.class);
        intent.putExtra(CopyBitmapService.URI, uri);
        intent.putExtra(CopyBitmapService.IMAGE_NAME, imageName);
        context.startService(intent);
    }

    public CopyBitmapService() {
        super("CopyBitMap");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        this.uri = intent.getParcelableExtra(URI);
        this.imageName = intent.getStringExtra(IMAGE_NAME);

        try {
            copyImage();
            createThumbnail();
            createImageNormalSize();
            AppBroadcastReceiverImageNameUpdated.sendImageName(imageName, this);
        } catch (IOException e) {
            logger.error("IO Exception " + e.getMessage(), e);
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

        ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "r");

        if (pfd != null) {
            FileDescriptor fd = pfd.getFileDescriptor();
            Bitmap bitmap;
            if (bmOptions == null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fd);
            } else {
                bitmap = BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
            }

            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            } else {
                logger.info("bitmap is null");
            }

            outputStream.flush();
            outputStream.close();
            fd.sync();
            pfd.close();

        } else {
            logger.error("pfd is null!");
        }
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

    private void addBitmapOptionsFromBitmap(BitmapFactory.Options bmOptions) throws IOException {
        ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "r");
        if (pfd == null) {
            throw new Error("pfd is null");
        }
        FileDescriptor fd = pfd.getFileDescriptor();
        BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        fd.sync();
        pfd.close();
    }


}

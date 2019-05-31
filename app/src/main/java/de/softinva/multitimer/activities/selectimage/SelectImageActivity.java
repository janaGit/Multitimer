package de.softinva.multitimer.activities.selectimage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;


import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.ImageSize;
import de.softinva.multitimer.utility.UtilityMethods;


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
            new CopyBitmap(fullPhotoUri, imageName).execute();

            Intent intent = new Intent();
            intent.putExtra(RESULT_IMAGE_NAME, imageName);
            setResult(RESULT_OK, intent);

        }
        setResult(RESULT_CANCELED);
        finish();
    }


    private class CopyBitmap extends AsyncTask<String, Void, Void> {

        Uri uri;
        String imageName;
        FileDescriptor fd;

        public CopyBitmap(Uri uri, String newImageName) {
            this.uri = uri;
            this.imageName = newImageName;
        }

        @Override
        protected Void doInBackground(String... urls) {
            try {
                setFileDescriptor();
                copyImage();
                createThumbnail();
                createImageNormalSize();
            } catch (IOException e) {
                throw new Error("IO Exception");
            }
            return null;
        }

        private void setFileDescriptor() throws FileNotFoundException {
            ParcelFileDescriptor inputImage = getContentResolver().openFileDescriptor(uri, "r");
            fd = inputImage.getFileDescriptor();
        }

        private void copyImage() throws IOException {
            String fileName = UtilityMethods.returnImageFileName(imageName, ImageSize.original);
            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);


            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd);

            Bitmap copy = Bitmap.createBitmap(bitmap);

            copy.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();


        }

        private void createThumbnail() throws IOException {
            String fileName = UtilityMethods.returnImageFileName(imageName, ImageSize.thumbnail);
            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

            int targetW = getResources().getDimensionPixelSize(R.dimen.image_size_thumbnail_width);
            int targetH = getResources().getDimensionPixelSize(R.dimen.image_size_thumbnail_height);

            BitmapFactory.Options bmOptions = adaptBitmapOptions(targetW, targetH);
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }

        private void createImageNormalSize() throws IOException {
            String fileName = UtilityMethods.returnImageFileName(imageName, ImageSize.normal);
            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

            int targetW = getResources().getDimensionPixelSize(R.dimen.image_size_normal_width);
            int targetH = getResources().getDimensionPixelSize(R.dimen.image_size_normal_height);

            BitmapFactory.Options bmOptions = adaptBitmapOptions(targetW, targetH);
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }

        private BitmapFactory.Options adaptBitmapOptions(int targetW, int targetH) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            return bmOptions;
        }
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

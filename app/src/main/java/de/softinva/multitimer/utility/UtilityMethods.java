package de.softinva.multitimer.utility;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import de.softinva.multitimer.R;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.Timer;


public class UtilityMethods {
    public static AppLogger createLogger(Object classObject) {
        return new AppLogger(classObject);
    }

    public static TreeMap<Integer, RunningTimer> createRunningTimerListForDetailedTimer(TreeMap<Integer, DetailedTimer> timerMap) {
        TreeMap<Integer, RunningTimer> treeMap = new TreeMap<>();
        for (Map.Entry<Integer, DetailedTimer> entry : timerMap.entrySet()) {
            treeMap.put(entry.getKey(), new RunningTimer(entry.getValue()));
        }
        return treeMap;
    }

    public static TreeMap<Integer, RunningTimer> createRunningTimerListForTempTimer(TreeMap<Integer, TempTimer> timerMap) {
        TreeMap<Integer, RunningTimer> treeMap = new TreeMap<>();
        for (Map.Entry<Integer, TempTimer> entry : timerMap.entrySet()) {
            treeMap.put(entry.getKey(), new RunningTimer(entry.getValue()));
        }
        return treeMap;
    }

    public static String getFileNameWithoutExtensionFromPath(String path) {
        return getFileNameWithExtensionFromPath(path).split("[.]")[0];
    }

    public static DialogFragment getDialog(String tag, FragmentActivity activity) {
        return (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static boolean isOriantationLandscape(Context context) {
        Integer screenOrientation = context.getResources().getConfiguration().orientation;
        return screenOrientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * From https://stackoverflow.com/questions/33696488/getting-bitmap-from-vector-drawable
     *
     * @param context
     * @param drawableId
     * @return
     */
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId, int widthInPx, int heigthInPx) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap;
        Canvas canvas;


        if (widthInPx == 0 || heigthInPx == 0) {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            bitmap = Bitmap.createBitmap(widthInPx,
                    heigthInPx, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, widthInPx, heigthInPx);
        }
        drawable.draw(canvas);

        return bitmap;
    }

    public static String createDurationAndCollDownString(RunningTimer runningTimer) {
        DetailedTimer detailedTimer = (DetailedTimer) runningTimer.getTimer();
        String string = UtilityMethods.transformSecIntoString(detailedTimer.getDurationInSec());

        if (detailedTimer.getCoolDownInSec() > 0) {
            return string + " (" + UtilityMethods.transformSecIntoString(detailedTimer.getCoolDownInSec()) + ")";
        }

        return string;
    }

    public static String getFileNameWithExtensionFromPath(String path) {
        String[] pathSegments = path.split("/");
        String lastPathSegment;
        if (pathSegments.length > 0) {
            lastPathSegment = pathSegments[pathSegments.length - 1];
        } else {
            lastPathSegment = pathSegments[0];
        }

        return lastPathSegment;
    }

    public static String createID() {
        return UUID.randomUUID().toString();
    }

    public static Uri copyJPGToExternalFolder(Bitmap bm, String imageName, Context context) {
        //from: https://stackoverflow.com/questions/19566840/get-uri-from-drawable-image?rq=1
        String extStorageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File file = new File(extStorageDirectory, imageName + ".jpg");
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(file);
        return uri;
    }

    public static String transformSecIntoString(Integer countDownInSec) {
        if (countDownInSec == null) {
            return "";
        }
        return UtilityMethods.transformSecIntoString(countDownInSec.longValue());
    }

    public static String getStringFromList(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String entry : list) {
            stringBuilder.append(entry).append("\n\n");
        }
        return stringBuilder.toString();
    }

    public static String transformSecIntoString(Long countDownInSec) {
        if (countDownInSec == null) {
            return "";
        }
        AppLogger logger = new AppLogger(new UtilityMethods());
        String countDownAsString = "";
        int hours = countDownInSec.intValue() / 3600;
        int minutes = (countDownInSec.intValue() - (hours * 3600)) / 60;
        int seconds = (countDownInSec.intValue() - (hours * 3600) - (minutes * 60));
        if (hours != 0) {
            countDownAsString += hours + " h";
        }
        if (minutes != 0) {
            countDownAsString += " " + minutes + " min";
        }
        if (seconds != 0) {
            countDownAsString += " " + seconds + " sec";
        }

        logger.info("countDownInSec: " + countDownInSec + " countDownAsString: " + countDownAsString);
        return countDownAsString;
    }

    public static LinkedList<DetailedTimer> getDetailedTimerOfTimerGroupInMap(TreeMap<String, RunningTimer> map, String timerGroupId) {
        LinkedList<DetailedTimer> list = new LinkedList<>();
        for (Map.Entry<String, RunningTimer> entry : map.entrySet()) {
            Timer timer = entry.getValue().getTimer();
            if (timer instanceof DetailedTimer) {
                DetailedTimer detailedTimer = (DetailedTimer) timer;
                if (detailedTimer.getGroupId().equals(timerGroupId)) {
                    list.add(detailedTimer);
                }
            }

        }
        return list;
    }

    public static String returnImageFileName(String imageName, ImageSize size) {
        switch (size) {
            case thumbnail:
                return imageName + "_" + "thumbnail.jpg";
            case normal:
                return imageName + "_" + "normal.jpg";
            case original:
                return imageName + ".jpg";
            default:
                throw new Error("size not supported!");
        }

    }

    public static String createNameForImage(String timerGroupId, String detailedTimerId) {
        return UtilityMethods.createNameForImage(timerGroupId) + "_" + detailedTimerId;
    }

    public static String createNameForImage(String timerGroupId) {
        return "__" + timerGroupId;
    }

    public static Bitmap getBitmap(Context context, String imageName, ImageSize imageSize) {
        Bitmap bitmap = null;
        try {
            FileInputStream fis = context.openFileInput(returnImageFileName(imageName, imageSize));
            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (IOException e) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_launcher);
        }
        return bitmap;
    }

    public static void deleteImageInAllSizesInInternalFolder(String imageName, Context context) {
        context.deleteFile(imageName);

        String nameWithoutExtension = UtilityMethods.getFileNameWithoutExtensionFromPath(imageName);
        for (ImageSize size : ImageSize.values()) {
            String imageNameWithExtension = UtilityMethods.returnImageFileName(nameWithoutExtension, size);
            context.deleteFile(imageNameWithExtension);
        }
    }

    public static void deleteImagesInInternalFolderStartingWithName(String imageName, Context context) {
        File directory = context.getFilesDir();
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().startsWith(imageName + ".") || file.getName().startsWith(imageName + "_");
            }
        });
        for (File file : files) {
            UtilityMethods.deleteImageInAllSizesInInternalFolder(file.getName(), context);
        }
    }

}

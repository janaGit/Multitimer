package de.softinva.multitimer.utility;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import androidx.lifecycle.MutableLiveData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SyncFailedException;
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
        return timerGroupId + "_" + detailedTimerId;
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
                    R.drawable.logo_fertig);
        }
        return bitmap;
    }
}

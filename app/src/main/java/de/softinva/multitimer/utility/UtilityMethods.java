package de.softinva.multitimer.utility;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;

import java.io.FileInputStream;
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


    public static String createID() {
        return UUID.randomUUID().toString();
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
            FileInputStream fis = context.openFileInput(UtilityMethods.returnImageFileName(imageName, imageSize));
            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (IOException e) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.logo_fertig);
        }
        return bitmap;
    }
}

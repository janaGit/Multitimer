package de.softinva.multitimer.utility;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import de.softinva.multitimer.R;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class ImportJSONTimerGroupManager {
    JSONObject json;
    Context context;
    Application application;
    String timerGroupId;
    LinkedList<String> errorMessages;
    LinkedList<String> successMessages;

    public ImportJSONTimerGroupManager(Application application) {
        errorMessages = new LinkedList<String>();
        successMessages = new LinkedList<String>();

        this.application = application;
        this.context = application.getApplicationContext();
    }

    public void insertDataIntoDatabase(JSONObject json, String jsonFileName) {
        this.json = json;

        getTimerGroupId(jsonFileName);
        importTimerGroup();
        importTimer();
    }

    public LinkedList<String> getErrorMesages() {
        return errorMessages;
    }

    public LinkedList<String> getSuccessMessages() {
        return successMessages;
    }

    private void getTimerGroupId(String jsonFileName) {
        try {
            timerGroupId = json.getString(context.getResources().getString(R.string.JSONTimerGroupID));
            successMessages.add(context.getResources().getString(R.string.success_message_json_import_timer_group) + " " + jsonFileName);

        } catch (JSONException e) {
            e.printStackTrace();
            errorMessages.add(context.getResources().getString(R.string.error_message_json_import_timer_group) + " " + jsonFileName);
        }
    }

    private void importTimerGroup() {
        TimerGroup timerGroup = new TimerGroup(timerGroupId);

        timerGroup.setTitle(getString(R.string.JSONTimerGroupTitle, json));
        timerGroup.setDescription(getString(R.string.JSONTimerGroupDescription, json));
        timerGroup.setZipped(getBoolean(R.string.JSONTimerGroupIsZipped, false, json));
        timerGroup.setImageName(timerGroupId);

        new TimerRepository(application).insertTimerGroup(timerGroup);
    }

    private void importTimer() {
        JSONArray jsonArray = getArray(R.string.JSONTimerGroupIDTimerArray);

        new TimerRepository(application).deleteAllDetailedTimerFromTimerGroup(timerGroupId);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String timerId = UtilityMethods.createID();
                DetailedTimer detailedTimer = new DetailedTimer(timerId);
                detailedTimer.setTitle(getString(R.string.JSONDetailedTimerTitle, jsonObject));
                detailedTimer.setDurationInSec(getInt(R.string.JSONDetailedTimerDurationInSec, 0, jsonObject));
                detailedTimer.setGroupId(timerGroupId);
                detailedTimer.setImageName(timerGroupId + "_" + timerId);
                detailedTimer.setDescription(getString(R.string.JSONDetailedTimerDescription, jsonObject));
                detailedTimer.setPositionInGroup(getInt(R.string.JSONDetailedTimerPositionInGroup, jsonArray.length() + i, jsonObject));
                detailedTimer.setCoolDownInSec(getInt(R.string.JSONDetailedTimerCoolDownInSec, 0, jsonObject));
                detailedTimer.setIsEnabled(getBoolean(R.string.JSONDetailedTimerDescription, false, jsonObject));

                new TimerRepository(application).insertDetailedTimer(detailedTimer);
            } catch (JSONException e) {
                e.printStackTrace();
                errorMessages.add(context.getResources().getString(R.string.error_message_json_import_timer) + " " + i);
            }

        }
    }

    private String getString(int resId, JSONObject json) {
        try {
            return json.getString(context.getResources().getString(resId));
        } catch (JSONException e) {
            return "";
        }
    }

    private boolean getBoolean(int resId, boolean _default, JSONObject json) {
        try {
            return json.getBoolean(context.getResources().getString(resId));
        } catch (JSONException e) {
            return _default;
        }
    }

    private int getInt(int resId, int _default, JSONObject json) {
        try {
            return json.getInt(context.getResources().getString(resId));
        } catch (JSONException e) {
            return _default;
        }
    }

    private JSONArray getArray(int resId) {
        try {
            return json.getJSONArray(context.getResources().getString(resId));
        } catch (JSONException e) {
            return new JSONArray();
        }
    }
}

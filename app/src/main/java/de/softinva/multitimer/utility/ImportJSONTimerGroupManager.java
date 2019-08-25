package de.softinva.multitimer.utility;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.softinva.multitimer.R;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class ImportJSONTimerGroupManager {
    JSONObject json;
    Context context;
    Application application;
    String timerGroupId;

    public ImportJSONTimerGroupManager(JSONObject json, Application application) throws JSONException {
        this.application = application;
        this.context = application.getApplicationContext();
        this.json = json;

        timerGroupId = json.getString(context.getResources().getString(R.string.JSONTimerGroupID));

        insertDataIntoDatabase();
    }

    public void insertDataIntoDatabase() throws JSONException {
        TimerGroup timerGroup = new TimerGroup(timerGroupId);

        timerGroup.setTitle(getString(R.string.JSONTimerGroupTitle, json));
        timerGroup.setDescription(getString(R.string.JSONTimerGroupDescription, json));
        timerGroup.setZipped(getBoolean(R.string.JSONTimerGroupIsZipped, false, json));
        timerGroup.setImageName(timerGroupId);

        new TimerRepository(application).insertTimerGroup(timerGroup);


        JSONArray jsonArray = getArray(R.string.JSONTimerGroupIDTimerArray);

        for (int i = 0; i < jsonArray.length(); i++) {
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

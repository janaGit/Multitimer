package de.softinva.multitimer.activities.importtimergroups;

import android.content.Intent;
import android.view.View;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ImportTimerGroupViewObject extends AppViewObject<ImportTimerGroupViewModel> {
    ImportTimerGroupActivity activity;

    public ImportTimerGroupViewObject(ImportTimerGroupViewModel model, ImportTimerGroupActivity activity) {
        super(model);
        this.activity = activity;
    }

    public void onClickSelectFilePath(View view) {
        activity.startActivityToSelectFilePath();
    }

    public void onClickImportData(View view) {
        activity.importData();
    }
}


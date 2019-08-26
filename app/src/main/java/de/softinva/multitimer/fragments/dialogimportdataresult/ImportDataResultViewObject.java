package de.softinva.multitimer.fragments.dialogimportdataresult;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

public class ImportDataResultViewObject extends AppViewObject<ImportDataMessages> {
    AppCompatActivity activity;
    ImportDataResultDialog dialog;

    public ImportDataResultViewObject(ImportDataMessages obj, AppCompatActivity activity, ImportDataResultDialog dialog) {
        super(obj);
        this.activity = activity;
        this.dialog = dialog;
    }
    
}

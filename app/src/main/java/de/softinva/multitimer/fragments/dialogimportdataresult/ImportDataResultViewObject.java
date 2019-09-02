package de.softinva.multitimer.fragments.dialogimportdataresult;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import de.softinva.multitimer.classes.abstract_classes.AppViewObject;

public class ImportDataResultViewObject extends AppViewObject<MutableLiveData<ImportDataMessages>> {
    ImportDataResultDialog dialog;

    public ImportDataResultViewObject(MutableLiveData<ImportDataMessages> obj, AppCompatActivity activity, ImportDataResultDialog dialog) {
        super(obj, activity);
        this.dialog = dialog;
    }

}

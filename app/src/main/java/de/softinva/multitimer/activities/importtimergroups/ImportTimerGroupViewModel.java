package de.softinva.multitimer.activities.importtimergroups;


import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.io.File;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;

public class ImportTimerGroupViewModel extends AppViewModel {
    private MutableLiveData<Uri> zipFilePath$;
    private MutableLiveData<File> zipFile$;

    public ImportTimerGroupViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public MutableLiveData<Uri> getZipFilePath() {
        if (zipFilePath$ == null) {
            zipFilePath$ = state.getLiveData("zipFilePath");
        }
        return zipFilePath$;
    }

    public MutableLiveData<File> getZipFile() {
        if (zipFile$ == null) {
            zipFile$ = state.getLiveData("zipFile");
        }
        return zipFile$;
    }


}

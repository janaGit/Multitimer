package de.softinva.multitimer.activities.importtimergroups;


import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.io.File;
import java.util.LinkedList;

import de.softinva.multitimer.classes.abstract_classes.AppViewModel;
import de.softinva.multitimer.fragments.dialogimportdataresult.ImportDataMessages;

public class ImportTimerGroupViewModel extends AppViewModel {
    private MutableLiveData<Uri> zipFilePath$;
    private MutableLiveData<File> zipFile$;

    private MutableLiveData<ImportDataMessages> messages$ = new MutableLiveData<>();

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

    public MutableLiveData<ImportDataMessages> getMessages() {
        if (messages$ == null) {
            messages$ = state.getLiveData("messages");
        }
        return messages$;
    }


}

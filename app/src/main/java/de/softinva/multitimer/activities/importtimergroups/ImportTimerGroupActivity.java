package de.softinva.multitimer.activities.importtimergroups;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import java.io.File;
import java.util.LinkedList;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityImportTimerGroupsBinding;
import de.softinva.multitimer.fragments.dialogimportdataresult.ImportDataMessages;
import de.softinva.multitimer.fragments.dialogimportdataresult.ImportDataResultDialog;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.ImportDataManager;

public class ImportTimerGroupActivity extends AppActivity<ImportTimerGroupViewModel> {
    private static final int SELECT_FILE_PATH_REQUEST_CODE = 42;
    private AppLogger logger = new AppLogger(this);
    LinkedList<String> errorMessages = new LinkedList<>();
    LinkedList<String> successMessages = new LinkedList<>();

    public static void startNewActivity(Context context) {
        Intent intent = new Intent(context, ImportTimerGroupActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected AppViewObject returnViewObject() {
        return new ImportTimerGroupViewObject(model, this);
    }

    @Override
    protected Class<ImportTimerGroupViewModel> returnModelClass() {
        return ImportTimerGroupViewModel.class;
    }


    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityImportTimerGroupsBinding) binding).appBar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
    }

    @Override
    protected ViewDataBinding returnBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_import_timer_groups);
    }

    @Override
    protected void setTitle() {
        setTitle(getResources().getString(R.string.import_timer_groups_title));
    }

    @Override
    protected void setClassSpecificObjects() {
        setZipFilePathAndFile();

    }

    protected void setZipFilePathAndFile() {
        ImportTimerGroupViewModel viewModel = model;
        File zipFile = viewModel.getZipFile().getValue();
        Uri zipFilePath = viewModel.getZipFilePath().getValue();

        if ((zipFilePath == null || zipFile == null) && getIntent().getData() != null) {
            try {
                zipFilePath = getIntent().getData();
                viewModel.getZipFilePath().setValue(zipFilePath);

                zipFile = new File(getIntent().getData().getPath());
                viewModel.getZipFile().setValue(zipFile);
            } catch (Exception e) {
                throw new Error("zipFilePath error!", e);
            }


        }

    }

    public void startActivityToSelectFilePath() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType("application/zip");

        startActivityForResult(intent, SELECT_FILE_PATH_REQUEST_CODE);
    }

    public void importData() {

        Application application = this.getApplication();
        Observer<Uri> observer = new Observer<Uri>() {
            @Override
            public void onChanged(@Nullable Uri uri) {
                ImportDataManager zipFileManager = new ImportDataManager(uri, application);

                zipFileManager.processZipFile();

                model.getMessages().setValue(new ImportDataMessages(zipFileManager.getErrorMesages(),zipFileManager.getSuccessMessages()));

                ImportDataResultDialog dialog = new ImportDataResultDialog();

                dialog.setMessages(model.getMessages());

                dialog.showDialog("importDataResult");

                model.getZipFilePath().removeObserver(this);
            }
        };
        model.getZipFilePath().observe(this, observer);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == SELECT_FILE_PATH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null && resultData.getData() != null && resultData.getData().getPath() != null) {
                uri = resultData.getData();
                logger.info("Uri to zip file path: " + uri.toString());
                model.getZipFilePath().setValue(uri);
                model.getZipFile().setValue(new File(uri.getPath()));

            }
        }
    }

}

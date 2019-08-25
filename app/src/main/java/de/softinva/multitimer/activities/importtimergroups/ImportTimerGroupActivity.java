package de.softinva.multitimer.activities.importtimergroups;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppActivity;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.ActivityImportTimerGroupsBinding;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.ImportJSONTimerGroupManager;

public class ImportTimerGroupActivity extends AppActivity<ImportTimerGroupViewModel> {
    private static final int SELECT_FILE_PATH_REQUEST_CODE = 42;
    private AppLogger logger = new AppLogger(this);

    @Override
    protected AppViewObject returnViewObject() {
        return new ImportTimerGroupViewObject(model, this);
    }

    @Override
    protected ImportTimerGroupViewModel returnModel() {
        return new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(ImportTimerGroupViewModel.class);
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

        if (zipFilePath == null || zipFile == null) {
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
        Observer<Uri> observer = new Observer<Uri>() {
            @Override
            public void onChanged(@Nullable Uri uri) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);

                    ZipInputStream zipFileInputStream = new ZipInputStream(inputStream);

                    iterateOverEntries(zipFileInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    model.getZipFilePath().removeObserver(this);
                }
                model.getZipFilePath().removeObserver(this);
            }
        };
        model.getZipFilePath().observe(this, observer);
    }

    private void iterateOverEntries(ZipInputStream zipInputStream) throws IOException, JSONException {
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            System.out.println(zipEntry.getName());
            //use entry input stream:
            if (zipEntry.isDirectory()) {

            } else {
                String name = zipEntry.getName();
                if (name.contains(".json")) {
                    String jsonString = readInputStream(zipInputStream);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    ImportJSONTimerGroupManager jsonManager = new ImportJSONTimerGroupManager(jsonObject, this.getApplication());
                    jsonManager.insertDataIntoDatabase();
                }
            }

        }
        zipInputStream.close();

    }

    private String readInputStream(final InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        int read = 0;
        while ((read = inputStream.read(buffer, 0, size)) >= 0) {
            stringBuilder.append(new String(buffer, 0, read));
        }

        String string = stringBuilder.toString();
        string = string.replace("\n", "");

        return string;
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

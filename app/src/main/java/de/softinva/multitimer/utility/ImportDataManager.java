package de.softinva.multitimer.utility;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.softinva.multitimer.R;

public class ImportDataManager {
    JSONObject json;
    Context context;
    Application application;
    Uri uri;
    LinkedList<String> errorMessages;
    LinkedList<String> successMessages;
    ImportJSONTimerGroupManager jsonManager;

    public ImportDataManager(Uri uri, Application application) {
        errorMessages = new LinkedList<String>();
        successMessages = new LinkedList<String>();

        this.uri = uri;
        this.application = application;
        this.context = application.getApplicationContext();

        jsonManager = new ImportJSONTimerGroupManager(application);
    }

    public LinkedList<String> getErrorMesages() {
        return errorMessages;
    }

    public LinkedList<String> getSuccessMessages() {
        return successMessages;
    }

    public void processZipFile() {
        try {
            InputStream inputStream = application.getContentResolver().openInputStream(uri);

            ZipInputStream zipFileInputStream = new ZipInputStream(inputStream);

            iterateOverEntries(zipFileInputStream);

            zipFileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorMessages.add(application.getResources().getString(R.string.error_message_json_import_zip_file));
        }
    }

    private void iterateOverEntries(ZipInputStream zipInputStream) throws IOException {
        ZipEntry zipEntry;

        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            System.out.println(zipEntry.getName());
            //use entry input stream:
            if (zipEntry.isDirectory()) {

            } else {
                String name = zipEntry.getName();
                if (name.contains(".json")) {
                    processJSONFile(zipInputStream, name);
                } else {
                    errorMessages.add(application.getResources().getString(R.string.error_message_json_import_file) + " " + name);
                }
            }

        }
        errorMessages.addAll(jsonManager.errorMessages);
        successMessages.addAll(jsonManager.successMessages);

    }

    private void processJSONFile(ZipInputStream zipInputStream, String jsonFileName) {
        try {
            String jsonString = readInputStream(zipInputStream, jsonFileName);
            JSONObject jsonObject = new JSONObject(jsonString);

            jsonManager.insertDataIntoDatabase(jsonObject, jsonFileName);
        } catch (JSONException e) {
            errorMessages.add(application.getResources().getString(R.string.error_message_json_import_json_file) + " " + jsonFileName);
            e.printStackTrace();
        } catch (IOException e) {
            errorMessages.add(application.getResources().getString(R.string.error_message_json_import_json_file_read_error) + " " + jsonFileName);
        }
    }

    private String readInputStream(final InputStream inputStream, String jsonFileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int size = 0;
        size = inputStream.available();
        byte[] buffer = new byte[size];
        int read = 0;
        while ((read = inputStream.read(buffer, 0, size)) >= 0) {
            stringBuilder.append(new String(buffer, 0, read));
        }

        String string = stringBuilder.toString();
        string = string.replace("\n", "");

        return string;

    }
}

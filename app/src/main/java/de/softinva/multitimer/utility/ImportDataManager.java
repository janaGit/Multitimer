package de.softinva.multitimer.utility;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.softinva.multitimer.R;
import de.softinva.multitimer.services.CopyBitmapService;

public class ImportDataManager {
    JSONObject json;
    Context context;
    Application application;
    Uri uri;
    LinkedList<String> errorMessages = new LinkedList<String>();
    LinkedList<String> successMessages = new LinkedList<String>();
    ImportJSONTimerGroupManager jsonManager;
    AppLogger logger = new AppLogger(this);
    HashMap<String, Bitmap> bitmapMap = new HashMap<>();
    HashMap<String, String> imageNameToBitmapName = new HashMap<>();

    public ImportDataManager(Uri uri, Application application) {
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

            iterateOverEntriesAndCopyAndDeleteImages(zipFileInputStream);

            zipFileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorMessages.add(application.getResources().getString(R.string.error_message_json_import_zip_file));
        }

        createImageCopiesForTimerGroupsAndTimers();
    }

    private void createImageCopiesForTimerGroupsAndTimers() {
        for (Map.Entry<String, String> entry : imageNameToBitmapName.entrySet()) {
            if (bitmapMap.get(entry.getValue()) != null) {
                CopyBitmapService.startImageBitmapService(bitmapMap.get(entry.getValue()), entry.getKey(), this.context);
            } else {
                errorMessages.add(context.getResources().getString(R.string.error_message_json_import_specified_image_not_found) + " " + entry.getValue());
            }

        }
    }

    private void iterateOverEntriesAndCopyAndDeleteImages(ZipInputStream zipInputStream) throws IOException {
        ZipEntry zipEntry;

        while ((zipEntry = zipInputStream.getNextEntry()) != null) {

            if (!zipEntry.isDirectory()) {

                logger.info(zipEntry.getName());
                String path = zipEntry.getName();

                if (path.contains("images/")) {

                    if (path.contains(".jpg") || path.contains(".png")) {

                        Bitmap bm = BitmapUtilities.getBitmapFromInputStream(zipInputStream);

                        bitmapMap.put(UtilityMethods.getFileNameWithExtensionFromPath(path), bm);

                        copyImageIntoMultitimerApplication(bm, UtilityMethods.getFileNameWithExtensionFromPath(path));

                    } else {
                        errorMessages.add(application.getResources().getString(R.string.error_message_json_import_image) + " " + path);
                    }

                } else {

                    if (path.contains(".json")) {

                        String timerGroupId = processJSONFile(zipInputStream, path);

                        UtilityMethods.deleteImagesInInternalFolderStartingWithName(timerGroupId, context);

                    } else {
                        errorMessages.add(application.getResources().getString(R.string.error_message_json_import_file) + " " + path);
                    }

                }
            }


        }
        errorMessages.addAll(jsonManager.errorMessages);
        successMessages.addAll(jsonManager.successMessages);
        imageNameToBitmapName = jsonManager.getImageNameToBitmapName();
    }

    private void copyImageIntoMultitimerApplication(Bitmap bm, String imageName) {
        UtilityMethods.copyJPGToExternalFolder(bm, UtilityMethods.getFileNameWithExtensionFromPath(imageName), context);
        successMessages.add(application.getResources().getString(R.string.success_message_json_import_image) + " " + imageName);
    }

    private String processJSONFile(ZipInputStream zipInputStream, String jsonFileName) {
        try {
            String jsonString = readInputStream(zipInputStream, jsonFileName);
            JSONObject jsonObject = new JSONObject(jsonString);

            return jsonManager.insertDataIntoDatabase(jsonObject, jsonFileName);
        } catch (JSONException e) {
            errorMessages.add(application.getResources().getString(R.string.error_message_json_import_json_file) + " " + jsonFileName);
            e.printStackTrace();
        } catch (IOException e) {
            errorMessages.add(application.getResources().getString(R.string.error_message_json_import_json_file_read_error) + " " + jsonFileName);
        }
        return "";
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

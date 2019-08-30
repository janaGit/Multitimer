package de.softinva.multitimer.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.softinva.multitimer.R;

public class BitmapUtilities {
    public static Bitmap getBitmapFromInputStream(InputStream inputStream) {
        Bitmap image = BitmapFactory.decodeStream(inputStream);

        return image;
    }

    public static Bitmap getBitmapFromUri(Uri uri, Context context) throws IOException {
        ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fd = pfd.getFileDescriptor();
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd);
        fd.sync();
        pfd.close();

        return bitmap;
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

}

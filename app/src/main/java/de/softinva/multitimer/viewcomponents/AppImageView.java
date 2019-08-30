package de.softinva.multitimer.viewcomponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import java.io.FileInputStream;
import java.io.IOException;

import de.softinva.multitimer.AppBroadcastReceiverImageNameUpdated;
import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.ImageSize;
import de.softinva.multitimer.utility.UtilityMethods;


public class AppImageView extends AppCompatImageView implements AppBroadcastReceiverImageNameUpdated.UpdateImageName {
    AppBroadcastReceiverImageNameUpdated broadcastReceiver;
    ImageSize imageSize;
    String imageName;

    public AppImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @BindingAdapter({"imageName", "imageSize"})
    public static void setImageName(AppImageView view, String imageName, ImageSize imageSize) {
        view.imageName = imageName;
        view.imageSize = imageSize;

        view.loadImage();
    }

    public void loadImage() {
        try {
            FileInputStream fis = getContext().openFileInput(UtilityMethods.returnImageFileName(imageName, imageSize));
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            setImageBitmap(bitmap);
        } catch (IOException e) {
            setImageResource(R.drawable.logo_fertig);
        }
        invalidate();
        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        broadcastReceiver = AppBroadcastReceiverImageNameUpdated.registerReceiverForImageNameUpdates(this, this.getContext());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AppBroadcastReceiverImageNameUpdated.unregisterReceiver(this.getContext(), broadcastReceiver);
    }

    @Override
    public void updateImageName(String imageName) {
        loadImage();
    }
}

package de.softinva.multitimer.viewcomponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import java.io.FileInputStream;
import java.io.IOException;

import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.ImageSize;
import de.softinva.multitimer.utility.UtilityMethods;


public class AppImageView extends AppCompatImageView {
    public AppImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @BindingAdapter({"imageName", "imageSize"})
    public static void setImageName(AppImageView view, String imageName, ImageSize imageSize) {
        try {
            FileInputStream fis = view.getContext().openFileInput(UtilityMethods.returnImageFileName(imageName, imageSize));
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            view.setImageBitmap(bitmap);
        } catch (IOException e) {
            view.setImageResource(R.drawable.logo_fertig);
        }
        view.invalidate();
        view.requestLayout();
    }

}

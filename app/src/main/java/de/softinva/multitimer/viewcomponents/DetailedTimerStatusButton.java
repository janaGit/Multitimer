package de.softinva.multitimer.viewcomponents;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.widget.ImageViewCompat;

import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.AppLogger;


public class DetailedTimerStatusButton extends AppCompatImageButton {
    protected AppLogger logger = new AppLogger(this);
    public Boolean isEnabled;
    protected Context context;

    public DetailedTimerStatusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setBackgroundDrawable(getResources().getDrawable(R.drawable.detailed_timer_status_button_border));
        setOnClickListener(view -> {
            setIsEnabled(!isEnabled);
        });
    }


    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        ColorStateList csl;
        if (isEnabled) {
            setImageResource(R.drawable.ic_timer_black_24dp);
            csl = AppCompatResources.getColorStateList(context, R.color.colorPrimary);
        } else {
            setImageResource(R.drawable.ic_timer_off_black_24dp);
            csl = AppCompatResources.getColorStateList(context, R.color.colorDisabled);
        }
        ImageViewCompat.setImageTintList(this, csl);
        invalidate();
        requestLayout();
    }

}

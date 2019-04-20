package de.softinva.multitimer.viewcomponents;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.widget.ImageViewCompat;

import de.softinva.multitimer.CountDownService;
import de.softinva.multitimer.R;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.utility.AppLogger;

public class StartStopTimerButton extends AppCompatImageButton {
    protected AppLogger logger = new AppLogger(this);
    protected boolean isRunning;
    protected Context context;
    protected Timer timer;

    public StartStopTimerButton(Context context, AttributeSet attrs) {
        super(context, attrs,  R.style.Widget_AppCompat_Button_Borderless);
        this.context = context;
        setImageResource(R.drawable.ic_av_timer_black_24dp);
        updateTintColor();
        setOnClickListener(view -> {
            if(isRunning){
                CountDownService.cancelTimer(timer, view.getContext());
            }else{
                CountDownService.startNewTimer(timer, view.getContext());
            }
        });
    }

    public void setIsRunning(Boolean isRunning) {
        if(isRunning != null){
            this.isRunning = isRunning;
            updateTintColor();
            invalidate();
            requestLayout();
        }
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
        updateTintColor();
        invalidate();
        requestLayout();
    }

    protected void updateTintColor() {
        if (isRunning) {
            ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.colorPrimary);
            ImageViewCompat.setImageTintList(this, csl);
        } else {
            ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.colorAccent);
            ImageViewCompat.setImageTintList(this, csl);
        }
    }
}

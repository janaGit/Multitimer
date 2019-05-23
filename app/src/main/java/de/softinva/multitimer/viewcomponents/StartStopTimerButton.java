package de.softinva.multitimer.viewcomponents;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.widget.ImageViewCompat;

import de.softinva.multitimer.CountDownService;
import de.softinva.multitimer.R;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.AppLogger;

public class StartStopTimerButton extends AppCompatImageButton {
    protected AppLogger logger = new AppLogger(this);
    protected boolean isRunning = false;
    protected boolean isEnabled = true;
    protected Context context;
    protected Timer timer;

    public StartStopTimerButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.style.Widget_AppCompat_Button_Borderless);
        this.context = context;
        setImageResource(R.drawable.ic_av_timer_black_24dp);
        updateTintColor();
        setOnClickListener(view -> {
            if (isRunning) {
                CountDownService.cancelTimer(timer, view.getContext());
            } else {
                if (isEnabled) {
                    CountDownService.startNewTimer(timer, view.getContext());
                    if (timer instanceof DetailedTimer) {
                        new TimerRepository((Application) context.getApplicationContext()).disableDetailedTimer(((DetailedTimer) timer).getGroupId(), timer.getId());

                    }
                }
            }
        });
    }

    public void setIsRunning(Boolean isRunning) {
        if (isRunning != null) {
            this.isRunning = isRunning;
            updateTintColor();
            invalidate();
            requestLayout();
        }
    }

    public void setIsEnabled(Boolean isEnabled) {
        if (isEnabled != null) {
            this.isEnabled = isEnabled;
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
            if (isEnabled) {
                ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.colorAccent);
                ImageViewCompat.setImageTintList(this, csl);
            } else {
                ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.colorDisabled);
                ImageViewCompat.setImageTintList(this, csl);
            }

        }
    }
}

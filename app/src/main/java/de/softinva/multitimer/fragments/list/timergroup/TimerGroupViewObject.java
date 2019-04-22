package de.softinva.multitimer.fragments.list.timergroup;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import de.softinva.multitimer.R;
import de.softinva.multitimer.TimerGroupActivity;
import de.softinva.multitimer.TimerGroupInfoActivity;
import de.softinva.multitimer.classes.AppViewObject;
import de.softinva.multitimer.model.TimerGroup;

public class TimerGroupViewObject extends AppViewObject<TimerGroup> {

    public int buttonSrcCompat;

    public TimerGroupViewObject(TimerGroup timerGroup) {
        super(timerGroup);
        if (timerGroup.isZipped) {
            buttonSrcCompat = R.drawable.ic_av_timer_black_24dp;
        } else {
            buttonSrcCompat = R.drawable.ic_chevron_right_black_24dp;
        }
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int buttonSrcCompat) {
        view.setImageResource(buttonSrcCompat);
    }

    public void onClickButton(View view) {
        TimerGroupActivity.startNewActivity(obj.id, view.getContext());
    }

    public void onClickTimerGroup(View view) {
        TimerGroupInfoActivity.startNewActivity(obj.id, view.getContext());
    }
}

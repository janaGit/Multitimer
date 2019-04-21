package de.softinva.multitimer.fragments.list.timergroup;

import android.content.Intent;
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

    public TimerGroupViewObject(TimerGroup timerGroup){
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


    public void setButtonSrcCompat(int buttonSrcCompat) {
        this.buttonSrcCompat = buttonSrcCompat;
    }

    public void onClickListItem(View view) {

    }

    public void onClickButton(View view) {
            Intent intent = new Intent(view.getContext(), TimerGroupActivity.class);
            intent.putExtra(TimerGroupActivity.GROUP_ID, obj.id);
            view.getContext().startActivity(intent);

    }

    public void onClickTimerGroup(View view) {
        Intent intent = new Intent(view.getContext(), TimerGroupInfoActivity.class);
        intent.putExtra(TimerGroupInfoActivity.GROUP_ID, obj.id);
        view.getContext().startActivity(intent);

    }
}

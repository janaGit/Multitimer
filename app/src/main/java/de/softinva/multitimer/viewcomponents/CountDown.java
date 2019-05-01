package de.softinva.multitimer.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;


public class CountDown extends AppCompatTextView {
    protected Long countDownInSec;
    protected String countDownAsString;
    protected AppLogger logger = new AppLogger(this);

    public CountDown(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setCountDownInSec(Long countDownInSec) {
        if (countDownInSec != null) {
            this.countDownInSec = countDownInSec;
            countDownAsString = UtilityMethods.transformToTextString(countDownInSec);
            setText(countDownAsString);
        }

    }
}

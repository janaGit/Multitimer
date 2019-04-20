package de.softinva.multitimer.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import de.softinva.multitimer.utility.AppLogger;


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
            transformToTextString();
            setText(countDownAsString);
        }

    }

    protected void transformToTextString() {
        int hours = countDownInSec.intValue() / 3600;
        int minutes = (countDownInSec.intValue() - (hours * 3600)) / 60;
        int seconds = (countDownInSec.intValue() - (hours * 3600) - (minutes * 60));
        if (hours != 0) {
            countDownAsString = hours + "h " + minutes + " min " + seconds + " sec";
        } else {
            if (minutes != 0) {
                countDownAsString = minutes + " min " + seconds + " sec";
            } else {
                countDownAsString = seconds + " sec";
            }
        }

        logger.info("countDownInSec: "+countDownInSec+" countDownAsString: " + countDownAsString);
    }
}

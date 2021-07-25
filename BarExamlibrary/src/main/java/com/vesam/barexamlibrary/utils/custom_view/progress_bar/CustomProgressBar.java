package com.vesam.barexamlibrary.utils.custom_view.progress_bar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CustomProgressBar extends ProgressBar {
private static final long DEFAULT_DELAY = 500;
private static final long DEFAULT_DURATION = 1000;

public CustomProgressBar(Context context) {
    super(context);
}

public CustomProgressBar(Context context, AttributeSet attrs) {
    super(context, attrs);
}

public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
}

public synchronized void setProgress(float progress) {
    super.setProgress((int) progress);
}

@Override
public synchronized void setProgress(int progress) {
    super.setProgress(progress);
}

public void startLcpProgressAnim(int progressTo) {
   startLcpProgressAnim(DEFAULT_DELAY, progressTo);
}

public void startLcpProgressAnim(long delay, int progressTo) {
   startLcpProgressAnim(DEFAULT_DURATION, delay, progressTo);
}

public void startLcpProgressAnim(long duration, long delay, float progressTo) {
    ObjectAnimator animation = ObjectAnimator.
            ofFloat(this, "progress",
                    (float)this.getProgress(), progressTo);
    animation.setDuration(duration);
    animation.setStartDelay(delay);
    animation.start();
}
}
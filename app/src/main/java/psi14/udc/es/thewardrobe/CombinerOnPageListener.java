package psi14.udc.es.thewardrobe;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import static psi14.udc.es.thewardrobe.Utils.Constants.APP_TAG;
import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;

public class CombinerOnPageListener implements OnPageChangeListener {

    int currentIndex = 0;
    String activityTag = "CombinerOnPageListener";

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (DEBUG) Log.d(APP_TAG + activityTag, "onPageScrollStateChanged: " + arg0);
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (DEBUG) Log.d(APP_TAG + activityTag, "onPageScrolled");
    }

    @Override
    public void onPageSelected(int arg0) {
        if (DEBUG) Log.d(APP_TAG + activityTag, "onPageSelected: " + arg0);
        currentIndex = arg0;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
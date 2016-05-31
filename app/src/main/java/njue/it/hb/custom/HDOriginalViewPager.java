package njue.it.hb.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * 写这个类是为了捕捉第三方缩放图片库抛出的错误
 */
public class HDOriginalViewPager extends ViewPager {

    public HDOriginalViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
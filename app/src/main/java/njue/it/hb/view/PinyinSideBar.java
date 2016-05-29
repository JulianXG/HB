package njue.it.hb.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.nio.channels.ClosedByInterruptException;
import java.util.Arrays;
import java.util.List;

public class PinyinSideBar extends View {

    private Context mContext;

    private AttributeSet mAttributeSet;

    private int mDefineStyle;

    private List<String> mLetterList;

    private Paint mPaint;

    private int mChoose = -1;

    public static final String[] INDEX_PINYIN = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public PinyinSideBar(Context context) {
        super(context, null);
    }

    public PinyinSideBar(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public PinyinSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttributeSet = attrs;
        mDefineStyle = defStyleAttr;
        mPaint = new Paint();
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#F0F0F0"));
        mLetterList = Arrays.asList(INDEX_PINYIN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / mLetterList.size();
        for (int i = 0; i < mLetterList.size(); i++) {
            mPaint.setColor(Color.parseColor("#606060"));
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setAntiAlias(true);

            if (i == mChoose) {
                mPaint.setColor(Color.parseColor("4F41FD"));
                mPaint.setFakeBoldText(true);
            }

            float x = width / 2 - mPaint.measureText(mLetterList.get(i)) / 2;
            float y = singleHeight * i + singleHeight / 2;
            canvas.drawText(mLetterList.get(i), x, y, mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = mChoose;
        int c = ((int) (y / getHeight() * mLetterList.size()));
        switch (action) {
            case MotionEvent.ACTION_UP:
        }

        return true;
    }
}
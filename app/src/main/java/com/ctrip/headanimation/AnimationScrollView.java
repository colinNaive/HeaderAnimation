package com.ctrip.headanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author Zhenhua on 2017/8/22.
 * @email zhshan@ctrip.com ^.^
 */
public class AnimationScrollView extends ScrollView {
    private static final String TAG = "VacationHomeFragment";
    private OnScrollChangeListener listener;
    private boolean msgLock;
    private final static int HANDLE_ACTION = 1;
    private final static int HANDLE_TIME = 2;
    private final static String TAG_ACTION = "TAG_ACTION";
    private final static String TAG_DY = "TAG_DY";
    public final static int ACTION_SCROLL_TOP = 3;
    public final static int ACTION_SCROLL_BOTTOM = 4;
    public final static int ACTION_SCROLL = 0;
    private ScrollHandler handler = new ScrollHandler();
    private int lastScrollY = 0;
    private int delay = 100;
    private Context context;


    private class ScrollHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANDLE_ACTION) {
                int dy = msg.getData().getInt(TAG_DY);
                int action = msg.getData().getInt(TAG_ACTION);
                listener.onScrollChanged(action, dy);
                msgLock = true;
                handler.sendEmptyMessageDelayed(HANDLE_TIME, delay);
            } else if (msg.what == HANDLE_TIME) {
                msgLock = false;
                lastScrollY = getScrollY();
            }
        }
    }

    public AnimationScrollView(Context context) {
        super(context);
        this.context = context;
    }

    public AnimationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setOnScrollListener(OnScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(int action, float dy);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (listener == null || msgLock) {
            return;
        }
        listener.onScrollChanged(-1, getScrollY()*0.65f);
    }
}

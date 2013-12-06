package com.nathan.myapps.widget.waterfall;

import com.nathan.myapps.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A generic, customizable Android ListView implementation that has 'Pull to
 * Refresh' functionality.
 * <p/>
 * This ListView can be used in place of the normal Android
 * android.widget.ListView class.
 * <p/>
 * Users of this class should implement OnRefreshListener and call
 * setOnRefreshListener(..) to get notified on refresh events. The using class
 * should call onRefreshComplete() when refreshing is finished.
 * <p/>
 * The using class can call setRefreshing() to set the state explicitly to
 * refreshing. This is useful when you want to show the spinner and 'Refreshing'
 * text when the refresh was not triggered by 'Pull to Refresh', for example on
 * start.
 * <p/>
 * For more information, visit the project page:
 * https://github.com/erikwt/PullToRefresh-ListView
 * 
 * @author Erik Wallentinsen <dev+ptr@erikw.eu>
 * @version 1.0.0
 */
public class MultiColumnPTRListView extends MultiColumnListView {

    private static final String TAG = MultiColumnPTRListView.class.getSimpleName();

    /** 松开更新 **/
    private final static int RELEASE_TO_REFRESH = 0;
    /** 下拉更新 **/
    private final static int PULL_TO_REFRESH = 1;
    /** 更新中 **/
    private final static int REFRESHING = 2;
    /** 无 **/
    private final static int DONE = 3;

    /** 实际的padding的距离与界面上偏移距离的比例 **/
    private final static int RATIO = 3;

    private int mBottomPosition;

    private boolean scrollbarEnabled;
    // private boolean bounceBackHeader;
    private boolean lockScrollWhileRefreshing;

    private LayoutInflater inflater;
    private int mLastY;
    private int state;

    private LinearLayout mHeaderView;

    private ProgressBar loadingPb;
    /** 箭头图标 **/
    private TextView loadingTv;
    /** 头部滚动条 **/
    private ImageView loadingIv;
    /** 显示动画 **/
     private RotateAnimation animation;
    /** 头部回退显示动画 **/
    private RotateAnimation reverseAnimation;
    /** 用于保证startY的值在一个完整的touch事件中只被记录一次 **/
    private boolean isRecored;
    /** 头部高度 **/
    private int measureHeaderHeight;
    /** 开始的Y坐标 **/
    private int mStartY;

    private boolean isBack;
    /** 是否要使用下拉刷新功能 **/
    public boolean showRefresh = true;

    private Context mContext;

    // 这个这样写好害人啊，一直都会报错
    // 因为我第一次向上滑动listview,结果会执行onRefresh()方法，执行onRefresh()方法就会报错
    // public static boolean canRefleash =
    // true;//在PullDownView类中控制listview是否下拉刷新
    public static boolean canRefleash = false;// 初始值修改成false值就好了

    public MultiColumnPTRListView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public MultiColumnPTRListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    public MultiColumnPTRListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(context);
    }

    /**
     * Default is false. When lockScrollWhileRefreshing is set to true, the list
     * cannot scroll when in 'refreshing' mode. It's 'locked' on refreshing.
     * 
     * @param lockScrollWhileRefreshing
     */
    public void setLockScrollWhileRefreshing(boolean lockScrollWhileRefreshing) {
        this.lockScrollWhileRefreshing = lockScrollWhileRefreshing;
    }

    /**
     * Set the state back to 'pull to refresh'. Call this method when refreshing
     * the data is finished.
     */
    public void onRefreshComplete() {
        state = DONE;
        changeHeaderViewByState();
    }

    private OnRefreshListener refreshListener = null;// (第2步)实现回调原理

    /**
     * (第1步)定义刷新监听的接口
     */
    public interface OnRefreshListener {

        /**
         * listview下拉刷新时被回调的方法
         */
        public void onRefresh();
    }

    // (第3步)注册一个listview需要刷新时的回调接口
    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        showRefresh = true;
    }

    // (第4步)调用回调方法
    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    private void init(Context context) {
        setVerticalFadingEdgeEnabled(false);
        inflater = LayoutInflater.from(context);
        mHeaderView = (LinearLayout) inflater.inflate(R.layout.pull_to_refresh_header_vertical,
                null);
        loadingPb = (ProgressBar) mHeaderView.findViewById(R.id.pull_to_refresh_progress);
        loadingIv = (ImageView) mHeaderView.findViewById(R.id.pull_to_refresh_image);
        loadingTv = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_text);

        measureView(mHeaderView);

        measureHeaderHeight = mHeaderView.getMeasuredHeight();

        mHeaderView.setPadding(0, -1 * measureHeaderHeight, 0, 0);
        mHeaderView.invalidate();

        /** 列表添加头部 **/
        addHeaderView(mHeaderView, null, false);

        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        state = DONE;

        // ViewTreeObserver vto = mHeaderView.getViewTreeObserver();
        // vto.addOnGlobalLayoutListener(new PTROnGlobalLayoutListener());
    }

    private OnScrollOverListener mOnScrollOverListener = new OnScrollOverListener()
    {

        @Override
        public boolean onListViewTopAndPullDown(int delta) {
            return false;
        }

        @Override
        public boolean onListViewBottomAndPullUp(int delta) {
            return false;
        }

        @Override
        public boolean onMotionDown(MotionEvent ev) {
            return false;
        }

        @Override
        public boolean onMotionMove(MotionEvent ev, int delta) {
            return false;
        }

        @Override
        public boolean onMotionUp() {
            if (MultiColumnPTRListView.canRefleash) {// 在PullDownView类中控制listview是否下拉刷新
                MultiColumnPTRListView.canRefleash = false;
                onRefresh();
            }
            return false;
        }
    };

    private void setHeaderPadding(int padding) {
        // headerPadding = padding;

        MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mHeaderView.getLayoutParams();
        mlp.setMargins(0, Math.round(padding), 0, 0);
        mHeaderView.setLayoutParams(mlp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int y = (int) event.getRawY();
        cancelLongPress();
        switch (event.getAction()) {
        case MotionEvent.ACTION_UP:
            if (state != REFRESHING) {
                if (state == DONE) {
                    // 什么都不做
                }
                if (state == PULL_TO_REFRESH) {
                    state = DONE;
                    changeHeaderViewByState();
                }
                if (state == RELEASE_TO_REFRESH) {
                    state = REFRESHING;
                    changeHeaderViewByState();
                    canRefleash = true;
                }
            }
            isRecored = false;
            isBack = false;
            // final boolean isHandlerMotionUp =
            // mOnScrollOverListener.onMotionUp();
            // if (isHandlerMotionUp) {
            // mLastY = y;
            // return true;
            // }
            break;

        case MotionEvent.ACTION_MOVE:
            int tempY = (int) event.getY();
            if (showRefresh) {
                if (!isRecored && firstItemIndex == 0) {
                    isRecored = true;
                    mStartY = tempY;
                }
                if (state != REFRESHING && isRecored) {
                    // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                    // 可以松手去刷新了
                    if (state == RELEASE_TO_REFRESH) {
                        setSelection(0);
                        // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                        if (((tempY - mStartY) / RATIO < measureHeaderHeight)
                                && (tempY - mStartY) > 0) {
                            loadingTv.startAnimation(reverseAnimation);
                            state = PULL_TO_REFRESH;
                            changeHeaderViewByState();
                        }
                        else if (tempY - mStartY <= 0) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                        // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                        else {
                            // 不用进行特别的操作，只用更新paddingTop的值就行了
                        }
                    }
                    // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                    if (state == PULL_TO_REFRESH) {
                        setSelection(0);
                        // 下拉到可以进入RELEASE_TO_REFRESH的状态
                        if ((tempY - mStartY) / RATIO >= measureHeaderHeight) {
                            state = RELEASE_TO_REFRESH;
                            isBack = true;
                            changeHeaderViewByState();
                        }
                        // 上推到顶了
                        else if (tempY - mStartY <= 0) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                    }
                    // done状态下
                    if (state == DONE) {
                        if (tempY - mStartY > 0) {
                            state = PULL_TO_REFRESH;
                            changeHeaderViewByState();
                        }
                    }
                    // 更新headView的size
                    if (state == PULL_TO_REFRESH) {
                        mHeaderView.setPadding(0, -1 * measureHeaderHeight + (tempY - mStartY)
                                / RATIO, 0, 0);
                    }
                    // 更新headView的paddingTop
                    if (state == RELEASE_TO_REFRESH) {
                        mHeaderView.setPadding(0, (tempY - mStartY) / RATIO - measureHeaderHeight,
                                0, 0);
                    }
                }
            }
            final int childCount = getChildCount();
            if (childCount == 0)
                return super.onTouchEvent(event);
            final int itemCount = getAdapter().getCount() - mBottomPosition;
            final int deltaY = y - mLastY;
            final int lastBottom = getChildAt(childCount - 1).getBottom();
            final int end = getHeight() - getPaddingBottom();
            final int firstVisiblePosition = getFirstVisiblePosition();
            final boolean isHandleMotionMove = mOnScrollOverListener.onMotionMove(event, deltaY);
            if (isHandleMotionMove) {
                mLastY = y;
                return true;
            }

            /** 到达底部 * 到达底部的事件在另外一个类执行 **/
            if (firstVisiblePosition + childCount >= itemCount && lastBottom <= end && deltaY < 0) {
                final boolean isHandleOnListViewBottomAndPullDown;
                isHandleOnListViewBottomAndPullDown = mOnScrollOverListener
                        .onListViewBottomAndPullUp(deltaY);
                if (isHandleOnListViewBottomAndPullDown) {
                    mLastY = y;
                    return true;
                }
            }
            break;

        case MotionEvent.ACTION_DOWN:
            if (firstItemIndex == 0 && !isRecored) {
                isRecored = true;
                mStartY = (int) event.getY();
            }
            mLastY = y;
            final boolean isHandled = mOnScrollOverListener.onMotionDown(event);
            if (isHandled) {
                mLastY = y;
                return isHandled;
            }
            break;
        }
        mLastY = y;
        return super.onTouchEvent(event);
    }

    // private void bounceBackHeader() {
    // int yTranslate = state == State.REFRESHING ?
    // header.getHeight() - mHeaderView.getHeight() :
    // -mHeaderView.getHeight() - mHeaderView.getTop();
    //
    // bounceAnimation = new TranslateAnimation(
    // TranslateAnimation.ABSOLUTE, 0,
    // TranslateAnimation.ABSOLUTE, 0,
    // TranslateAnimation.ABSOLUTE, 0,
    // TranslateAnimation.ABSOLUTE, yTranslate);
    //
    // bounceAnimation.setDuration(BOUNCE_ANIMATION_DURATION);
    // bounceAnimation.setFillEnabled(true);
    // bounceAnimation.setFillAfter(false);
    // bounceAnimation.setFillBefore(true);
    // // bounceAnimation.setInterpolator(new
    // OvershootInterpolator(BOUNCE_OVERSHOOT_TENSION));
    // bounceAnimation.setAnimationListener(new
    // HeaderAnimationListener(yTranslate));
    // startAnimation(bounceAnimation);
    // }

    // private void resetHeader(){
    //
    // if(getFirstVisiblePosition() > 0){
    // setHeaderPadding(-header.getHeight());
    // setState(State.PULL_TO_REFRESH);
    // return;
    // }
    //
    // if(getAnimation() != null && !getAnimation().hasEnded()){
    // bounceBackHeader = true;
    // }else{
    // bounceBackHeader();
    // }
    // }
    //
    // private void setUiRefreshing(){
    // image.clearAnimation();
    // image.setVisibility(View.GONE);
    // refreshingIcon.setVisibility(View.VISIBLE);
    // refreshingIcon.startAnimation(refreshingAnimation);
    // }
    //
    // private void stopRefreshing(){
    // refreshingIcon.clearAnimation();
    // refreshingIcon.setVisibility(View.GONE);
    // }

    private class HeaderAnimationListener implements AnimationListener {

        private int height, translation;
        private int stateAtAnimationStart;

        public HeaderAnimationListener(int translation) {
            this.translation = translation;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            stateAtAnimationStart = state;

            android.view.ViewGroup.LayoutParams lp = getLayoutParams();
            height = lp.height;
            lp.height = getHeight() - translation;
            setLayoutParams(lp);

            if (scrollbarEnabled) {
                setVerticalScrollBarEnabled(false);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            setHeaderPadding(stateAtAnimationStart == REFRESHING ? 0 : -measureHeaderHeight
                    - mHeaderView.getTop());
            // setSelection(0);

            android.view.ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = height;
            setLayoutParams(lp);

            if (scrollbarEnabled) {
                setVerticalScrollBarEnabled(true);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private class PTROnGlobalLayoutListener implements OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            int initialHeaderHeight = mHeaderView.getHeight();

            if (initialHeaderHeight > 0) {
                measureHeaderHeight = initialHeaderHeight;

                if (measureHeaderHeight > 0 && state != REFRESHING) {
                    setHeaderPadding(-measureHeaderHeight);
                    requestLayout();
                }
            }

            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState() {
        switch (state) {
        case RELEASE_TO_REFRESH:
            loadingIv.startAnimation(animation);
            loadingTv.setText(getResources().getString(R.string.pull_to_refresh_release_label));
            break;
        case PULL_TO_REFRESH:
            loadingTv.setText(getResources().getString(R.string.pull_to_refresh_pull_label));
            break;

        case REFRESHING:
            animHideShowView(mHeaderView, measureHeaderHeight, 500, 1);
            break;

        case DONE:
            animHideShowView(mHeaderView, measureHeaderHeight, 250, 0);
            loadingTv.setText(getResources().getString(R.string.pull_to_refresh_done_label));
            loadingIv.clearAnimation();
            break;
        }
    }

    /**
     * 回弹效果
     * 
     * @author Nathan
     * @param v
     * @param measureHeight
     * @param ainmTime
     */
    private void animHideShowView(final View v, final int measureHeight, int ainmTime,
            final int flag) {

        Animation anim = new Animation()
        {

            int paddingTop = v.getPaddingTop();
            boolean tag = true;
            int height = 0;

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                switch (flag) {
                case 0:
                    if (paddingTop == 0) {
                        height = (int) (measureHeight * interpolatedTime);
                        v.setPadding(0, -1 * height, 0, 0);
                        if(interpolatedTime ==1.0f)
                        {
                            loadingIv.setVisibility(View.VISIBLE);
                            loadingPb.setVisibility(View.INVISIBLE);
                        }
                    }
                    else {
                        height = (int) ((measureHeight + paddingTop) * interpolatedTime);
                        v.setPadding(0, -1 * height + paddingTop, 0, 0);

                    }
                    break;
                case 1:
                    height = (int) (paddingTop * interpolatedTime);
                    v.setPadding(0, (paddingTop - height), 0, 0);
                    if (v.getPaddingTop() == 0 && tag) {
                        loadingTv.setText(getResources().getString(
                                R.string.pull_to_refresh_refreshing_label));
                        loadingIv.setVisibility(View.INVISIBLE);
                        loadingPb.setVisibility(View.VISIBLE);
                        mOnScrollOverListener.onMotionUp();
                        tag = false;
                    }
                    break;
                }

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        anim.setDuration(ainmTime);
        v.startAnimation(anim);
    }

    /**
     * 此处是“估计”headerView的高与宽
     * 
     * @param child
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        }
        else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 可以自定义其中一个条目为尾部，尾部触发的事件将以这个为准，默认为最后一个
     * 
     * @param index
     *            倒数第几个，必须在条目数范围之内
     */
    public void setBottomPosition(int index) {
        if (index < 0)
            throw new IllegalArgumentException("Bottom position must > 0");
        mBottomPosition = index;
    }

    /**
     * 设置这个Listener可以监听是否到达顶端，或者是否到达低端等事件</br>
     * 
     * @see OnScrollOverListener
     */
    public void setOnScrollOverListener(OnScrollOverListener onScrollOverListener) {
        mOnScrollOverListener = onScrollOverListener;
    }

    public interface OnScrollOverListener {

        /**
         * 到达最顶部触发
         * 
         * @param delta
         *            手指点击移动产生的偏移量
         * @return
         */
        boolean onListViewTopAndPullDown(int delta);

        /**
         * 到达最底部触发
         * 
         * @param delta
         *            手指点击移动产生的偏移量
         * @return
         */
        boolean onListViewBottomAndPullUp(int delta);

        /**
         * 手指触摸按下触发，相当于{@link MotionEvent#ACTION_DOWN}
         * 
         * @return 返回true表示自己处理
         * @see View#onTouchEvent(MotionEvent)
         */
        boolean onMotionDown(MotionEvent ev);

        /**
         * 手指触摸移动触发，相当于{@link MotionEvent#ACTION_MOVE}
         * 
         * @return 返回true表示自己处理
         * @see View#onTouchEvent(MotionEvent)
         */
        boolean onMotionMove(MotionEvent ev, int delta);

        /**
         * 手指触摸后提起触发，相当于{@link MotionEvent#ACTION_UP}
         * 
         * @return 返回true表示自己处理
         * @see View#onTouchEvent(MotionEvent)
         */
        boolean onMotionUp();

    }

    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * 获取当前列表的状态
     * 
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     * 设置当前头部的状态，并更改视图
     * 
     * @param state
     */
    public void setState(int state) {
        this.state = state;
        changeHeaderViewByState();
    }

}

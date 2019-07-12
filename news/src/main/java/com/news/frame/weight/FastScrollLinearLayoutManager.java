package com.news.frame.weight;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * @author
 * @create 2019-03-28
 * @Desc:Recylerview LayoutManager
 */

public class FastScrollLinearLayoutManager extends LinearLayoutManager {
    private float MILLISECONDS_PER_INCH = 0.03f;
    private Context mContext;

    public FastScrollLinearLayoutManager(Context context) {
        super(context);
        mContext = context;
        setSpeedFast();
    }

    /**
     * 快滑
     */
    public void setSpeedFast() {
        MILLISECONDS_PER_INCH = mContext.getResources().getDisplayMetrics().density * 0.02f;
    }

    /**
     * 慢滑
     */
    public void setSpeedSlow() {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
        MILLISECONDS_PER_INCH = mContext.getResources().getDisplayMetrics().density * 0.3f;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return FastScrollLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            /**
             * 控制滑动速度
             */
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                // 单位速度 25F/densityDpi
                // return 1F / displayMetrics.densityDpi;
                return MILLISECONDS_PER_INCH / displayMetrics.density;
            }

            @Override
            /**
             * 该方法计算滑动所需时间。在此处间接控制速度。
             */
            protected int calculateTimeForScrolling(int dx) {
                /*
                   控制距离, 然后根据上面那个方(calculateSpeedPerPixel())提供的速度算出时间,
                   默认一次 滚动 TARGET_SEEK_SCROLL_DISTANCE_PX = 10000个像素,
                   在此处可以减少该值来达到减少滚动时间的目的.
                    */
            //间接计算时提高速度，也可以直接在calculateSpeedPerPixel提高
            //                if (dx > 3000) {
            //                    dx = 3000;
            //                }
                return super.calculateTimeForScrolling(dx);

            }
        };
        smoothScroller.setTargetPosition(position);

        startSmoothScroll(smoothScroller);
    }
}



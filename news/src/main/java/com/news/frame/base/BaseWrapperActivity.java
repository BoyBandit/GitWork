package com.news.frame.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.news.frame.R;
import com.news.frame.weight.TitleView;
import com.news.frame.weight.ToastView;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 *  Created by boy Administrator on 2018/2/11.
 * Auther:
 * desc:适配、初始化
 */

public abstract class BaseWrapperActivity extends SupportActivity {

    private boolean mReceiverTag = false;//判断广播是否注册
    protected boolean isRegister = true;

    private TitleView titleView;
    protected FrameLayout frameLayout;
    private LinearLayout rootView;
    protected AppCompatActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取类名
//        Appliction.getInstance().activityName=this.getClass().getSimpleName();
        handleBeforeSetLayout();//设置布局之前的操作
        setContentView(R.layout.base_layout);
        intiBaseView();
        intiBaseData();
        ButterKnife.bind(this);
        initView(savedInstanceState, titleView, getIntent());
    }


    @Override
    public void onBackPressedSupport() {
        beforeFinish();
        super.onBackPressedSupport();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
        if (titleView != null) {
            titleView.removeAllViews();
            titleView = null;
        }
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            frameLayout = null;
        }

    }


    /**
     * 设置布局之前的操作
     */
    protected void handleBeforeSetLayout() {

    }

    /*
    *     子acitvity设置布局
    * */
    protected abstract int setLayout();

    /**
     * 初始化控件数据
     */
    protected abstract void initView(Bundle savedInstanceState, TitleView titleView, Intent intent);


    /**
     * 初始化布局
     */
    private void intiBaseView() {
        mActivity = this;
        titleView = (TitleView) findViewById(R.id.base_title_view);
        frameLayout = (FrameLayout) findViewById(R.id.base_frame_layout);
        rootView = (LinearLayout) findViewById(R.id.base_root_view);
    }


    /**
     * 初始化baseAcitvity的控件
     */
    private void intiBaseData() {
        titleView.setTitle("Base");
        titleView.setChildClickListener(R.id.iv_title_left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeFinish();
                finish();
            }
        });
        View view = getLayoutInflater().inflate(setLayout(), null);
        frameLayout.addView(view);
    }

    /**
     * 按返回键之前操作
     */
    protected void beforeFinish() {

    }

    /**
     * 设置根布局（整个Activity）的背景色
     *
     * @param drawableRes 背景色
     */
    protected void setRootBackGroundResource(int drawableRes) {
        rootView.setBackgroundResource(drawableRes);
    }

    /**
     * 设置根布局（整个Activity）的背景色
     *
     * @param color 背景色
     */
    protected void setRootBackGroundColor(@ColorInt int color) {
        rootView.setBackgroundColor(color);
    }

    /**
     * 隐藏标题栏
     */
    protected void showTitle(boolean isShow) {
        titleView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    /**
     * 注册广播
     */
    private void registerReceiver() {
        if (!mReceiverTag && isRegister) {     //在注册广播接受者的时候 判断是否已被注册,避免重复多次注册广播
            mReceiverTag = true;    //标识值 赋值为 true 表示广播已被注册
            IntentFilter filter = new IntentFilter();
            filter.addAction("ExitApp");
            this.registerReceiver(this.mBrocast, filter);

        }
    }

    // 创建一个广播对象
    protected BroadcastReceiver mBrocast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            finish();
        }
    };

    /**
     * 注销广播
     */
    private void unregisterReceiver() {
        if (mReceiverTag && isRegister) {   //判断广播是否注册
            mReceiverTag = false;   //Tag值 赋值为false 表示该广播已被注销
            this.unregisterReceiver(this.mBrocast);

        }

    }

    /**
     * 关闭关于BaseActivity的所有Activity
     */
    public void myExit() {
        Intent intent = new Intent();
        intent.setAction("ExitApp");
        this.sendBroadcast(intent);
        super.finish();
    }

    public void showToast(String msg){
        ToastView.getInstance(mActivity).showToastContent(msg, Toast.LENGTH_SHORT);
    }
}

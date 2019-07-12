package com.news.frame.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.news.frame.R;

/**
 * Created by Amuse
 * Data:2018/12/28 0028
 * Desc:提示
 */

public class ToastView {
    static Toast mToast;

    private Context mContext;

    public ToastView(Context context) {
        this.mContext = context;
    }

    private static ToastView toastView;

    public static ToastView getInstance(Context context) {
        if (toastView == null) {
            toastView = new ToastView(context.getApplicationContext());
        }

        return toastView;
    }

    /**
     * 文本弹窗提示
     *
     * @param msg
     * @param duration
     */
    public void showToastContent(String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext.getApplicationContext(), "", duration);
        }
        mToast.setDuration(duration);
        View view = View.inflate(mContext.getApplicationContext(), R.layout.toast_normal, null);
        TextView tv_txt = (TextView) view.findViewById(R.id.tv_txt);
        tv_txt.setText(msg);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();

    }

    /**
     * 文本弹窗提示
     *
     * @param msg
     *
     */
    public void showToastContent(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext.getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        View view = View.inflate(mContext.getApplicationContext(), R.layout.toast_normal, null);
        TextView tv_txt = (TextView) view.findViewById(R.id.tv_txt);
        tv_txt.setText(msg);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();

    }
}

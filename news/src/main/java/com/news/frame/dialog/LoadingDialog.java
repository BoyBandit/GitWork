package com.news.frame.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.news.frame.R;


/**
 * Created by Administrator on 2018/2/10.
 * Auther:ruiwen
 * 数据加载dialog
 */

public class LoadingDialog extends ProgressDialog {
    public LoadingDialog(Context context) {
        super(context);
    }
    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading_progressly);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

}


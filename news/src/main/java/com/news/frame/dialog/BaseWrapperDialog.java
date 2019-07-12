package com.news.frame.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;

/**
 * Created by Amuse
 * Date:2019/7/3.
 * Desc:
 */

public abstract class BaseWrapperDialog extends BaseDialog{
    public BaseWrapperDialog(@NonNull Context context) {
        super(context);
        setDialogParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }
}

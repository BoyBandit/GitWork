package com.news.frame.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.news.frame.R;
import com.news.frame.inter.OnViewHolder;
import com.news.frame.util.ViewHolder;

import java.lang.ref.WeakReference;

/**
 * Created :Auser
 * Date: 2019/5/13.
 * Desc:PopupWindow 基类
 */

public abstract class BasePopupWindow extends PopupWindow implements OnViewHolder {
    private WeakReference<Activity> viewReference; //MvpView的子类的弱引用
    protected ViewHolder helper;
//    public boolean canDismiss=false;
    WindowManager.LayoutParams params;
    public BasePopupWindow(@NonNull Activity activity) {

        viewReference = new WeakReference<>(activity);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setClippingEnabled(true);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getDialogContext(), R.color.bgAlpha)));
        setContentView(getHelperView(null, getViewLayout(), this));
    }



    public abstract int getViewLayout();

    /**
     * 实例化对应layoutId的view同时生成ViewHelper
     *
     * @param group    可为null
     * @param layoutId
     * @param listener
     * @return
     */
    protected View getHelperView(ViewGroup group, int layoutId, OnViewHolder listener) {
        helper = new ViewHolder(LayoutInflater.from(getDialogContext()).inflate(layoutId, group, false));
        if (listener != null) {
            listener.helper(helper);
        }
        return helper.getItemView();
    }




    @Override
    public void helper(ViewHolder helper) {

    }


    public Activity getDialogContext() {
        return viewReference == null ? null : viewReference.get();
    }


    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }


    /**
     * 关闭处理
     */
   public  void onDestroy(){
        if(viewReference!=null){
            viewReference.clear();
            viewReference=null;
        }
        dismiss();
   }
    public void closePopupWindow()
    {
        if (this.isShowing()) {
            this.dismiss();
            setBgShadow(1f);
        }
    }

    /**
     * 设置阴影
     */
    public void setBgShadow(float alpha){
        if(params==null){
            params=getDialogContext().getWindow().getAttributes();
        }
        params.alpha=alpha;
        getDialogContext().getWindow().setAttributes(params);
    }

//    @Override
//    public void dismiss() {
////        new Exception().printStackTrace();
//        if(canDismiss)
//            dismiss2();
//        else {
//            StackTraceElement[] stackTrace = new Exception().getStackTrace();
//            if(stackTrace.length >= 2 && "dispatchKeyEvent".equals(stackTrace[1].getMethodName())){
//                dismiss2();
//            }
//        }
//    }
//
//    public void dismiss2(){
//        super.dismiss();
//    }


}

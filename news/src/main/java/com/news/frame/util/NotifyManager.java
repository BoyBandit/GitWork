package com.news.frame.util;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.news.frame.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Auser
 * @create 2019-04-08
 * @Desc:通知管理
 */

public class NotifyManager {
    private static NotifyManager notifyManager;

    private WeakReference<Context> weakReference;
    private NotificationManager manager;

    public NotifyManager(Context context) {
        weakReference = new WeakReference<>(context);
        manager = (NotificationManager) weakReference.get().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static NotifyManager getInstance(Context context) {
        if (notifyManager == null) {
            notifyManager = new NotifyManager(context);
        }

        return notifyManager;
    }

    /**
     * 开柜记录消息提醒
     *
     * @param title    标题
     * @param msg      内容
     * @param drawable logo图
     * @param id
     */
    public void showNotice(String title, String msg, int drawable, int id) {
        if (getNotificationManager() == false) {
            return;
        }
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建
            NotificationChannel channel = new NotificationChannel(String.valueOf(id), "通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(ContextCompat.getColor(weakReference.get(), R.color.text_Main));
            channel.setShowBadge(true);
            channel.setDescription("通知栏");
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(weakReference.get(), String.valueOf(id));
        mBuilder.setContentTitle(title)
                .setContentText(msg)
                .setLargeIcon(BitmapFactory.decodeResource(weakReference.get().getResources(), drawable))
                .setOnlyAlertOnce(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.return_key_while)
                .setColor(ContextCompat.getColor(weakReference.get(), R.color.colorMain))
                .setAutoCancel(true);
        manager.notify(id, mBuilder.build());

    }


    /**
     * 开柜记录消息提醒
     *
     * @param title    标题
     * @param msg      内容
     * @param drawable logo图
     * @param id
     */
    public void showNotice(String title, String msg, int drawable, int id, RemoteViews remoteViews) {
        if (getNotificationManager() == false) {
            return;
        }
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建
            NotificationChannel channel = new NotificationChannel(String.valueOf(id), "通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(ContextCompat.getColor(weakReference.get(), R.color.text_Main));
            channel.setShowBadge(true);
            channel.setDescription("通知栏");
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(weakReference.get(), String.valueOf(id));
        mBuilder.setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.return_key_while)
                .setColor(ContextCompat.getColor(weakReference.get(), R.color.colorMain))
                .setAutoCancel(true);
        manager.notify(id, mBuilder.build());

    }


    /**
     * 开柜记录消息提醒(广播处理)
     *
     * @param title    标题
     * @param msg      内容
     * @param drawable logo图
     * @param id
     */
    public <T extends BroadcastReceiver> void showNotice(String title, String msg, int drawable, int id, Class<T> receiver) {
        if (getNotificationManager() == false) {
            return;
        }
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建
            NotificationChannel channel = new NotificationChannel(String.valueOf(id), "通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(ContextCompat.getColor(weakReference.get(), R.color.text_Main));
            channel.setShowBadge(true);
            channel.setDescription("通知栏");
            manager.createNotificationChannel(channel);
        }

        Intent intentClick = new Intent(weakReference.get(), receiver);
        intentClick.setAction("notification_clicked");
        intentClick.putExtra("id", id);
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(weakReference.get(), 0, intentClick, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(weakReference.get(), String.valueOf(id));
        //点击删除
        Intent cancelIntent = new Intent(weakReference.get(), receiver);
        cancelIntent.setAction("notification_cancelled");
        cancelIntent.putExtra("id", id);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(weakReference.get(), 0, cancelIntent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setContentTitle(title)
                .setContentText(msg)
                .setLargeIcon(BitmapFactory.decodeResource(weakReference.get().getResources(), drawable))
                .setOnlyAlertOnce(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.return_key_while)
                .setColor(ContextCompat.getColor(weakReference.get(), R.color.colorMain))
                .setContentIntent(pendingIntentClick)
                .setDeleteIntent(cancelPendingIntent)
                .setAutoCancel(true);
        manager.notify(id, mBuilder.build());

    }


    /**
     * 开柜记录消息提醒
     *
     * @param title    标题
     * @param msg      内容
     * @param drawable logo图
     * @param tClass   要跳转的activity
     * @param id       通知id
     * @param <T>
     */
    public <T> void showNotice(String title, String msg, int drawable, Class<T> tClass, int id) {
        if (getNotificationManager() == false) {
            return;
        }
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建
            NotificationChannel channel = new NotificationChannel(String.valueOf(id), "通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(ContextCompat.getColor(weakReference.get(), R.color.text_Main));
            channel.setShowBadge(true);
            channel.setDescription("拯救通知栏");
            manager.createNotificationChannel(channel);
        }

        Intent clickIntent = new Intent(weakReference.get(), tClass);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(weakReference.get(), String.valueOf(id));
        mBuilder.setContentTitle(title)
                .setContentText(msg)
                .setLargeIcon(BitmapFactory.decodeResource(weakReference.get().getResources(), drawable))
                .setOnlyAlertOnce(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.return_key_while)
                .setColor(ContextCompat.getColor(weakReference.get(), R.color.colorMain));
        clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(weakReference.get(), 0, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(clickPendingIntent);
        mBuilder.setAutoCancel(true);
        manager.notify(id, mBuilder.build());

    }

    /**
     * 删除通知
     *
     * @param id
     */
    public void deleteNotification(int id) {
        getNotificationManager();
        manager.cancel(id);
    }

    /**
     * 清空所有通知
     */
    public void destroy() {
        if (weakReference != null) {
            manager.cancelAll();
            manager = null;
            weakReference.clear();
            weakReference = null;
            notifyManager = null;
        }
    }


    /**
     * 通知栏相关判断
     *
     * @return
     */
    public boolean getNotificationManager() {

        if (weakReference == null) {
            return false;
        }
//        if(isNotificationEnabled(weakReference.get())){
//            openSetNotification(weakReference.get());
//            return false;
//        }
//        Log.i("=======uuuu=======",""+isNotificationEnabled(weakReference.get()));
        if (manager == null) {
            manager = (NotificationManager) weakReference.get().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return true;
    }


    /**
     * 获取通知权限
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 打开设置通知栏界面
     *
     * @param context
     */
    public void openSetNotification(Context context) {
        /**
         * 跳到通知栏设置界面
         * @param context
         */
        Intent localIntent = new Intent();
        //直接跳转到应用通知设置的代码：
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }


//    @SuppressLint("NewApi")
//    private void createNotification(Context context) {
//    RemoteViews    remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_refresh_header);//通知栏布局
//        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        mBuilder = new NotificationCompat.Builder(this);
//        // 点击跳转到播报界面
//        if (mMessenger != null) {
//            Intent intent_main = new Intent(this, VoiceBroadcastActivity.class);
//            PendingIntent pending_intent_go = PendingIntent.getActivity(this, 1, intent_main, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.fl_window_layout, pending_intent_go);
//        }
//        //关闭播报
//        Intent intent_cancel = new Intent();
//        intent_cancel.setAction(BroadcastConstants.ACTION_CLOSE);
//        PendingIntent pending_intent_close = PendingIntent.getBroadcast(this, 2, intent_cancel, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.iv_close_notify, pending_intent_close);
//
//        mBuilder.setSmallIcon(R.drawable.ic_close_grey1); // 设置顶部图标（状态栏）
//        mBuilder.setContent(remoteViews);
//        mBuilder.setOngoing(true);
//        mNotificationManager.notify(BroadcastConstants.NOTIFICATION_CEDE, mBuilder.build());
//    }

}

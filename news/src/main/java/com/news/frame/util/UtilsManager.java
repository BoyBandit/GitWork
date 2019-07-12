package com.news.frame.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  Created by boy Administrator on 2018/2/1.
 * Auther:
 * app工具类
 */

public class UtilsManager {


    private SharedPreferences sharedPreferences = null;
    private static UtilsManager instance = null;
    private Context mContext = null;

    public static UtilsManager getInstance(Context context) {
        if (instance == null) {
            instance = new UtilsManager(context);
        }

        return instance;
    }

    public static UtilsManager getInstance() {
        if (instance == null) {
            instance = new UtilsManager();
        }

        return instance;
    }

    public UtilsManager() {
    }

    public UtilsManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * @param saveName 存储的名字
     */
    public SharedPreferences.Editor setSharePreferencesSave(String saveName) {

        return getSPInstance(saveName).edit();
    }

    /**
     * 获得String类型的内存储
     *
     * @param saveName
     * @param keyName
     * @return
     */
    public String getSPStringCode(String saveName, String keyName) {
        return getSPInstance(saveName).getString(keyName, "");
    }

    /**
     * 获得Boolean类型的内存储
     *
     * @param saveName
     * @param keyName
     * @return
     */
    public boolean getSPBooleanCode(String saveName, String keyName) {
        return getSPInstance(saveName).getBoolean(keyName, false);
    }


    /**
     * 清空所有存储数据
     *
     * @param saveName
     */
    public void clearShareData(String saveName) {
        SharedPreferences.Editor editor = getSPInstance(saveName).edit();
        editor.clear().commit(); //数据提交到xml文件中

    }


    public SharedPreferences getSPInstance(String saveName) {
        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(saveName, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    /**
     * 设置sp为空
     */
    public void setShNull() {
        sharedPreferences = null;
    }

    /**
     * 获取手机屏幕的宽度
     *
     * @return
     */
    public int getWindowWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;

    }

    /**
     * 获取手机屏幕的高度
     *
     * @return
     */
    public int getWindowHeight() {

        DisplayMetrics dm = new DisplayMetrics();
        dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * 关闭键盘
     * 提示框
     *
     * @return
     */
    public void closeKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    /**
     * 7.0apk安裝
     *
     * @param apkFile
     */
    public void installApk(File apkFile) {
        if (apkFile == null) {
            return;
        }
        //兼容8.0
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            boolean hasInstallPermission = mActivity.getPackageManager().canRequestPackageInstalls();
//            if (!hasInstallPermission) {
////                            ToastUtil.makeText(MyApplication.getContext(), MyApplication.getContext().getString(R.string.string_install_unknow_apk_note), false);
//                startInstallPermissionSettingActivity();
//                return;
//            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", apkFile);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            mContext.startActivity(install);
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(install);
        }

    }

    /**
     * 读取本地文件的方法
     *
     * @param context
     * @param fileName
     * @return
     */
    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取版本信息
     * @return
     */
    public String getVersionName(){
            PackageManager manager = mContext.getPackageManager();
            String name = "";
            try {
                PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
                name = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

          return name;
    }

    InputMethodManager inputMethodManager;

    /**
     * 隐藏软键盘
     * @param view
     */
    public void hideKeyboard( View view) {
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 跳转到设置-允许安装未知来源-页面
     * 注意这个是8.0新API
     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void startInstallPermissionSettingActivity() {
//
//
//
//        Intent intent = new Intent();
//        //获取当前apk包URI，并设置到intent中（这一步设置，可让“未知应用权限设置界面”只显示当前应用的设置项）
//        Uri packageURI = Uri.parse("package:"+mActivity.getPackageName());
//        intent.setData(packageURI);
//
//        //设置不同版本跳转未知应用的动作
//        if (Build.VERSION.SDK_INT >= 26) {
//            //intent = new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
//            intent.setAction(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
//        }else {
//            intent.setAction(android.provider.Settings.ACTION_SECURITY_SETTINGS);
//        }
//        mContext.startActivityForResult(intent, 1);
//
//
//
//        //注意这个是8.0新API
////        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////       startActivity(intent);
//    }


}

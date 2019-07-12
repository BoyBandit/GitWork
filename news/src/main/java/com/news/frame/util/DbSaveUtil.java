package com.news.frame.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.news.frame.BaseConfig;
import com.news.frame.WrapperApplication;
import com.news.frame.bean.DaoMaster;
import com.news.frame.bean.DaoSession;
import com.news.frame.bean.DownloadData;
import com.news.frame.bean.DownloadDataDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Amuse
 * Data:2019/1/18 0018
 * Desc:数据库缓存
 */

public class DbSaveUtil {
    private static DbSaveUtil db;
    private DaoMaster.DevOpenHelper openHelper;

    private Context context;

    public DbSaveUtil() {
        this.context = WrapperApplication.getInstance();
        openHelper = new DaoMaster.DevOpenHelper(context, getDbName(), null);
    }

    private String getDbName() {
        return BaseConfig.TYPE_DB_NAME;
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static DbSaveUtil getInstance() {
        if (db == null) {
            synchronized (DbSaveUtil.class) {
                if (db == null) {
                    db = new DbSaveUtil();
                }
            }
        }
        return db;
    }


    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, getDbName(), null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, getDbName(), null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    public void save(DownloadData info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadDataDao downInfoDao = daoSession.getDownloadDataDao();
        downInfoDao.insert(info);
    }

    public void update(DownloadData info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadDataDao downInfoDao = daoSession.getDownloadDataDao();
        downInfoDao.update(info);
    }

    public void deleteDownData(DownloadData info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadDataDao downInfoDao = daoSession.getDownloadDataDao();
        downInfoDao.delete(info);
    }


    public DownloadData queryDownBy(long Id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadDataDao downInfoDao = daoSession.getDownloadDataDao();
        QueryBuilder qb = downInfoDao.queryBuilder();
        try {
            qb.where(DownloadDataDao.Properties.Id.eq(Id));
            List<DownloadData> list = qb.list();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<DownloadData> queryDownAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadDataDao downInfoDao = daoSession.getDownloadDataDao();
        QueryBuilder qb = downInfoDao.queryBuilder();
        return qb.list();
    }
}

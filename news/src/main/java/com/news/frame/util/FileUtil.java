package com.news.frame.util;


import org.greenrobot.greendao.annotation.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * @author
 * @create 2019-04-03
 * @Desc:文件管理
 */

public class FileUtil {

    private static FileUtil fileUtil;

    public static FileUtil getInstance() {
        if (fileUtil == null) {
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    /**
     * 判断文件是否存在
     *
     * @param file
     * @return
     */
    public boolean isFile(File file) {
        try {
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    public boolean deleteFile(File file){

        try {
            if (isFile( file)) {
                return file.delete();//删除成功返回true
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * 文件的复制
     */
    public void copyFile(String parentDir, String fileName, File originFile, CopyListener copyListener) {
        try {
            FileInputStream fileInputStream = new FileInputStream(originFile);
            copyFile(parentDir, fileName, fileInputStream, originFile.length(), copyListener);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件的复制
     */
    public void copyFile(String parentDir, String fileName, InputStream inputStream, long totalLenth, CopyListener copyListener) {
        try {
            copyListener.startCopy();
            File newFile = new File(parentDir + File.separator + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            byte[] data = new byte[2048];
            int len = 0;
            long currentLenght = 0;
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
                currentLenght += len;
                copyListener.progress((int) (currentLenght * 100 / totalLenth));
            }
            copyListener.finish(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface CopyListener {
        void startCopy();

        void progress(int progress);

        void finish(File file);
    }

    /**
     * 创建文件
     *
     * @param path     文件所在目录的目录名，如/java/test/0.txt,要在当前目录下创建一个文件名为1.txt的文件，<br>
     *                 则path为/java/test，fileName为1.txt
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    public boolean createFile(@NotNull String path, @NotNull String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
//            Logger.w("新建文件失败：file.exist()=" + file.exists());
            return false;
        } else {
            try {
                boolean isCreated = file.createNewFile();
                return isCreated;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 删除单个文件
     *
     * @param path     文件所在路径名
     * @param fileName 文件名
     * @return 删除成功则返回true
     */
    public boolean deleteFile(@NotNull String path, @NotNull String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            return isDeleted;
        } else {
            return false;
        }
    }


    /**
     * 根据文件名获得文件的扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名（不带点）
     */
    public String getFileSuffix(@NotNull String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1, fileName.length());
        return suffix;
    }

    /**
     * 重命名文件
     *
     * @param oldPath 旧文件的绝对路径
     * @param newPath 新文件的绝对路径
     * @return 文件重命名成功则返回true
     */
    public boolean renameTo(@NotNull String oldPath, @NotNull String newPath) {
        if (oldPath.equals(newPath)) {
//            Logger.w( "文件重命名失败：新旧文件名绝对路径相同！");
            return false;
        }
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        boolean isSuccess = oldFile.renameTo(newFile);
//        Logger.w("文件重命名是否成功：" + isSuccess);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象
     * @param newFile 新文件对象
     * @return 文件重命名成功则返回true
     */
    public boolean renameTo(File oldFile, File newFile) {
        if (oldFile.equals(newFile)) {
//            Logger.w( "文件重命名失败：旧文件对象和新文件对象相同！");
            return false;
        }
        boolean isSuccess = oldFile.renameTo(newFile);
//        Logger.w("文件重命名是否成功：" + isSuccess);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象，File类型
     * @param newName 新文件的文件名，String类型
     * @return 重命名成功则返回true
     */
    public boolean renameTo(File oldFile, String newName) {
        File newFile = new File(oldFile.getParentFile() + File.separator + newName);
        boolean flag = oldFile.renameTo(newFile);
        return flag;
    }


    /**
     * 文件大小的格式化
     *
     * @param size 文件大小，单位为byte
     * @return 文件大小格式化后的文本
     */
    public String formatSize(long size) {
        DecimalFormat df = new DecimalFormat("####.00");
        if (size < 1024) // 小于1KB
        {
            return size + "Byte";
        } else if (size < 1024 * 1024) // 小于1MB
        {
            float kSize = size / 1024f;
            return df.format(kSize) + "KB";
        } else if (size < 1024 * 1024 * 1024) // 小于1GB
        {
            float mSize = size / 1024f / 1024f;
            return df.format(mSize) + "MB";
        } else if (size < 1024L * 1024L * 1024L * 1024L) // 小于1TB
        {
            float gSize = size / 1024f / 1024f / 1024f;
            return df.format(gSize) + "GB";
        } else {
            return "size: error";
        }
    }


    /**
     * 获取某个路径下的文件列表
     *
     * @param path 文件路径
     * @return 文件列表File[] files
     */
    public File[] getFileList(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                return files;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取某个目录下的文件列表
     *
     * @param directory 目录
     * @return 文件列表File[] files
     */
    public File[] getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            return files;
        } else {
            return null;
        }
    }

    /**
     * 取得文件或文件夹大小
     */
    public long getFileSize(File file) {
        long size = 0;
        if (!file.isDirectory()) { // 文件
            return file.length();
        }
        File files[] = file.listFiles(); // 文件夹（递归）
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size = size + getFileSize(files[i]);
            } else {
                size = size + files[i].length();
            }
        }
        return size;
    }

    /**
     *  删除文件
     * @param f
     */
    public void deleteAllFile(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; ++i) {
                    deleteFile(files[i]);
                }
            }
        }
        f.delete();
    }


}

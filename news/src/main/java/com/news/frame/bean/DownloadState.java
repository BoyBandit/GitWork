package com.news.frame.bean;

/**
 * Created by Amuse
 * Data:2019/1/17 0017
 * Desc:下载状态
 */

public enum  DownloadState {
    START(0),
    DOWNLOADER(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    FINISH(5);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    DownloadState(int state) {
        this.state = state;
    }
}

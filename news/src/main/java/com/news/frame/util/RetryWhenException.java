package com.news.frame.util;


import com.news.frame.bean.DownloadData;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 *   Created by boy
 * Data:2019/1/18 0018
 * Desc:
 */

public class RetryWhenException implements Function<Observable<Throwable>, ObservableSource<DownloadData>> {
    int current = -1;
    int[] timeDely = new int[]{1, 3, 5, 4, 2, 3, 5, 4, 2, 1,2, 3, 5, 4, 2, 1,2, 3, 5, 4, 2};

    @Override
    public Observable apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(new Function<Throwable, Observable<Long>>() {
            @Override
            public Observable<Long> apply(Throwable throwable) throws Exception {
                ++current;
//                System.out.println("retry：" + current + String.format(" 时间：%tT", new Date()));
                if ((throwable instanceof ConnectException
                        || throwable instanceof SocketTimeoutException
                        ||throwable instanceof TimeoutException) && current < timeDely.length) {
                    return Observable.timer(timeDely[current], TimeUnit.SECONDS);
                } else {
                    return Observable.error(throwable);
                }
            }
        });
    }
}
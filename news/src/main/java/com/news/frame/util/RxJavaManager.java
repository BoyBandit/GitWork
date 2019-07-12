package com.news.frame.util;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created :Auser
 * Date: 2019/5/14.
 * Desc:Rxjava工具类
 */

public class RxJavaManager {
    private static    RxJavaManager  rxJavaManager;

    public static RxJavaManager getInstance(){
        if(rxJavaManager==null){
            rxJavaManager=new RxJavaManager();
        }

        return rxJavaManager;
    }

    /**
     * 获取要解析的数组和数据
     * @param items
     * @param <T>
     * @return
     */
    public <T>Flowable getFromArray(T... items){
        return   Flowable.fromArray(items);
    }

    /**
     * 获取要解析的集合和数据
     * @param list
     * @param <T>
     * @return
     */
    public <T>Flowable getFromIterable(List<T> list){
        return   Flowable.fromIterable(list);
    }


    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public  <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 合并数据
     * @param list
     * @param consumer
     * @param <T>
     */
    public <T>void   concat(List<Publisher<T>> list, Consumer<T> consumer){
        Flowable.concat(list).subscribe(consumer);
    }
    /**
     * 解析数组
     * @param consumer
     * @param items
     * @param <T>
     */
    public <T> void  fromArray(Consumer<T> consumer, T... items){
        getFromArray(items).subscribe(consumer);
    }

    /**
     * 解析集合
     * @param consumer
     * @param items
     * @param <T>
     */
    public <T> void  fromIterable(Consumer<T> consumer, List<T> items){
        getFromIterable(items).subscribe(consumer);
    }

    /**
     * 数组转List
     * @param consumer
     * @param items
     * @param <T>
     */
    public <T>void  toList(Consumer<List<T>> consumer, T... items){
        getFromArray(items).toList().subscribe(consumer);
    }

    /**
     * 发送验证码
     *
     * @param timeLong 验证码时长
     */
    public void sendCode(final int timeLong,Consumer<Disposable> disposable ,Observer<Long> observer) {
        Observable.interval(0, 1, TimeUnit.SECONDS)//第一个字段是什么时候开始，第二个字段表示时隔多长时间发送，第三个字段是前面两个字段的单位现在是秒
                .take(timeLong)//表示多长时间停止，这里是发送验证码时长
                .map(new Function<Long, Long>() {//map集合对两个数据做处理，不做处理是12345，处理后是54321
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return timeLong - aLong;
                    }
                })
                .observeOn(Schedulers.io())//子线程发送
                .observeOn(AndroidSchedulers.mainThread())//主线程接收
                .doOnSubscribe(disposable)
                .subscribe(observer);

    }

    /**
     * 计时
     */
    public Disposable  countTime(Consumer<Long> consumer){

           return  Flowable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                    .subscribe(consumer);


    }



}

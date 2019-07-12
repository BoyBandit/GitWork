package com.gitwork.boy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.news.frame.base.BaseBean;
import com.news.frame.base.BaseStatusActivity;
import com.news.frame.weight.TitleView;

public class MainActivity extends BaseStatusActivity<CommPresenter> {
    @Override
    public void onShowResult(int requestType, BaseBean result) {

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState, TitleView titleView, Intent intent) {

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}

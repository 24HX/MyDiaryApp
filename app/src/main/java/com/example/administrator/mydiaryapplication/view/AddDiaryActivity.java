package com.example.administrator.mydiaryapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.administrator.mydiaryapplication.R;
import com.example.administrator.mydiaryapplication.utils.GetDate;


public class AddDiaryActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleOtherView();
    }

    /**
     * 初始化布局
     * @return
     */
    @Override
    protected int initLayout() {
        return super.initLayout();
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {
        mIvBackTitle.setOnClickListener(this);
        mIvOkTitle.setOnClickListener(this);
        super.initListener();
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        super.initView();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mTvDate.setText("今天，" + GetDate.getDate());
        Intent intent = getIntent();
        mEtTitle.setText(intent.getStringExtra("title"));
        mEtContent.setText(intent.getStringExtra("content"));
        defaultBackPic();
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_ok_title:
                addDiary();  //添加一条日记
                finish();
                break;
            case R.id.iv_back_title:
                backButtonAdd();  //退出
                break;
        }
        super.onClick(v);
    }
}

package com.example.administrator.mydiaryapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mydiaryapplication.R;
import com.example.administrator.mydiaryapplication.bean.DiaryBean;
import com.example.administrator.mydiaryapplication.events.UpdateDiaryEvent;
import com.example.administrator.mydiaryapplication.utils.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Stack;


public abstract class BaseActivity extends AppCompatActivity {
    //标题栏控件
    LinearLayout mLlTitleTitle;
    ImageView mIvBackTitle;
    TextView mTvTitleTitle;
    ImageView mIvOkTitle;
    ImageView mIvOption;


    private static Stack<Activity> listActivity = new Stack<Activity>(); //保存已打开的Activity
    private static Toast mToast; //封装Toast


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this,Color.parseColor("#00bcd4"));
        setContentView(initLayout());
        listActivity.push(this);

        //初始化标题栏控件
        mLlTitleTitle = findViewById(R.id.ll_title_title); //标题栏
        mIvBackTitle = findViewById(R.id.iv_back_title); //标题栏返回按钮
        mTvTitleTitle = findViewById(R.id.iv_title_title); //标题栏文本框
        mIvOkTitle = findViewById(R.id.iv_ok_title); //标题栏保存按键
        mIvOption = findViewById(R.id.iv_option); //标题栏左侧按钮（弹出滑动菜单）
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化监听器
     */
    protected abstract void initListener();


    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 初始化控件
     */
    protected abstract void initView();


    /**
     * 设置布局
     * @return
     */
    protected abstract int initLayout();

    /**
     * 显示长toast
     * @param msg Toast显示的信息
     */
    public void toastLong(String msg){
        if (null == mToast) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
            mToast.show();
        }
    }

    /**
     * 显示短toast
     * @param msg Toast显示的信息
     */
    public void toastShort(String msg){
        if (null == mToast) {
             mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.show();
        }
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除当前Activity
        if(listActivity.contains(this))
        {
            listActivity.remove(this);
        }
    }

    /**
     * 跳转到新的Activity
     * @param targetActivityClass 要跳转的Activity
     * @param bundle 原Activity携带的数据
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
//

    /**
     * 关闭所有(前台、后台)Activity
     */
    protected static void finishAll() {
        int len = listActivity.size();
        for (int i = 0; i < len; i++) {
            Activity activity = listActivity.pop();
            activity.finish();
        }
    }

    /**
     * @param context
     * @param resId
     * @return
     * 节省内存的方式读取资源图片
     */
    protected static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    /**
     * @param res
     * @return
     * 节省内存的方式读取添加的图片
     */
    protected static Bitmap readBitMapPic(String res){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(res, opt);

        return bitmap;
    }

    /**
     * 主界面的标题栏设置
     */
    protected void initTitleMainView()
    {
        mIvBackTitle.setVisibility(View.INVISIBLE);
        mIvOkTitle.setVisibility(View.INVISIBLE);
    }

    /**
     * 其他界面的标题栏设置
     */
    protected void initTitleOtherView()
    {
        mIvOption.setVisibility(View.INVISIBLE);
    }

}

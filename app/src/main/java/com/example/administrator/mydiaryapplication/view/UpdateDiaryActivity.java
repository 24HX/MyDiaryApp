package com.example.administrator.mydiaryapplication.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.mydiaryapplication.R;
import com.example.administrator.mydiaryapplication.utils.GetDate;

import java.io.FileNotFoundException;

public class UpdateDiaryActivity extends BaseViewActivity implements View.OnClickListener {

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
     * 初始化控件
     */
    @Override
    protected void initView() {
        super.initView();
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {
        mIvOkTitle.setOnClickListener(this);
        mIvBackTitle.setOnClickListener(this);
        super.initListener();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        Intent intent = getIntent();
        _ID = intent.getExtras().getString("_id");
        mTv_id.setText(_ID);
        String tag = "显示id";
        Log.d(tag,"++++"+intent.getExtras().getString("_id"));
        mTvDate.setText("今天，" + GetDate.getDate());
        mEtTitle.setText(intent.getStringExtra("title"));
        mEtContent.setText(intent.getStringExtra("content"));
        Log.d(tag,"+++++"+intent.getStringExtra("title"));

        imagePath = intent.getStringExtra("path");
        Bitmap bitmap = BitmapFactory.decodeFile(intent.getStringExtra("path"));
        initImages(bitmap);  //显示图片
        //显示背景图片
        backgroundPath = intent.getStringExtra("backgroundPath");
        uri = Uri.parse(intent.getStringExtra("backgroundPath"));
        try {
            Context mContext = getApplication().getApplicationContext();
            drawable = Drawable.createFromStream(mContext.getContentResolver().openInputStream(uri),null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (ActivityCompat.checkSelfPermission(UpdateDiaryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(UpdateDiaryActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }else
        {
            loadBackgroundPic();
        }
        super.initData();
    }

    /**
     * 加载背景图片
     */
    private void loadBackgroundPic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(drawable==null)
                {
                    mRlContent.setBackgroundResource(R.drawable.background0);
                }else{
                    mRlContent.setBackgroundDrawable(drawable);
                }
            }
        }).start();
    }

    public static void startActivity(Context context,String _id, String title, String content, String path,String backgroundPath) {
        Intent intent = new Intent(context, UpdateDiaryActivity.class);
        intent.putExtra("_id",_id);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("path",path);
        intent.putExtra("backgroundPath",backgroundPath);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_ok_title:  //更新界面的更新日记按钮
                updateDiary(Integer.parseInt(_ID));
                finish();
                break;
            case R.id.iv_back_title: //更新界面的返回按钮
                backButtonUpdate();
                break;
        }
        super.onClick(v);
    }
}

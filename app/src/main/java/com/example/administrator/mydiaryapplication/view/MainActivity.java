package com.example.administrator.mydiaryapplication.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mydiaryapplication.R;
import com.example.administrator.mydiaryapplication.bean.DiaryBean;
import com.example.administrator.mydiaryapplication.db.DiaryDataBaseHelper;
import com.example.administrator.mydiaryapplication.db.DiaryDataBaseManger;
import com.example.administrator.mydiaryapplication.events.UpdateDiaryEvent;
import com.example.administrator.mydiaryapplication.utils.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, DiaryAdapter.onItemOnClickListener, DiaryAdapter.onItemLongClickListener {

    //activity_main中的控件
    RelativeLayout mRlMainActivity;
    LinearLayout mLlMainActivity;
    ImageView mIvCircle;
    TextView mTvDateMain;
    RelativeLayout mRlItemContent;
    TextView mTv_id;
    TextView mTvTitle;
    TextView mTvContent;
    RecyclerView mRlDiary;
    FloatingActionButton mFABAdd;
    LinearLayout mLlItemContent2;
    ImageView mIvPic;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ImageView mIvNavHeader;
    CircleImageView mIvHeader;

    private DiaryAdapter mAdapter;
    private List<DiaryBean> mDiaryBeanList;
    private DiaryDataBaseHelper mHelper;
    private long exitTime = 0;
    private static int DB_VERSION = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleMainView();
        EventBus.getDefault().register(this);
    }
    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {
        mFABAdd.setOnClickListener(this);
        mIvOption.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mRlMainActivity = findViewById(R.id.rl_mainActivity); //RelativeLayout总布局2
        mLlMainActivity = findViewById(R.id.ll_mainActivity); //LinearLayout总布局3
        mIvCircle = findViewById(R.id.iv_circle_main); //CardView中圆圈
        mTvDateMain = findViewById(R.id.tv_date_main); //CardView中日期
        mRlItemContent = findViewById(R.id.rl_item_content); //CardView中除日期外内容布局
        mTv_id = findViewById(R.id.tv_id_main);      //id
        mTvTitle = findViewById(R.id.tv_title_main); //CardView中标题
        mTvContent = findViewById(R.id.tv_content_main); //CardView中正文
        mRlDiary = findViewById(R.id.recyclerView_diary); //RecyclerView
        mFABAdd = findViewById(R.id.fab_add_main); //添加日记按钮
        mLlItemContent2 = findViewById(R.id.ll_item_content_2); //CardView中正文与图片布局
        mIvPic = findViewById(R.id.iv_pic_main); //CardView中图片布局
        mDrawerLayout = findViewById(R.id.dl_drawerLayout); //DrawerLayout总布局1
        mNavigationView = findViewById(R.id.nav_view); //左侧滑动菜单
        //navigationView的头布局控件
        View v = mNavigationView.getHeaderView(0);
        mIvNavHeader = v.findViewById(R.id.iv_nav_header); //滑动菜单头布局背景图片
        mIvHeader = v.findViewById(R.id.iv_civ_head); //滑动菜单头布局头像
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mHelper = new DiaryDataBaseHelper(this,"Diary.db",null,DB_VERSION);
        DiaryDataBaseManger instance = DiaryDataBaseManger.getInstance(this);  //实例化类
        mDiaryBeanList = new ArrayList<>();
        mDiaryBeanList = instance.getDiaryBeanList();
        mAdapter = new DiaryAdapter(this,mDiaryBeanList);
        mAdapter.setOnItemClickListener(this);//事件监听
        mAdapter.setOnItemLongClickListener(this);
        mRlDiary.setLayoutManager(new LinearLayoutManager(this));
        mRlDiary.setAdapter(mAdapter);
    }

    /**
     * 初始化布局
     * @return 当前布局
     */
    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    /**
     * 点击事件的实现
     * @param v 控件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab_add_main:
                openActivity(AddDiaryActivity.class,null);
                break;
            case R.id.iv_option:
                mDrawerLayout.openDrawer(GravityCompat.START);
                Bitmap navHeader = readBitMap(MainActivity.this,R.drawable.nav);
                Bitmap civHeader = readBitMap(MainActivity.this,R.drawable.header);
                mIvNavHeader.setImageBitmap(navHeader);//设置滑动菜单的头布局图片
                mIvHeader.setImageBitmap(civHeader);//设置头像
                break;
        }
    }

    /**
     * 滑动菜单的点击事件
     * @param item 项目
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_statistics:
                toastShort("点击了“ 统计 ”");
                break;
            case R.id.nav_pic:
                toastShort("点击了“ 图库 ”");
                break;
            case R.id.nav_theme:
                toastShort("点击了“ 主题 ”");
                break;
            case R.id.nav_share:
                toastShort("点击了“ 分享 ”");
                break;
            case R.id.nav_option:
                toastShort("点击了“ 设置 ”");
                break;
            case R.id.nav_Feedback:
                toastShort("点击了“ 反馈 ”");
                break;
        }
        return false;
    }

    /**
     * 返回键监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
                finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //EvenBus回调方法
    @Subscribe
    public void startUpdateDiaryActivity(UpdateDiaryEvent event) {
        String _id = String.valueOf(mDiaryBeanList.get(event.getPosition()).get_id());
        String title = mDiaryBeanList.get(event.getPosition()).getTitle();
        String content = mDiaryBeanList.get(event.getPosition()).getContent();
        String path = mDiaryBeanList.get(event.getPosition()).getPath();
        String backgroundPath = mDiaryBeanList.get(event.getPosition()).getBackgroundPath();
        UpdateDiaryActivity.startActivity(this,_id, title, content, path, backgroundPath);
    }

    //TODO 返回主界面时的刷新问题
    @Override
    protected void onResume() {
        initData();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册EvenBus
        if(EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onItemClick(View view, int pos) {
        EventBus.getDefault().post(new UpdateDiaryEvent(pos));
    }

    @Override
    public void onItemLongClick(final View view, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("是否删除日记？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int _id = mDiaryBeanList.get(pos).get_id();
                String data = mDiaryBeanList.get(pos).getTitle();
                String TAG = "标记";
                Log.d(TAG,"++奇迹++"+data);
                Log.d(TAG,"+++传奇+++"+_id);
                SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                Cursor cursor = dbDelete.query("Diary", null, null, null, null, null, null);
                int id = cursor.getColumnIndex("_id");
                Log.d(TAG,"++++哈哈哈哈+++++"+id);
                DiaryDataBaseManger instance = DiaryDataBaseManger.getInstance(MainActivity.this);
                instance.deleteDiary(_id);
                cursor.close();
                //TODO 实现删除事件 并刷新显示界面
                mDiaryBeanList.remove(pos);
                mAdapter.notifyItemRemoved(pos);
                if(pos != mDiaryBeanList.size())
                {
                    mAdapter.notifyItemRangeChanged(pos,mDiaryBeanList.size()-pos);
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
}

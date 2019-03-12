package com.example.administrator.mydiaryapplication.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.mydiaryapplication.R;
import com.example.administrator.mydiaryapplication.bean.DiaryBean;
import com.example.administrator.mydiaryapplication.db.DiaryDataBaseHelper;
import com.example.administrator.mydiaryapplication.db.DiaryDataBaseManger;
import com.example.administrator.mydiaryapplication.utils.GetDate;
import com.example.administrator.mydiaryapplication.utils.StatusBarCompat;


public class BaseViewActivity extends BaseActivity implements View.OnClickListener {

    private static boolean SHOW_BACKGROUND_PIC = false;
    private static final int REQUEST_SYSTEM_PIC = 0;
    private static int DB_VERSION = 7;
    public static String _ID ;
    private DiaryDataBaseHelper mHelper;
    public String imagePath; //添加图片真实路径
    public String backgroundPath; //背景图片路径
    //AddActivity公用控件
    TextView mTv_id;
    TextView mTvDate;
    EditText mEtTitle;
    EditText mEtContent;
    LinearLayout mLlShowBackgroundPic;
    ImageView mIvAddPic;
    ImageView mIvSetBackPic;
    LinearLayout mLlPicGroup;
    RelativeLayout mRlContent;
    ImageView mIvBackGroundPic0;
    ImageView mIvBackGroundPic1;
    ImageView mIvBackGroundPic2;
    ImageView mIvBackGroundPic3;
    ImageView mIvBackGroundPic4;
    private Resources r;
    public Uri uri = null;
    public Drawable drawable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        mIvAddPic.setOnClickListener(this);  //添加图片监听
        mIvSetBackPic.setOnClickListener(this);  //选择背景图片监听
        mIvBackGroundPic0.setOnClickListener(this);
        mIvBackGroundPic1.setOnClickListener(this);
        mIvBackGroundPic2.setOnClickListener(this);
        mIvBackGroundPic3.setOnClickListener(this);
        mIvBackGroundPic4.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mHelper = new DiaryDataBaseHelper(this,"Diary.db",null,DB_VERSION);
        mLlShowBackgroundPic.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mRlContent = findViewById(R.id.rl_add_content); //RelativeLayout 总布局
        mTv_id = findViewById(R.id.tv_add_diary_id); //_id
        mTvDate = findViewById(R.id.tv_add_diary_date); //日期
        mEtTitle = findViewById(R.id.et_add_diary_title); //标题
        mEtContent = findViewById(R.id.et_add_diary_content); //正文
        mLlPicGroup = findViewById(R.id.ll_gallery); //添加的图片布局
        mLlShowBackgroundPic = findViewById(R.id.ll_add_diary_backgroundPic); //显示背景图片布局
        mIvAddPic = findViewById(R.id.iv_selectPicture);   //添加图片
        mIvSetBackPic = findViewById(R.id.iv_selectBackGroundPic);  //背景图片显示按钮
        mIvBackGroundPic0 = findViewById(R.id.iv_add_diary_pic0); //背景0
        mIvBackGroundPic1 = findViewById(R.id.iv_add_diary_pic1); //背景1
        mIvBackGroundPic2 = findViewById(R.id.iv_add_diary_pic2); //背景2
        mIvBackGroundPic3 = findViewById(R.id.iv_add_diary_pic3); //背景3
        mIvBackGroundPic4 = findViewById(R.id.iv_add_diary_pic4); //背景4
    }

    /**
     * 初始化布局
     * @return 当前布局
     */
    @Override
    protected int initLayout() {
        StatusBarCompat.compat(this, Color.parseColor("#00bcd4"));
        return R.layout.activity_base_view;
    }

    /**
     * 监听事件
     * @param v View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_selectBackGroundPic:  //显示及收起设置背景布局监听
                if(SHOW_BACKGROUND_PIC)
                {
                    mLlShowBackgroundPic.setVisibility(View.INVISIBLE);
                    SHOW_BACKGROUND_PIC = false;
                }else
                {
                    mLlShowBackgroundPic.setVisibility(View.VISIBLE);
                    Bitmap bitmap0 = readBitMap(this,R.drawable.background0);
                    Bitmap bitmap1 = readBitMap(this,R.drawable.background1);
                    Bitmap bitmap2 = readBitMap(this,R.drawable.background2);
                    Bitmap bitmap3 = readBitMap(this,R.drawable.background3);
                    Bitmap bitmap4 = readBitMap(this,R.drawable.background4);
                    mIvBackGroundPic0.setImageBitmap(bitmap0);
                    mIvBackGroundPic1.setImageBitmap(bitmap1);
                    mIvBackGroundPic2.setImageBitmap(bitmap2);
                    mIvBackGroundPic3.setImageBitmap(bitmap3);
                    mIvBackGroundPic4.setImageBitmap(bitmap4);
                    SHOW_BACKGROUND_PIC = true;
                }
                break;

            case R.id.iv_add_diary_pic0:   //设置背景0
                selectBackgroundPic(R.drawable.background0);
                backgroundPath = getBackgroundPicPath(R.drawable.background0);
                break;

            case R.id.iv_add_diary_pic1:   //设置背景1
                selectBackgroundPic(R.drawable.background1);
                backgroundPath = getBackgroundPicPath(R.drawable.background1);
                break;

            case R.id.iv_add_diary_pic2:   //设置背景2
                selectBackgroundPic(R.drawable.background2);
                backgroundPath = getBackgroundPicPath(R.drawable.background2);
                break;

            case R.id.iv_add_diary_pic3:   //设置背景3
                selectBackgroundPic(R.drawable.background3);
                backgroundPath = getBackgroundPicPath(R.drawable.background3);
                break;

            case R.id.iv_add_diary_pic4:   //设置背景4
                selectBackgroundPic(R.drawable.background4);
                backgroundPath = getBackgroundPicPath(R.drawable.background4);
                break;

            case R.id.iv_selectPicture:   //浮动菜单选择图片按钮
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开系统相册
                    openAlbum();
                }
                break;
        }
    }

    /**
     * 设置背景图片
     * @param drawable 背景图片在Drawable文件夹下的id
     */
    private void selectBackgroundPic(int drawable) {
        Bitmap bitmap = readBitMap(this,drawable);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        mRlContent.setBackgroundDrawable(bitmapDrawable);
    }
    /**
     * @param id 背景图片的id
     * @return 背景图片的uri
     */
    private String getBackgroundPicPath(int id)
    {
        r = getApplicationContext().getResources();
        backgroundPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(id) + "/"
                + r.getResourceTypeName(id) + "/"
                + r.getResourceEntryName(id);
        return backgroundPath;
    }

    /**
     * 默认背景
     */
    public void defaultBackPic()
    {
        selectBackgroundPic(R.drawable.background0);
        backgroundPath = getBackgroundPicPath(R.drawable.background0);
    }

    /**
     * 保存一条日记
     */
    public void addDiary() {
        DiaryBean diary = new DiaryBean();
        diary.setDate(GetDate.getDate().toString());
        diary.setTitle(mEtTitle.getText().toString());
        diary.setContent(mEtContent.getText().toString());
        diary.setPath(imagePath);
        diary.setBackgroundPath(backgroundPath);
        //标题或正文内容不为空的时候才添加日记
        if(!mEtTitle.getText().toString().isEmpty() || !mEtContent.getText().toString().isEmpty())
        {
            DiaryDataBaseManger diaryDataBaseManger = new DiaryDataBaseManger(this);
            diaryDataBaseManger.saveDiary(diary);
        }
    }


    /**
     * 添加日记Activity的退出按钮事件
     */
    public void backButtonAdd() {
        //若标题或正文内容不为空时，弹出窗口询问用户是否保存日记
        if(!mEtTitle.getText().toString().isEmpty() || !mEtContent.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否保存日记").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addDiary();
                    finish();  //退出到主界面
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish(); //退出到主界面
                }
            }).show();
        }else //若标题或正文内容为空时,直接退出到主界面
        {
            finish(); //退出到主界面
        }
    }

    /**
     * 更新一条日记
     */
    public void updateDiary(int id)
    {
        DiaryBean diary = new DiaryBean();
        diary.setTitle(mEtTitle.getText().toString());
        diary.setContent(mEtContent.getText().toString());
        diary.setPath(imagePath);
        diary.setBackgroundPath(backgroundPath);
        String TAG = "标题内容";
        Log.d(TAG,"++++++"+mEtTitle.getText().toString());
        Log.d(TAG,"啊啊啊啊啊啊"+id);
        DiaryDataBaseManger instance = DiaryDataBaseManger.getInstance(this);
        instance.updateDiary(diary,id);
    }

    /**
     * 更新日记Activity的返回按钮事件
     */
    public void backButtonUpdate()
    {
        //若标题或正文内容不为空时，弹出窗口询问用户是否保存日记
        if(!mEtTitle.getText().toString().isEmpty() || !mEtContent.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否保存日记").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateDiary(Integer.parseInt(_ID)); //更新日记
                    finish();//退出到主界面
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish(); //退出到主界面
                }
            }).show();
        }else if(mEtTitle.getText().toString().isEmpty() && mEtContent.getText().toString().isEmpty())
        {
            //若标题与正文内容为空时,提示用户必须填写内容或删除该条日记
            toastShort("请添加日记内容或删除该条日记");
        }
    }

    /**
     * 打开系统相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SYSTEM_PIC);
    }

    /**
     * Android6.0以上获取危险授权
     * @param requestCode 请求码
     * @param permissions 授权
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openAlbum();
                }else
                {
                    toastShort("您没有授权");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SYSTEM_PIC && resultCode == RESULT_OK && null != data)
        {
            if(Build.VERSION.SDK_INT >= 19)
            {
                handleImageOnKitkat(data);//sdk版本>=19的选择的图片返回的uri为封装过的uri,需要进行解析
            }else{
                handleImageBeforeKitKat(data);//sdk版本<19的则不要要对返回的uri进行解析
            }
        }
    }

    /**
     * 获取图片真实路径
     * @param uri 图片 uri
     * @param selection
     * @return 图片真实路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 获取图片路径 无需解析 SDK<19
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    /**
     * 获取图片路径 需要解析 SDK>=19
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) {
        imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content:" +
                        "//downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是File类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }

    /**
     * 根据图片路径显示图片
     * @param imagePath 添加的图片路径
     */
    private void displayImage(String imagePath) {
        if(imagePath != null)
        {
            //滑动显示图片
            Bitmap bitmap = readBitMapPic(imagePath);
            initImages(bitmap);
        }
    }
    /**
     * 滑动显示图片
     */
    public void initImages(Bitmap bitmap) {
        //TODO 将选择的图片添加进数组
        Bitmap bitmap1 = readBitMap(this,R.drawable.background1);
        Bitmap bitmap2 = readBitMap(this,R.drawable.background2);
        Bitmap bitmap3 = readBitMap(this,R.drawable.background3);
        Bitmap bitmap4 = readBitMap(this,R.drawable.background4);

        Bitmap images[] = {bitmap,bitmap1,bitmap2,bitmap3,bitmap4};
        for(int i = 0; i<images.length;i++)
        {
            View v = LayoutInflater.from(this).inflate(R.layout.item_horizontalscrollview,mLlPicGroup,false);
            ImageView mIvPic = v.findViewById(R.id.imageView);
            mIvPic.setImageBitmap(images[i]);
            mLlPicGroup.addView(v);
        }
    }
}

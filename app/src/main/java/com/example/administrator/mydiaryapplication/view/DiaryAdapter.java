package com.example.administrator.mydiaryapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mydiaryapplication.R;
import com.example.administrator.mydiaryapplication.bean.DiaryBean;
import com.example.administrator.mydiaryapplication.events.UpdateDiaryEvent;
import com.example.administrator.mydiaryapplication.utils.GetDate;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

/**
 * RecyclerView适配器
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>  {

    private Context mContext;
    private List<DiaryBean> mDiaryBeanList;
    private LayoutInflater mLayoutInflater;

    private int mEditPosition = -1;


    public interface onItemOnClickListener
    {
        void onItemClick(View view,int pos);
    }

    public interface onItemLongClickListener
    {
        void onItemLongClick(View view,int pos);
    }

    private onItemOnClickListener mOnItemOnClickListener;
    private onItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(onItemOnClickListener listener){
        this.mOnItemOnClickListener = listener;
    }
    public void setOnItemLongClickListener(onItemLongClickListener longClickListener)
    {
        this.mOnItemLongClickListener = longClickListener;
    }

    public DiaryAdapter (Context context, List<DiaryBean> mDiaryBeanList)
    {
        mContext = context;
        this.mDiaryBeanList = mDiaryBeanList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        DiaryViewHolder diaryViewHolder = new DiaryViewHolder(mLayoutInflater.inflate(R.layout.item_recyclrerview,viewGroup,false));
        return diaryViewHolder;
    }

    @Override
    public void onBindViewHolder(final DiaryViewHolder diaryViewHolder, final int position) {
        String dateSystem = GetDate.getDate().toString();
        //编辑时间与当天时间相同时，将圆圈设置为橙色
        if(mDiaryBeanList.get(position).getDate().equals(dateSystem))
        {
            diaryViewHolder.mIvCircle.setImageResource(R.drawable.circle_orange);
        }
        diaryViewHolder.mTv_id.setText(mDiaryBeanList.get(position).get_id()+"");
        diaryViewHolder.mTvDate.setText(mDiaryBeanList.get(position).getDate());
        diaryViewHolder.mTvTitle.setText(mDiaryBeanList.get(position).getTitle());
        diaryViewHolder.mTvContent.setText(mDiaryBeanList.get(position).getContent());
        String path = mDiaryBeanList.get(position).getPath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        diaryViewHolder.mIvPic.setImageBitmap(bitmap);
        //recyclerView Item点击事件
        if(mOnItemOnClickListener != null)
        {
            diaryViewHolder.mLlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = diaryViewHolder.getLayoutPosition();
                    mOnItemOnClickListener.onItemClick(diaryViewHolder.itemView,pos);
                }
            });
        }
        //recyclerView Item长按事件
        if(mOnItemLongClickListener != null)
        {
            diaryViewHolder.mLlItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = diaryViewHolder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(diaryViewHolder.itemView,pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDiaryBeanList.size();
    }


    public static class DiaryViewHolder extends RecyclerView.ViewHolder{
        TextView mTv_id;
        ImageView mIvCircle;
        TextView mTvDate;
        TextView mTvTitle;
        TextView mTvContent;
        LinearLayout mLlItem;
        RelativeLayout mLlItemContent;
        ImageView mIvPic;


        DiaryViewHolder(View view) {
            super(view);
            mTv_id = view.findViewById(R.id.tv_id_main);
            mIvCircle = view.findViewById(R.id.iv_circle_main);
            mTvDate = view.findViewById(R.id.tv_date_main);
            mTvTitle = view.findViewById(R.id.tv_title_main);
            mTvContent = view.findViewById(R.id.tv_content_main);
            mLlItem = view.findViewById(R.id.ll_item);
            mLlItemContent = view.findViewById(R.id.rl_item_content);
            mIvPic = view.findViewById(R.id.iv_pic_main);
        }
    }
}

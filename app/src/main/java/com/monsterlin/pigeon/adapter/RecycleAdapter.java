package com.monsterlin.pigeon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.NoteEntity;
import com.monsterlin.pigeon.utils.MyDateUtils;

import java.util.List;


/**
 * Created by Pluto  on 2016/5/6.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private List<NoteEntity> mDataList;/*数据源*/
    private LayoutInflater mInflater;/*布局解析器*/

    /*声明一个接口，用于实现点击事件*/
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClickListener(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;/*设置接口对象*/

    /**
     * 设置点击事件监听器供外部调用
     * @param mOnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    /**
     * 构造方法
     * @param dataList 数据源
     * @param mContext 上下文
     */
    public RecycleAdapter(List<NoteEntity> dataList, Context mContext) {
        this.mDataList = dataList;
        this.mInflater = LayoutInflater.from(mContext);/**/

    }

    /**
     * 创建ViewHolder
     * 【将布局转化为VIew,并传递给RecyclerView封装好的ViewHolder】
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*获取itemView*/
        View view = mInflater.inflate(R.layout.rc_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    /**
     * 绑定ViewHolder并进行相应的赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv_title.setText(mDataList.get(position).getNoteTitle());
        holder.tv_content.setText(mDataList.get(position).getNoteContent());
        String dateString =  MyDateUtils.getStringForFormat(mDataList.get(position).getCreateDate());
        holder.tv_date.setText(dateString);
        switch (mDataList.get(position).getSort()){
            case 0:/*工作*/
                holder.tv_sort.setText("全部");
                break;
            case 1:/*学习*/
                holder.tv_sort.setText("工作");
                break;
            case 2:/*生活*/
                holder.tv_sort.setText("学习");
                break;
            case  3:
                holder.tv_sort.setText("生活");
                break;
        }


        /*为列表项*/
        if (null != mOnItemClickListener){/*外部设置了点击事件监听器*/
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {/*v 与 holder.itemView是同一个吗*/
                    int layoutPosition = holder.getLayoutPosition();/*得到布局的position*/
//                     holder.getAdapterPosition()
                     mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
                 }
             });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();/*得到布局的position*/
                    mOnItemClickListener.onItemLongClickListener(holder.itemView, layoutPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_content, tv_sort,tv_date;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_sort = (TextView) itemView.findViewById(R.id.tv_sort);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

}
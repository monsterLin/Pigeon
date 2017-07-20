package com.monsterlin.pigeon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.ChatModel;
import com.monsterlin.pigeon.bean.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * @author : danry
 * @version : 1.0
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/18
 * @desc : 聊天适配器
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseAdapter> {

    private ArrayList<ItemModel> mDataList = new ArrayList<>();

    public void replaceAll(ArrayList<ItemModel> list) {
        mDataList.clear();
        if (list != null && list.size() > 0) {
            mDataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<ItemModel> list) {
        if (mDataList != null && list != null) {
            mDataList.addAll(list);
            notifyItemRangeChanged(mDataList.size(),list.size());
        }

    }

    @Override
    public BaseAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemModel.CHAT_LEFT:
                return new ChatLeftViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false));
            case ItemModel.CHAT_RIGHT:
                return new ChatRightViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseAdapter holder, int position) {
        holder.setData(mDataList.get(position).object);
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public class BaseAdapter extends RecyclerView.ViewHolder {

        public BaseAdapter(View itemView) {
            super(itemView);
        }

        void setData(Object object) {

        }
    }

    private class ChatLeftViewHolder extends BaseAdapter {
        private ImageView ic_user;
        private TextView tv;

        public ChatLeftViewHolder(View view) {
            super(view);
            ic_user = (ImageView) itemView.findViewById(R.id.ic_user);
            tv = (TextView) itemView.findViewById(R.id.tv_msg);
        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatModel model = (ChatModel) object;
            Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(ic_user);
            tv.setText(model.getContent());
        }
    }

    private class ChatRightViewHolder extends BaseAdapter {
        private ImageView mIcUser;
        private TextView mTvMsg;

        public ChatRightViewHolder(View view) {
            super(view);
            mIcUser = (ImageView) itemView.findViewById(R.id.ic_user);
            mTvMsg = (TextView) itemView.findViewById(R.id.tv_msg);

        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatModel model = (ChatModel) object;
            Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(mIcUser);
            mTvMsg.setText(model.getContent());
        }
    }
}

package com.monsterlin.pigeon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.ChatMessage;

import java.util.List;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 聊天窗口适配器
 */

public class ChatMessageAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING)
        {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            // 通过ItemType设置不同的布局
            if (getItemViewType(position) == 0)
            {
                convertView = mInflater.inflate(R.layout.item_from_msg, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.id_from_msg_info);
            } else
            {
                convertView = mInflater.inflate(R.layout.item_to_msg, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.id_to_msg_info);
            }
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 设置数据
        viewHolder.mMsg.setText(chatMessage.getMsg());
        return convertView;
    }

    private final class ViewHolder
    {
        TextView mMsg;
    }

}
package com.monsterlin.pigeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.Tools;
import com.monsterlin.pigeon.ui.brower.BrowerActivity;
import com.monsterlin.pigeon.vholder.ToolsVHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 工具板块适配器
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolsVHolder> {
    private Context mContext ;
    private List<Tools> toolsList;
    private LayoutInflater mInflater ;

    public ToolsAdapter(Context mContext, List<Tools> toolsList) {
        this.mContext = mContext;
        this.toolsList = toolsList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public ToolsVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tools,parent,false);
        ToolsVHolder vHolder = new ToolsVHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(ToolsVHolder holder, final int position) {
        holder.mTvTitle.setText(toolsList.get(position).getToolsTitle());
        final String iconUrl = toolsList.get(position).getToolsIcon().getFileUrl();
        if (!TextUtils.isEmpty(iconUrl)){
            Picasso.with(mContext).load(iconUrl).into(holder.mIvIcon);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, BrowerActivity.class);
                i.putExtra("url",toolsList.get(position).getToolsUrl());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return toolsList.size();
    }
}

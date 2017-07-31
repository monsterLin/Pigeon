package com.monsterlin.pigeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.news.Data;
import com.monsterlin.pigeon.ui.brower.BrowerActivity;
import com.monsterlin.pigeon.vholder.TopNewsVHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/22
 * @desc : 资讯适配器
 */
public class TopNewsAdapter extends RecyclerView.Adapter<TopNewsVHolder> {
    private Context mContext;
    private List<Data> newsBeanList;
    private LayoutInflater mInflater;

    public TopNewsAdapter(Context mContext, List<Data> newsBeanList) {
        this.mContext = mContext;
        this.newsBeanList = newsBeanList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public TopNewsVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_topnews, null);
        TopNewsVHolder vHolder = new TopNewsVHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(TopNewsVHolder holder, final int position) {
        holder.mTvTitle.setText(newsBeanList.get(position).getTitle());
        holder.mTvFrom.setText(newsBeanList.get(position).getAuthor_name());
        holder.mTvDate.setText(newsBeanList.get(position).getDate());
        Picasso.with(mContext).load(newsBeanList.get(position).getThumbnail_pic_s()).placeholder(R.mipmap.ic_launcher).into(holder.mIvImg1);
        Picasso.with(mContext).load(newsBeanList.get(position).getThumbnail_pic_s02()).placeholder(R.mipmap.ic_launcher).into(holder.mIvImg2);
        Picasso.with(mContext).load(newsBeanList.get(position).getThumbnail_pic_s03()).placeholder(R.mipmap.ic_launcher).into(holder.mIvImg3);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, BrowerActivity.class);
                i.putExtra("url",newsBeanList.get(position).getUrl());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsBeanList.size();
    }
}

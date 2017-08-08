package com.monsterlin.pigeon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.Sticker;
import com.monsterlin.pigeon.vholder.StickerVHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/8
 * @desc : 亲情贴适配器
 */

public class StickerAdapter extends RecyclerView.Adapter<StickerVHolder> {

    private Context mContext ;
    private List<Sticker> stickerList ;
    private LayoutInflater mInflater ;

    public StickerAdapter(Context mContext, List<Sticker> stickerList) {
        this.mContext = mContext;
        this.stickerList = stickerList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public StickerVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_sticker,parent,false);
        StickerVHolder stickerVHolder = new StickerVHolder(view);
        return stickerVHolder;
    }

    @Override
    public void onBindViewHolder(StickerVHolder holder, int position) {
        holder.mTvDate.setText(stickerList.get(position).getUpdatedAt());
        holder.mTvContent.setText(stickerList.get(position).getContent());
        holder.mTvNick.setText(stickerList.get(position).getUser().getNick());

        BmobFile bmobFile = stickerList.get(position).getUser().getUserPhoto();
        if (bmobFile!=null){
            Picasso.with(mContext).load(bmobFile.getFileUrl()).into(holder.mCivUserPhoto);
        }else {
            holder.mCivUserPhoto.setImageResource(R.drawable.ic_default);
        }

        int typeInt = stickerList.get(position).getUser().getType();

        if (typeInt==0){
            //子女
            holder.mRlSticker.setBackgroundResource(R.color.syellow);
        }else if (typeInt==1){
            //父母
            holder.mRlSticker.setBackgroundResource(R.color.sblue);
        }



    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }
}

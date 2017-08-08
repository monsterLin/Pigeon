package com.monsterlin.pigeon.vholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monsterlin.pigeon.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/8
 * @desc : 亲情贴适配器
 */
public class StickerVHolder extends RecyclerView.ViewHolder {
    public TextView mTvDate , mTvContent ,mTvNick ;
    public CircleImageView mCivUserPhoto ;
    public RelativeLayout mRlSticker;

    public StickerVHolder(View itemView) {
        super(itemView);
        mTvDate= (TextView) itemView.findViewById(R.id.sticker_tv_date);
        mTvContent= (TextView) itemView.findViewById(R.id.sticker_tv_content);
        mTvNick= (TextView) itemView.findViewById(R.id.sticker_tv_nick);
        mCivUserPhoto= (CircleImageView) itemView.findViewById(R.id.sticker_civ_userPhoto);
        mRlSticker= (RelativeLayout) itemView.findViewById(R.id.sticker_rl);
    }
}

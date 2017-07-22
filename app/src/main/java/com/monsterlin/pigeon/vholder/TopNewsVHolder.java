package com.monsterlin.pigeon.vholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.pigeon.R;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/22
 * @desc : 新闻ViewHolder
 */
public class TopNewsVHolder extends RecyclerView.ViewHolder {
    public TextView mTvTitle ,mTvFrom , mTvDate ;
    public ImageView mIvImg1 , mIvImg2 , mIvImg3 ;

    public TopNewsVHolder(View itemView) {
        super(itemView);
        mTvTitle= (TextView) itemView.findViewById(R.id.topNews_tv_title);
        mTvFrom= (TextView) itemView.findViewById(R.id.topNews_tv_from);
        mTvDate= (TextView) itemView.findViewById(R.id.topNews_tv_date);
        mIvImg1= (ImageView) itemView.findViewById(R.id.topNews_iv_img1);
        mIvImg2= (ImageView) itemView.findViewById(R.id.topNews_iv_img2);
        mIvImg3= (ImageView) itemView.findViewById(R.id.topNews_iv_img3);
    }
}

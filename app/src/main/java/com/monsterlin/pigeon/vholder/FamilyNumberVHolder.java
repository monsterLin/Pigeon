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
 * @time : 2017/8/5
 * @desc : 家庭成员
 */
public class FamilyNumberVHolder extends RecyclerView.ViewHolder {
    public ImageView mIvIcon , mIvType , mIvUserInfo;
    public TextView mTvNick;

    public FamilyNumberVHolder(View itemView) {
        super(itemView);
        mIvIcon = (ImageView) itemView.findViewById(R.id.number_iv_icon);
        mIvType = (ImageView) itemView.findViewById(R.id.number_iv_type);
        mIvUserInfo = (ImageView) itemView.findViewById(R.id.number_iv_userInfo);
        mTvNick = (TextView) itemView.findViewById(R.id.number_tv_nick);
    }
}

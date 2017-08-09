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
 * @desc : 工具板块vholder
 */
public class ToolsVHolder extends RecyclerView.ViewHolder {
    public ImageView mIvIcon ;
    public TextView mTvTitle ;

    public ToolsVHolder(View itemView) {
        super(itemView);
        mIvIcon= (ImageView) itemView.findViewById(R.id.tools_iv_icon);
        mTvTitle= (TextView) itemView.findViewById(R.id.tools_tv_title);
    }
}

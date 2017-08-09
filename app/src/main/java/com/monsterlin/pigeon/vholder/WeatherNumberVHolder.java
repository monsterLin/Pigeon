package com.monsterlin.pigeon.vholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.pigeon.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 家庭成员
 */
public class WeatherNumberVHolder extends RecyclerView.ViewHolder {
    public TextView mTvNick,mTvWeather;
    public CircleImageView mCivUserPhoto ;
    public ImageView mCivSms ;

    public WeatherNumberVHolder(View itemView) {
        super(itemView);
        mCivUserPhoto = (CircleImageView) itemView.findViewById(R.id.weather_number_iv_icon);
        mTvNick = (TextView) itemView.findViewById(R.id.weather_number_tv_nick);
        mTvWeather = (TextView) itemView.findViewById(R.id.weather_number_tv_weather);
        mCivSms = (ImageView) itemView.findViewById(R.id.weather_number_iv_sms);
    }
}

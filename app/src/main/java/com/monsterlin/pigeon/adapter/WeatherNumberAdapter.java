package com.monsterlin.pigeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.monsterlin.pigeon.vholder.WeatherNumberVHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/9
 * @desc : 家庭天气
 */
public class WeatherNumberAdapter extends RecyclerView.Adapter<WeatherNumberVHolder> {
    private List<User> userList;
    private Context mContext;
    private LayoutInflater mInflater;

    public WeatherNumberAdapter(List<User> userList, Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public WeatherNumberVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_weather_number, parent, false);
        WeatherNumberVHolder weatherNumberVHolder = new WeatherNumberVHolder(view);
        return weatherNumberVHolder;
    }

    @Override
    public void onBindViewHolder(final WeatherNumberVHolder holder, final int position) {
        BmobFile userPhotoFile = userList.get(position).getUserPhoto();
        if (userPhotoFile != null) {
            Picasso.with(mContext).load(userPhotoFile.getFileUrl()).into(holder.mCivUserPhoto);
        } else {
            holder.mCivUserPhoto.setImageResource(R.drawable.ic_default);
        }

        String nick = userList.get(position).getNick();
        if (!TextUtils.isEmpty(nick)) {
            holder.mTvNick.setText(nick);
        }

        holder.mCivSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSendSMSTo(userList.get(position).getMobilePhoneNumber());
            }
        });

        String location = userList.get(position).getLocation();

        if (!TextUtils.isEmpty(location)) {

            WeatherSearchQuery mquery = new WeatherSearchQuery(location, WeatherSearchQuery.WEATHER_TYPE_LIVE);
            WeatherSearch mweathersearch = new WeatherSearch(mContext);
            mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                @Override
                public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int rCode) {
                    if (rCode == 1000) {

                        if (localWeatherLiveResult.getLiveResult() != null) {
                            LocalWeatherLive weatherlive = localWeatherLiveResult.getLiveResult();
                            holder.mTvWeather.setText(weatherlive.getWeather());
                        } else {
                            holder.mTvWeather.setText("--");
                        }
                    } else {
                        ToastUtils.showToast(mContext, "查询天气异常：" + rCode);
                    }
                }

                @Override
                public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int rCode) {

                }
            });
            mweathersearch.setQuery(mquery);
            mweathersearch.searchWeatherAsyn(); //异步搜索

        } else {
            holder.mTvWeather.setText("--");
        }


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void doSendSMSTo(String phoneNumber) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            mContext.startActivity(intent);
        }
    }


}

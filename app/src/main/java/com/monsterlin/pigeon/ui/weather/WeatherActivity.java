package com.monsterlin.pigeon.ui.weather;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.WeatherNumberAdapter;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Family;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.utils.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 天气
 */
public class WeatherActivity extends BaseActivity implements WeatherSearch.OnWeatherSearchListener {

    private Toolbar mToolBar;
    private RecyclerView mRvList;
    private WeatherNumberAdapter mAdapter;

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;

    private WeatherSearchQuery mquery = null;
    private WeatherSearch mWeatherSearch = null;


    private TextView mWeatherCity, mWeatherWInfo, mWeatherTime, mWeatherWind, mWeatherTemp, mWeatherHumidity;


    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "天气通", true);
        mWeatherCity = findView(R.id.weather_tv_city);
        mWeatherWInfo = findView(R.id.weather_tv_WInfo);
        mWeatherTime = findView(R.id.weather_tv_time);
        mWeatherWind = findView(R.id.weather_tv_wind);
        mWeatherTemp = findView(R.id.weather_tv_temp);
        mWeatherHumidity = findView(R.id.weather_tv_humidity);
        mRvList = findView(R.id.weather_rv_list);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        dialog.showDialog();
        initLocation();


    }

    private void updateUserLocation(String city) {
        User mCurrentUser = BmobUser.getCurrentUser(User.class);
        if (mCurrentUser != null) {
            // 允许用户使用应用
            User user = new User();
            user.setLocation(city);
            user.update(mCurrentUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {

                    } else {
                        ToastUtils.showToast(WeatherActivity.this, "Error：" + e.getMessage());
                    }
                }
            });
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
            ToastUtils.showToast(WeatherActivity.this, "no cache");
        }

    }

    private void initWeatherNumber() {
        BmobQuery<User> queryNumber = new BmobQuery();

        User mCurrentUser = BmobUser.getCurrentUser(User.class);

        if (mCurrentUser != null) {
            Family family = mCurrentUser.getFamily();

            if (family != null) {
                queryNumber.addWhereEqualTo("family", family);
                queryNumber.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list != null) {
                                mAdapter = new WeatherNumberAdapter(list, WeatherActivity.this);
                                mRvList.setAdapter(mAdapter);
                                mRvList.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
                                dialog.dismissDialog();
                            } else {
                                dialog.dismissDialog();
                                ToastUtils.showToast(WeatherActivity.this, "无数据显示");
                            }
                        } else {
                            dialog.dismissDialog();
                            ToastUtils.showToast(WeatherActivity.this, "查询成员异常：" + e.getMessage());
                        }
                    }
                });


            } else {
                dialog.dismissDialog();
                ToastUtils.showToast(WeatherActivity.this, "未查询到家庭");
            }

        } else {
            dialog.dismissDialog();
            ToastUtils.showToast(WeatherActivity.this, "未查询到家庭");
        }
    }


    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //高精度定位
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);

        mLocationClient.setLocationOption(mLocationOption);

        mLocationClient.startLocation();
    }

    @Override
    public void processClick(View v) {

    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容
                    updateUserLocation(amapLocation.getCity());
                    initCardWeather(amapLocation.getCity());
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            } else {

            }
        }

    };


    private void initCardWeather(String city) {
        mWeatherCity.setText(city);
        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mWeatherSearch = new WeatherSearch(this);
        mWeatherSearch.setOnWeatherSearchListener(this);
        mWeatherSearch.setQuery(mquery);
        mWeatherSearch.searchWeatherAsyn(); //异步搜索
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (localWeatherLiveResult.getLiveResult() != null) {

                LocalWeatherLive weatherlive = localWeatherLiveResult.getLiveResult();
                mWeatherWInfo.setText(weatherlive.getWeather());

                mWeatherTime.setText(weatherlive.getReportTime().substring(11, 16) + "  发布");
                mWeatherWind.setText(weatherlive.getWindDirection() + "风  " + weatherlive.getWindPower() + "级");
                mWeatherTemp.setText("温度  " + weatherlive.getTemperature() + "°");
                mWeatherHumidity.setText("湿度  " + weatherlive.getHumidity() + "%");

                initWeatherNumber();
            }
        } else {
            ToastUtils.showToast(WeatherActivity.this, "No Result：" + rCode);
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int rCode) {

    }

}

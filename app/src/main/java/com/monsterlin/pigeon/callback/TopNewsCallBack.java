package com.monsterlin.pigeon.callback;

import com.google.gson.Gson;
import com.monsterlin.pigeon.bean.news.TopNewsBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/22
 * @desc : 新闻回调
 */
public abstract class TopNewsCallBack extends Callback<TopNewsBean> {
    @Override
    public TopNewsBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        TopNewsBean topNewsBean = new Gson().fromJson(string, TopNewsBean.class);
        return topNewsBean;
    }
}

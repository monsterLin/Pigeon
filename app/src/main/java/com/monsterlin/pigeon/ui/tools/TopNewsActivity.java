package com.monsterlin.pigeon.ui.tools;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.TopNewsAdapter;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.news.TopNewsBean;
import com.monsterlin.pigeon.callback.TopNewsCallBack;
import com.monsterlin.pigeon.constant.ApiConfig;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/22
 * @desc : 头条新闻
 */
public class TopNewsActivity extends BaseActivity{

    private Toolbar mToolBar ;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRvList ;
    private TopNewsAdapter mAdapter ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_topnews;
    }

    @Override
    public void initViews() {
        mToolBar=findView(R.id.common_toolbar);
        initToolBar(mToolBar,"今日资讯",true);
        mRefreshLayout=findView(R.id.refreshLayout);
        mRvList=findView(R.id.topnews_rv_list);
    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
            }
        });
    }

    @Override
    public void initData() {
        String url = "http://v.juhe.cn/toutiao/index";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("type", "shehui")
                .addParams("key", ApiConfig.TOP_NEWS_APIKEY)
                .build()
                .execute(new TopNewsCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToast(TopNewsActivity.this,e.getMessage());
                    }

                    @Override
                    public void onResponse(TopNewsBean response, int id) {
                        mAdapter=new TopNewsAdapter(TopNewsActivity.this,response.getResult().getData());
                        mRvList.setLayoutManager(new LinearLayoutManager(TopNewsActivity.this, LinearLayoutManager.VERTICAL, false));
                        mRvList.setAdapter(mAdapter);
                    }
                });
    }

    @Override
    public void processClick(View v) {

    }
}

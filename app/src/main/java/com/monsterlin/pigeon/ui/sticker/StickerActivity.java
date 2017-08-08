package com.monsterlin.pigeon.ui.sticker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.StickerAdapter;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Sticker;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc :
 */
public class StickerActivity extends BaseActivity {
    private RecyclerView mStickerRv;
    private Toolbar mToolBar;
    private StickerAdapter stickerAdapter;
    private FloatingActionButton mFabNew ;
    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sticker;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "亲情贴", true);
        mStickerRv = findView(R.id.sticker_rv);
        mFabNew=findView(R.id.sticker_fab_new);
        mRefreshLayout=findView(R.id.refreshLayout);

        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void initListener() {
        setOnClick(mFabNew);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    @Override
    public void initData() {
        stickerAdapter = new StickerAdapter(this,getTestData());
        mStickerRv.setLayoutManager(new LinearLayoutManager(this));
        mStickerRv.setItemAnimator(new DefaultItemAnimator());
        mStickerRv.setAdapter(stickerAdapter);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.sticker_fab_new:

                break;
        }
    }

    public static List<Sticker> getTestData(){
        List<Sticker> mList = new ArrayList<>();
        for (int i = 0 ;i<12;i++){
            Sticker s = new Sticker();
            s.setContent("测试数据测试数据"+i);
            mList.add(s);
        }

        return mList;
    }
}

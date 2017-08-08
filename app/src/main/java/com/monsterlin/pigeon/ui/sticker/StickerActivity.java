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
import com.monsterlin.pigeon.ui.setting.NewStickerActivity;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 亲情贴
 */
public class StickerActivity extends BaseActivity {
    private RecyclerView mStickerRv;
    private Toolbar mToolBar;
    private StickerAdapter stickerAdapter;
    private FloatingActionButton mFabNew;
    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;
    private BmobQuery<Sticker> stickerQuery;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sticker;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "亲情贴", true);
        mStickerRv = findView(R.id.sticker_rv);
        mFabNew = findView(R.id.sticker_fab_new);
        mRefreshLayout = findView(R.id.refreshLayout);

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
        //TODO 分页查询
        stickerQuery = new BmobQuery<>();
        stickerQuery.include("user,family");
        stickerQuery.findObjects(new FindListener<Sticker>() {
            @Override
            public void done(List<Sticker> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        stickerAdapter = new StickerAdapter(StickerActivity.this, list);
                        mStickerRv.setLayoutManager(new LinearLayoutManager(StickerActivity.this));
                        mStickerRv.setItemAnimator(new DefaultItemAnimator());
                        mStickerRv.setAdapter(stickerAdapter);
                    } else {
                        ToastUtils.showToast(StickerActivity.this, "无数据显示");
                    }
                } else {
                    ToastUtils.showToast(StickerActivity.this, "FindSticker：" + e.getMessage());
                }
            }
        });


    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.sticker_fab_new:
                nextActivity(NewStickerActivity.class);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}

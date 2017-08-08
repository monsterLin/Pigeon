package com.monsterlin.pigeon.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.ToolsAdapter;
import com.monsterlin.pigeon.base.BaseFragment;
import com.monsterlin.pigeon.bean.Tools;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/9
 * @desc : 工具板块
 */

public class ToolsFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRvTools;
    private ToolsAdapter toolsAdapter;
    private BmobQuery<Tools> query;

    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tools;
    }

    @Override
    public void initViews() {
        mRvTools = findView(R.id.tools_rv_list);
        mRefreshLayout=findView(R.id.refreshLayout);

        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void initListener() {


    }

    @Override
    public void initData() {
        query = new BmobQuery<>();
        boolean isCache = query.hasCachedResult(Tools.class);

        if(isCache){
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        }else{
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }

        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(3));//此表示缓存一天
        query.setLimit(10);
        query.findObjects(new FindListener<Tools>() {
            @Override
            public void done(List<Tools> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        toolsAdapter = new ToolsAdapter(getContext(), list);
                        mRvTools.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        mRvTools.setAdapter(toolsAdapter);

                    }
                }
            }
        });


    }

    @Override
    public void processClick(View v) {

    }
}
